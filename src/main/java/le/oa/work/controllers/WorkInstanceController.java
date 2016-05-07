package le.oa.work.controllers;


import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
import le.oa.work.models.WorkTemplate;
import le.oa.work.repositories.FormTeamTemplateRepository;
import le.oa.work.repositories.WorkTemplateRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;

@Controller
public class WorkInstanceController extends BaseTeamController {

    private WorkTemplateRepository workTemplateRepository;
    private FormTeamTemplateRepository formTeamTemplateRepository;


    @Inject
    public WorkInstanceController(WorkTemplateRepository workTemplateRepository,
                                  FormTeamTemplateRepository formTeamTemplateRepository) {

        this.workTemplateRepository = workTemplateRepository;
        this.formTeamTemplateRepository = formTeamTemplateRepository;
    }

    @Get
    @Route("/work")
    public Result index() {
        return Results.html();
    }

    @Get
    @Route("/work/instance")
    public Result work() {
        return Results.html();
    }

    @Get
    @Route("/work/instance/add")
    public Result add() {
        return Results.html();
    }

    @Get
    @Route("/work/instance/new")
    public Result newOne(@Param("template-id") Integer templateId) {
        WorkTemplate template = this.checkEntity(workTemplateRepository.findById(templateId));
        return Results.html()
                .render("template", template);
    }


    @Get
    @Route("/work/instance/archived")
    public Result archived() {
        return Results.html();
    }
}
