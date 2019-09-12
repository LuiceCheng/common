package com.example.common.model;

public class RoleResourceLink{

    private static final long serialVersionUID = 1L;

    private String id;
    private String roleId;
    private String resourceId;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }

    public String getRoleId(){
        return this.roleId;
    }

    public void setResourceId(String resourceId){
        this.resourceId = resourceId;
    }

    public String getResourceId(){
        return this.resourceId;
    }

}