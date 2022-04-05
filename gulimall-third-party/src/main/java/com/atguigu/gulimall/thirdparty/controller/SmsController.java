package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxh
 * @createTime 2022-03-29 22:03:46
 */
@RestController
@RequestMapping("sms")
public class SmsController {
    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone") String phone,@RequestParam("code") String code) {
        Map<String, String> params = new HashMap<>(2);
        params.put("phone", phone);
        params.put("code", code);
        return R.ok().put("data", params);
    }
}
