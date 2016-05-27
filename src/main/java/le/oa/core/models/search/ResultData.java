package le.oa.core.models.search;

import java.util.ArrayList;
import java.util.List;

public class ResultData<T> {

    private List<T> list = new ArrayList<>();
    private long totalCount = 0;

    public ResultData() {
    }

    public ResultData(Long count, List<T> list) {
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

    public static <T> ResultData<T> create(Long count, List<T> list) {
        return new ResultData<>(count, list);
    }
}
