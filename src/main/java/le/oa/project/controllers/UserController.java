package le.oa.project.controllers;

import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
import le.oa.project.models.Project;
import le.oa.project.repositories.ProjectRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

@Controller
public class UserController extends BaseTeamController {

    private ProjectRepository projectRepository;

    @Inject
    public UserController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Get
    @Route("/project/{id}/users")
    public Result index(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        return Results.html()
                .render("project", project);
    }


}
