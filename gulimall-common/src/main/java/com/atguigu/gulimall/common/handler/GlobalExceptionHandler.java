package com.atguigu.gulimall.common.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.utils.BizCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lxh
 * @createTime 2022-03-08 13:38:15
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.atguigu.gulimall"})
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<StringBuilder> list = e.getBindingResult().getFieldErrors().stream().map(
                error -> StrUtil.builder().append(error.getDefaultMessage()).append(
                        ObjectUtil.isNull(error.getRejectedValue()) ? "" :
                                String.format("，当前非法值：%s", error.getRejectedValue())))
                .collect(Collectors.toList());
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", list);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg())
                .put("error", String.format("【%s】%s", throwable.getMessage(), ExceptionUtil.stacktraceToString(throwable)));
    }
}