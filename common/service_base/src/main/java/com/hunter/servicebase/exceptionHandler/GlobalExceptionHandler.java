package com.hunter.servicebase.exceptionHandler;


import com.hunter.commonUtils.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo error(Exception ex) {
        ex.printStackTrace();
        return ResultVo.error().message("全局异常异常");
    }

    @ExceptionHandler(ConsumeException.class)
    @ResponseBody
    public ResultVo error(ConsumeException ex) {
        ex.printStackTrace();
        return ResultVo.error().code(ex.getCode()).message(ex.getMsg());
    }

}
