package com.atguigu.gulimall.oms.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.oms.entity.OrderReturnReasonEntity;
import com.atguigu.gulimall.oms.service.OrderReturnReasonService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;

import javax.annotation.Resource;


/**
 * 退货原因
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-04-04 00:23:01
 */
@RestController
@RequestMapping("oms/orderreturnreason")
@RequiredArgsConstructor
@Slf4j
public class OrderReturnReasonController {
    @Autowired
    private OrderReturnReasonService orderReturnReasonService;
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{num}")
    public void sendMsg(@PathVariable("num") int num) {
        for(int i = 0; i<num;i++) {
            OrderReturnReasonEntity entity = new OrderReturnReasonEntity();
            entity.setId(0L);
            entity.setName("哈哈");
            entity.setSort(0);
            entity.setStatus(0);
            entity.setCreateTime(new Date());
            rabbitTemplate.convertAndSend("hello-java-exchange", "hello-java", entity,
                    new CorrelationData(UUID.randomUUID().toString()));
            log.info("消息发送完成");
        }
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("oms:orderreturnreason:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderReturnReasonService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("oms:orderreturnreason:info")
    public R info(@PathVariable("id") Long id){
		OrderReturnReasonEntity orderReturnReason = orderReturnReasonService.getById(id);

        return R.ok().put("orderReturnReason", orderReturnReason);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("oms:orderreturnreason:save")
    public R save(@RequestBody OrderReturnReasonEntity orderReturnReason){
		orderReturnReasonService.save(orderReturnReason);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("oms:orderreturnreason:update")
    public R update(@RequestBody OrderReturnReasonEntity orderReturnReason){
		orderReturnReasonService.updateById(orderReturnReason);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("oms:orderreturnreason:delete")
    public R delete(@RequestBody Long[] ids){
		orderReturnReasonService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
