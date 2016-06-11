package le.oa.project.controllers.form;

import le.oa.core.models.User;
import le.oa.project.models.Project;
import le.oa.project.models.ProjectRole;
import le.oa.project.models.ProjectUser;

public class ProjectUserForm {

    private ProjectRole role;
    private Integer userId;
    private boolean isOwner;

    public ProjectUser toProjectUser(Project project) {
        ProjectUser projectUser = new ProjectUser();
        projectUser.setOwner(isOwner);
        User user = new User();
        user.setId(userId);
        projectUser.setUser(user);
        projectUser.setRole(role);
        projectUser.setProject(project);
        return projectUser;
    }

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
}
