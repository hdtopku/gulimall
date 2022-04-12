package com.atguigu.gulimall.common.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.utils.BizCodeEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * The type Sentinel gateway config.
 *
 * @author lxh
 * @createTime 2022 -04-13 02:00:32
 */
@Configuration
public class SentinelGatewayConfig {

    /**
     * TODO Webflux、Mono响应式编程学习
     * Instantiates a new Sentinel gateway config.
     */
    public SentinelGatewayConfig() {
        GatewayCallbackManager.setBlockHandler((exchange, t) -> {
            R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
            return ServerResponse.ok().body(Mono.just(JSONUtil.toJsonStr(error)), String.class);
        });
    }
}
