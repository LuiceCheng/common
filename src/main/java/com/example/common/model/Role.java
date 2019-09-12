package com.example.common.model;

public class Role{

    private static final long serialVersionUID = 1L;

    private String roleId;
    private String roleName;

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }

    public String getRoleId(){
        return this.roleId;
    }

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return this.roleName;
    }

}