package le.oa.work.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.work.models.FormTeamTemplate;
import le.oa.work.models.WorkFlow;
import le.oa.work.models.WorkFlowTask;
import le.oa.work.models.WorkTemplate;
import le.oa.work.models.dto.WorkTemplateDto;
import le.oa.work.repositories.FormTeamTemplateRepository;
import le.oa.work.repositories.WorkTemplateRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Delete;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

import java.util.List;
import java.util.Optional;

@Controller
public class WorkTemplateController extends BaseTeamController {
    private WorkTemplateRepository workTemplateRepository;
    private FormTeamTemplateRepository formTeamTemplateRepository;

    @Inject
    public WorkTemplateController(WorkTemplateRepository workTemplateRepository,
                                  FormTeamTemplateRepository formTeamTemplateRepository) {
        this.workTemplateRepository = workTemplateRepository;
        this.formTeamTemplateRepository = formTeamTemplateRepository;
    }

    @Get
    @Route("/work/templates")
    public Result templates() {
        List<WorkTemplate> datas = workTemplateRepository.findByUser(currentUserProvider.get().getId());
        return Results.html().render("datas", datas);
    }

    @Get
    @Route("/work/templates/{id}/json")
    public Result template(@PathParam("id") Integer id) {
        WorkTemplate template = checkEntity(workTemplateRepository.findById(id));
        WorkFlowTask task;
        if (template.getWorkFlow().getTasks().size() == 0) {
            task = new WorkFlowTask();
            template.getWorkFlow().getTasks().add(task);
            task.setTaskId("1");
        } else {
            task = template.getWorkFlow().getTasks().get(0);
        }
        task.setPhoto(currentUserProvider.get().getPhoto());
        task.setUserId(currentUserProvider.get().getId());
        task.setUserName(currentUserProvider.get().getName());

        return Results.json()
                .render("templateId", template.getFormTeamTemplate().getId())
                .render("templateName", template.getFormTeamTemplate().getName())
                .render("fields", template.getFormTeamTemplate().getFields())
                .render("tasks", template.getWorkFlow().getTasks());
    }

    @Get
    @Route("/work/templates/dialog")
    public Result selDialog() {
        List<WorkTemplateDto> datas = workTemplateRepository
                .findDtoAll(currentUserProvider.get().getId(), currentTeamProvider.get().getId());
        return Results.html()
                .render("datas", datas);
    }

    @Post
    @Route("/work/flow-templater/new")
    @Transactional
    public Result addTemplates(@Param("templateid") Integer templateId) {
        FormTeamTemplate formTeamTemplate = checkEntity(formTeamTemplateRepository.findById(templateId));
        WorkTemplate workTemplate = new WorkTemplate();
        workTemplate.setUserId(currentUserProvider.get().getId());
        workTemplate.setFormTeamTemplate(formTeamTemplate);
        workTemplate.setWorkFlow(new WorkFlow());
        workTemplateRepository.save(workTemplate);
        return Results.json().render(new ResponseJson());
    }

    @Delete
    @Route("/work/flow-templater/{templateid}")
    @Transactional
    public Result delUserTemplates(@PathParam("templateid") Integer templateid) {
        Optional<WorkTemplate> optional = workTemplateRepository.findByFormTeamTemplate(templateid);
        if (optional.isPresent()) {
            workTemplateRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson());
    }

    @Get
    @Route("/work/flow-templater/{templateid}/flows")
    public Result selUser(@PathParam("templateid") Integer templateid) {
        Optional<WorkTemplate> optional = workTemplateRepository.findByFormTeamTemplate(templateid);
        if (optional.isPresent()) {
            workTemplateRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson());
    }

}