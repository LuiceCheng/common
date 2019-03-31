package com.example.common.model;

import com.example.common.entity.BaseObject;

import java.io.Serializable;

/**
 * @Author sen
 * @Date: 2019/3/14 20:59
 * @Description:
 */
public class Demo extends BaseObject implements Serializable {
    private String id;
    private String name;
    private String age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
