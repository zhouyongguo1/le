package le.oa.core.models.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pagination {

    private Long count;

    private int pageSize;

    private int pageIndex;

    public Pagination(Long count, int pageSize, int pageIndex) {
        this.count = count;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public Long getCount() {
        return count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    @JsonIgnore
    public int getPageOffset() {
        return (pageIndex - 1) * pageSize;
    }

}
