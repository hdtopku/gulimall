package com.atguigu.gulimall.pms.feign.fallback;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.utils.BizCodeEnum;
import org.springframework.stereotype.Component;

import com.atguigu.gulimall.pms.feign.SeckillFeignService;

/**
 * The type Seckill feign service fallback.
 *
 * @author lxh
 * @createTime 2022 -04-12 16:33:52
 */
@Component
public class SeckillFeignServiceFallback implements SeckillFeignService {

    @Override
    public R getSkuSeckillInfo(Long skuId) {
        return R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
    }
}
