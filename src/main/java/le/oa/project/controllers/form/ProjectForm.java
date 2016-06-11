package le.oa.project.controllers.form;

import le.oa.project.models.Project;
import le.oa.project.models.ProjectUser;

import java.util.ArrayList;
import java.util.List;

public class ProjectForm {
    private Integer id;
    private String name;
    private List<ProjectUserForm> users = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectUserForm> getUsers() {
        return users;
    }

    public void setUsers(List<ProjectUserForm> users) {
        this.users = users;
    }

    public Project toProject(Project project) {
        if (project == null) {
            project = new Project();
        }
        project.setId(id);
        project.setName(name);
        return project;
    }

    public List<ProjectUser> toProjectUsers(Project project) {
        List<ProjectUser> list = new ArrayList<>();
        for (ProjectUserForm item : users) {
            list.add(item.toProjectUser(project));
        }
        return list;
    }

}
