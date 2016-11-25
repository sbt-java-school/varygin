package lesson26.home.utils;

public class Request implements Cloneable {
    private Integer firstResult;
    private Integer pageSize;

    public Request() {
    }

    public Request(Integer page, Integer pageSize) {
        this.firstResult = page * pageSize;
        if (this.firstResult < 0) {
            this.firstResult = 0;
        }
        this.pageSize = pageSize;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPages(Long total) {
        float pages = total / (float) pageSize;
        return (long) Math.ceil(pages);
    }
}