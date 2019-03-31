package com.example.common.entity;

import com.example.common.enums.EnError;

import java.util.List;

/**
 * @Author sen
 * @Date: 2019/3/11 21:23
 * @Description:
 */
public class Msg<T> {
    //错误代码
    private Integer code = EnError.DEFAULT.getCode();
    //返回的数据，任意类型
    private T data;
    //返回结果说明
    private String msg = EnError.DEFAULT.getDescription();

    private List<String> msgs;

    public Boolean getSuccess(){
        return 0 == this.code;
    }

    public void setResult(EnError result){
        this.code = result.getCode();
        this.msg = result.getDescription();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }
}
