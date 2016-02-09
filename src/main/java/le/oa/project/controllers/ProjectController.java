package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.project.controllers.form.ProjectForm;
import le.oa.project.models.Project;
import le.oa.project.models.ProjectUser;
import le.oa.project.repositories.ProjectRepository;
import le.oa.project.repositories.ProjectUserRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.session.FlashScope;

import java.util.List;

@Controller
public class ProjectController extends BaseTeamController {

    private ProjectRepository projectRepository;
    private ProjectUserRepository projectUserRepository;

    @Inject
    public ProjectController(ProjectRepository projectRepository,
                             ProjectUserRepository projectUserRepository) {
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
    }

    @Get
    @Route("/projects")
    public Result index() {
        return Results.html();
    }

    @Get
    @Route("/project")
    public Result project() {
        return Results.html();
    }

    @Get
    @Route("/project/new")
    public Result add() {
        return Results.html();
    }

    @Post
    @Route("/project/new")
    @Transactional
    public Result create(FlashScope flashScope, ProjectForm projectForm) {
        Project project = projectForm.toProject(null);
        projectRepository.save(project);
        List<ProjectUser> projectUsers = projectForm.toProjectUsers(project.getId());
        projectUsers.forEach(p -> projectUserRepository.save(p));
        flashScope.success("项目添加成功");
        return this.redirect("/");
    }


    @Get
    @Route("/project/archived")
    public Result archived() {
        return Results.html();
    }

}
