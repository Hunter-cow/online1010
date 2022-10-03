package com.hunter.commonUtils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultVo {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();

    //构造方法私有
    private ResultVo(){}

    //成功静态方法
    public static ResultVo ok(){
        ResultVo resultVo=new ResultVo();
        resultVo.setSuccess(true);
        resultVo.setCode(ResultCode.SUCCESS);
        resultVo.setMessage("成功");
        return resultVo;
    }
    //失败静态方法
    public static ResultVo error(){
        ResultVo resultVo=new ResultVo();
        resultVo.setSuccess(false);
        resultVo.setCode(ResultCode.ERROR);
        resultVo.setMessage("失败");
        return resultVo;
    }

    public ResultVo success(Boolean success){
        this.setSuccess(success);
        return this;

    }
    public ResultVo message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultVo code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultVo data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public ResultVo data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
