package com.example.common.model;

public class UserRoleLink{

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String roleId;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }

    public String getRoleId(){
        return this.roleId;
    }

}