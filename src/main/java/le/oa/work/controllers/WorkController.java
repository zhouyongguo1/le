package le.oa.work.controllers;


import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
import le.oa.work.models.FormTeamTemplate;
import le.oa.work.repositories.TeamTemplateRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

import java.util.List;

@Controller
public class WorkController extends BaseTeamController {
    
    private TeamTemplateRepository templateRepository;
    @Inject
    public WorkController(TeamTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;

    }

    @Get
    @Route("/works")
    public Result index() {
        return Results.html();
    }

    @Get
    @Route("/work")
    public Result work() {
        return Results.html();
    }

    @Get
    @Route("/work/new")
    public Result add() {
        List<FormTeamTemplate> list = templateRepository.findAll();
        return Results.html()
                .render("list", list);
    }

    @Get
    @Route("/work/archived")
    public Result archived() {
        return Results.html();
    }
}
