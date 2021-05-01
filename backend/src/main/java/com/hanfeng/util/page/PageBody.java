package com.hanfeng.util.page;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author HanFeng
 */
public class PageBody<T> {
    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private long pages;
    /**
     * 总页数
     */
    private List<T> itemList;


    public PageBody() {
    }

    public static <T> PageBody<T> getPageBody(Page<T> page) {
        PageBody<T> body = new PageBody<>();
        body.total = page.getTotal();
        body.pages = page.getPages();
        body.pageNum = page.getPageNum();
        body.pageSize = page.getPageSize();
        body.itemList = page.getResult();
        return body;
    }

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }
}
