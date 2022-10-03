package com.hunter.commonUtils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();

    //构造方法私有


    public R() {
    }

    //成功静态方法

    public R(Boolean success, Integer code, String message, Map<String, Object> data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //失败静态方法
    public static R error() {
        R resultVo = new R();
        resultVo.setSuccess(false);
        resultVo.setCode(ResultCode.ERROR);
        resultVo.setMessage("失败");
        return resultVo;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;

    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
