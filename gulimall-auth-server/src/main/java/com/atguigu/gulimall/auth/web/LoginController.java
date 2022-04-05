package com.atguigu.gulimall.auth.web;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.auth.feign.MemberFeignService;
import com.atguigu.gulimall.auth.feign.ThirdPartyFeignService;
import com.atguigu.gulimall.auth.vo.UserLoginVo;
import com.atguigu.gulimall.auth.vo.UserRegisterVo;
import com.atguigu.gulimall.common.constant.AuthServerConstant;
import com.atguigu.gulimall.common.utils.BizCodeEnum;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lxh
 * @createTime 2022-03-29 17:13:55
 */
@Controller
@RequiredArgsConstructor

public class LoginController {
    private final ThirdPartyFeignService thirdPartyFeignService;
    private final StringRedisTemplate redisTemplate;
    private final MemberFeignService memberFeignService;

    @GetMapping("/sms/sendcode")
    @ResponseBody
    public R sendCode(@RequestParam("phone") String phone) {
//        接口防刷
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (StrUtil.isNotBlank(redisCode)) {
            assert redisCode != null;
            String[] s = redisCode.split("_");
            if (s.length == 2 && System.currentTimeMillis() - Long.parseLong(s[1]) < 60000) {
                return R.error(BizCodeEnum.VALID_CODE_EXCEPTION.getCode(), BizCodeEnum.VALID_CODE_EXCEPTION.getMsg());
            }
        }
        String code = StrUtil.strBuilder().append(IdUtil.simpleUUID().substring(0, 5))
                .append("_").append(System.currentTimeMillis()).toString();

//        验证码的再次校验，
//        redis缓存验证码：key：sms:code:13871982981，value：code
//        防止同一个手机号60s内再次发送验证码
        redisTemplate.opsForValue().set(
                AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, code, 10, TimeUnit.MINUTES);
        return thirdPartyFeignService.sendCode(phone, code);
    }

    @GetMapping("/login.html")
    public String login(HttpSession session){
        if (null != session.getAttribute(AuthServerConstant.LOGIN_USER)) {
            return "redirect:http://gulimall.com";
        }
        return "login";
    }


    @PostMapping("login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes, HttpSession session) {

        R r = memberFeignService.login(vo);
        if (r.getCode() == 0) {
            session.setAttribute(AuthServerConstant.LOGIN_USER, JSONUtil.toBean(JSONUtil.toJsonStr(r.get("data")), MemberRespVo.class));
//            response.addCookie(new Cookie());
            return "redirect:http://gulimall.com/index.html";
        }
        HashMap<String, String> errors = MapUtil.of("msg", r.getMessage());
        redirectAttributes.addFlashAttribute("errors", errors);
        return "redirect:http://auth.gulimall.com/login.html";
    }

    /**
     * 注册成功后重定向
     *
     * @return
     */
    @PostMapping("register")
    public String registerSuccess(@Validated UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
//            校验出错，转发到注册页
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            redirectAttributes.addFlashAttribute("errors", errors);
//            return "forward:/reg.html"; // registerSuccess是post请求，通过post方法forward到/reg.html会报错405
//            return "redirect:/reg.html"; // 重定向到ip，http://192.168.0.15:8886/reg.html;jsessionid=23D93B355DA14E8A9CABDC15E53783B9
//            return "reg.html"; // 前端提交以后，刷新页面，表单会再次提交，会再次进入到这一行
            return "redirect:http://auth.gulimall.com/reg.html"; // 重定向使用session，将数据放在session中，只要跳到下一个页面，session中的数据就会取出并删除，TODO 但是分布式下的Session问题
        }
//        校验验证码
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (StrUtil.isBlank(redisCode) || !redisCode.startsWith(vo.getCode())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/reg.html"; // 重定向使用session，将数据放在session中，只要跳到下一个页面，session中的数据就好取出并删除，TODO 但是分布式下的Session问题
        }
//        调用远程服务进行注册
        R r = memberFeignService.register(vo);
        if (r.getCode() == 0) {
//            成功
            redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
            return "redirect:http://auth.gulimall.com/login.html";
        }
        redirectAttributes.addFlashAttribute("errors", MapUtil.of("msg", r.getData("msg", new TypeReference<String>() {})));
        return "redirect:http://auth.gulimall.com/reg.html"; // 重定向使用session，将数据放在session中，只要跳到下一个页面，session中的数据就好取出并删除，TODO 但是分布式下的Session问题
    }
}
