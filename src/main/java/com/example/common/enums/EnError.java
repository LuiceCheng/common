package com.example.common.enums;

/**
 * @Author sen
 * @Date: 2019/3/11 21:25
 * @Description:
 */
public enum EnError {
    DEFAULT(0,""),
    NO_MATCH(1,"没有匹配项"),
    INSERT_NONE(2,"插入失败"),
    DELETE_NONE(3,"删除失败"),
    UPDATE_NONE(4,"更新失败"),
    CONFLICT(5,"已经存在"),
    SQL_ERROR(1000,"SQL执行失败"),
    EXECUTE_FAILED(4000,"执行失败"),
    SYSTEM_ERROR(500,"系统异常"),
    TIMEOUT(40001,"调用超时"),
    NONEMPTY_PARAM(40005,"缺少必填参数"),
    ILLEGAL_PARAM(40006,"参数不合法"),
    SERVICE_INVALIID(40008,"服务不可用");

    private Integer code = 0;
    private String description = "";
    public Integer getCode(){
        return code;
    }
    public String getDescription(){
        return description;
    }

    EnError(Integer code, String description){
        this.code = code;
        this.description = description;
    }
}
