package com.software.blogserver.handler;

import com.software.blogcommon.entity.Result;
import com.software.blogserver.exception.BizException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result bizHandler(BizException e){
        e.printStackTrace();
        return Result.fail(e.getMessage(), e.getCode());
    }
}
