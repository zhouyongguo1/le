package le.oa.home.controllers;

import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
import le.oa.project.models.Project;
import le.oa.project.models.dto.ProjectDto;
import le.oa.project.repositories.ProjectRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

import java.util.List;


@Controller
public class HomeController extends BaseTeamController {
    private ProjectRepository projectRepository;

    @Inject
    public HomeController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Get
    @Route("/")
    public Result index() {
        List<ProjectDto> list = projectRepository.findProjectDtoByUserId(currentUserProvider.get().getId(),
                currentTeamProvider.get().getId());
        return Results.html().render("list", list);
    }

}
