package com.atguigu.gulimall.common.config.must;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.utils.BizCodeEnum;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lxh
 * @createTime 2022-04-12 15:23:36
 */
@Configuration
public class SentinelConfig implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        response.getWriter().print(JSONUtil.toJsonStr(error));
    }
}
