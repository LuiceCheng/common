package com.example.common.config.redis;

/**
 * description: 全局锁
 *
 * @author:cxs
 * @date: 2019/9/6 15:26
 */
public class Lock {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
