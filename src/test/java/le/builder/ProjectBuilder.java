package le.builder;

import com.google.inject.Inject;
import le.oa.core.models.User;
import le.oa.project.models.Project;
import le.oa.project.models.ProjectRole;
import le.oa.project.models.ProjectUser;
import le.oa.project.repositories.ProjectRepository;
import le.oa.project.repositories.ProjectUserRepository;
import le.test.Builder;

public class ProjectBuilder implements Builder<Project> {

    @Inject
    private ProjectRepository projectRepository;
    @Inject
    private ProjectUserRepository projectUserRepository;

    private String name = "乐趣办公";
    private User user;

    @Override
    public Project build() {
        Project project = new Project();
        project.setName(name);
        return project;
    }

    @Override
    public Project create() {
        Project project = build();
        projectRepository.save(project);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setProject(project);
        projectUser.setUser(user);
        projectUser.setOwner(true);
        projectUser.setRole(ProjectRole.ADMIN);
        projectUserRepository.save(projectUser);
        return project;
    }

    public ProjectBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProjectBuilder user(User user) {
        this.user = user;
        return this;
    }
}
