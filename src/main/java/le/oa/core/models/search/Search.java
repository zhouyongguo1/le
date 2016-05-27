package le.oa.core.models.search;

public abstract class Search {

    private int pageSize = 3;

    private int pageIndex = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageOffset() {
        return (pageIndex - 1) * pageSize;
    }
}
