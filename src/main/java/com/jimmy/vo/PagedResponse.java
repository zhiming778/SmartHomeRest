package com.jimmy.vo;

import java.util.List;

public class PagedResponse<T> {

    private int page;
    private int rows;
    private String order;
    private List<T> body;

    public PagedResponse() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "PagedResponse [page=" + page + ", rows=" + rows + ", order=" + order + ", body=" + body + "]";
    }

}
