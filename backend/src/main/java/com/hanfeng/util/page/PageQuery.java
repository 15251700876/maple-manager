package com.hanfeng.util.page;

/**
 * @author HanFeng
 */
public class PageQuery<T> {

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;


    private T queryData;


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getQueryData() {
        return queryData;
    }

    public void setQueryData(T queryData) {
        this.queryData = queryData;
    }
}
