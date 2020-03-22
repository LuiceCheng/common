package com.example.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;

@ApiModel(value = "分页信息")
public class PageBounds extends RowBounds implements Serializable {
    private static final long serialVersionUID = -4683595215388409169L;
    public final static int NO_PAGE = 1;
    @ApiModelProperty(value = "页号")
    protected int page = NO_PAGE;
    @ApiModelProperty(value = "分页大小")
    protected int pageSize = Integer.MAX_VALUE;

    public PageBounds() {
    }

    public PageBounds(RowBounds rowBounds) {
            this.page = (rowBounds.getOffset()/rowBounds.getLimit())+1;
            this.pageSize = rowBounds.getLimit();
    }

    /**
     * Query TOP N, default containsTotalCount = false
     * @param limit
     */
    public PageBounds(int limit) {
        this.pageSize = limit;
    }

    public PageBounds(int page, int limit) {
        this.page = page;
        this.pageSize = limit;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
