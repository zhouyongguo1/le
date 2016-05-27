package le.oa.work.models.search;

import le.oa.core.models.search.Search;

public class WorkInstanceSearch extends Search {
    private String title;
    private Boolean isArchived = false;//是否归档了这个工作流

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }
}
