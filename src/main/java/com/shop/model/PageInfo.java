package com.shop.model;

import com.github.pagehelper.Page;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author
 * @create 2019-10-16 11:36
 */
public class PageInfo<T> implements Serializable {
    //当前页
    private int pageNum;
    //每页数量
    private int pageSize;
    //总记录数
    private long total;
    //总页数
    private int  pages;
    //结果集
    private List<T> list;
    //是否为第一页
    private boolean isFirstPage=false;
    //是否为最后一页
    private boolean isLastPage=false;

    public PageInfo(){

    }
    public PageInfo(List<T> list){
        if (list instanceof Page){
            Page page=(Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize=page.getPageSize();

            this.pages=page.getPages();
            this.list=page;
            this.total=page.getTotal();
        }else if (list instanceof Collection){
            this.pageNum=1;
            this.pageSize=list.size();

            this.pages=1;
            this.list=list;
            this.total=list.size();
        }
        if (list instanceof Collection){
            judgePageBoudary();
        }
    }
    //判断页面边界
    private void  judgePageBoudary(){
        isFirstPage=pageNum==1;
        isLastPage=pageNum==pages;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
}
