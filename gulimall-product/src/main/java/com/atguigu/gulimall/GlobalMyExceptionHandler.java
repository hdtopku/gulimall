//package com.atguigu.gulimall;
//
//import cn.hutool.core.util.StrUtil;
//import com.atguigu.common.utils.R;
//import com.atguigu.gulimall.common.utils.BizCodeEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author lxh
// * @createTime 2022-03-08 13:38:15
// */
//@Slf4j
//@RestControllerAdvice(basePackages = "com.atguigu.gulimall.pms.controller")
//public class GlobalMyExceptionHandler {
//
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        List<StringBuilder> list = e.getBindingResult().getFieldErrors().stream().map(
//                error -> StrUtil.builder().append(error.getDefaultMessage()).append(error.getRejectedValue()))
//                .collect(Collectors.toList());
//        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", list);
//    }
//
//    @ExceptionHandler(value = Throwable.class)
//    public R handleException(Throwable throwable) {
//        log.error(throwable.getMessage());
//        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
//    }
//}
