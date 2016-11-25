package lesson26.home.utils;

import java.util.List;

public class Page<T> {
    private List<T> contents;
    private Long totalCount;
    private Long pages;

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }

    public Page(List<T> contents, Long totalCount) {
        this.contents = contents;
        this.totalCount = totalCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
