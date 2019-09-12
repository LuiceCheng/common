package com.example.common.model;


public class Resource{

    private static final long serialVersionUID = 1L;

    private String resourceId;
    private String resourceName;

    public void setResourceId(String resourceId){
        this.resourceId = resourceId;
    }

    public String getResourceId(){
        return this.resourceId;
    }

    public void setResourceName(String resourceName){
        this.resourceName = resourceName;
    }

    public String getResourceName(){
        return this.resourceName;
    }

}