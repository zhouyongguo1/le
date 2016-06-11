package le.oa.project.models.search;

import le.oa.core.models.search.Search;
import le.oa.project.models.ProjectStatus;

public class ProjectSearch extends Search {
    private String name;
    private Boolean isArchived = false;
    private ProjectStatus status;
    private Integer currentUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
    }
}
