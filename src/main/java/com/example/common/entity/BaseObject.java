package com.example.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sen
 * @Date: 2019/2/24 13:07
 * @Description:
 */
public class BaseObject implements Serializable {

    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
