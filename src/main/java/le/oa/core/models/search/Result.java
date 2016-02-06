package le.oa.core.models.search;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private List<T> list = new ArrayList<>();
    private long totalCount = 0;

    public Result() {
    }

    public Result(Long count, List<T> list) {
        this.totalCount = count;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public static <T> Result<T> create(Long count, List<T> list) {
        return new Result<>(count, list);
    }
}
