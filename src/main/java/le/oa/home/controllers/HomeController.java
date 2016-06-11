package le.oa.home.controllers;

import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
import le.oa.core.repositories.EventRepository;
import le.oa.project.models.dto.ProjectDto;
import le.oa.project.repositories.ProjectRepository;
import le.oa.work.models.WorkInstanceItem;
import le.oa.work.repositories.WorkInstanceItemRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;

import java.util.List;


@Controller
public class HomeController extends BaseTeamController {
    private ProjectRepository projectRepository;
    private WorkInstanceItemRepository instanceItemRepository;


    @Inject
    public HomeController(ProjectRepository projectRepository,
                          WorkInstanceItemRepository instanceItemRepository) {
        this.projectRepository = projectRepository;
        this.instanceItemRepository = instanceItemRepository;
    }

    @Get
    @Route("/")
    public Result index() {
        List<ProjectDto> projectDtos = projectRepository.findProjectDtoByUserId(currentUserProvider.get().getId(),
                currentTeamProvider.get().getId());
        return Results.html().render("projects", projectDtos);
    }

    @Get
    @Route("/work/instance-items")
    public Result instanceItems(@Param("status") String status) {
        List<WorkInstanceItem> instanceItems = null;
        Integer userId = this.currentUserProvider.get().getId();
        if ("WAIT" .equals(status)) {
            instanceItems = instanceItemRepository.findWaitByUserId(userId);
        } else {
            instanceItems = instanceItemRepository.findStartByUserId(userId);
        }
        return Results.html()
                .render("instanceItems", instanceItems);
    }

}
