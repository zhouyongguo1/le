package le.oa.work.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.work.models.FormTemplate;
import le.oa.work.models.WorkFlow;
import le.oa.work.models.WorkFlowTask;
import le.oa.work.models.UserTemplate;
import le.oa.work.models.dto.WorkTemplateDto;
import le.oa.work.repositories.FormTemplateRepository;
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
    private FormTemplateRepository formTemplateRepository;

    @Inject
    public WorkTemplateController(WorkTemplateRepository workTemplateRepository,
                                  FormTemplateRepository formTemplateRepository) {
        this.workTemplateRepository = workTemplateRepository;
        this.formTemplateRepository = formTemplateRepository;
    }

    @Get
    @Route("/work/user-templates")
    public Result templates() {
        List<UserTemplate> datas = workTemplateRepository.findByUser(currentUserProvider.get().getId());
        return Results.html().render("datas", datas);
    }

    @Get
    @Route("/work/user-templates/{id}/json")
    public Result template(@PathParam("id") Integer id) {
        UserTemplate template = checkEntity(workTemplateRepository.findById(id));
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
                .render("templateId", template.getFormTemplate().getId())
                .render("templateName", template.getFormTemplate().getName())
                .render("fields", template.getFormTemplate().getFields())
                .render("tasks", template.getWorkFlow().getTasks());
    }

    @Get
    @Route("/work/user-templates/dialog")
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
        FormTemplate formTemplate = checkEntity(formTemplateRepository.findById(templateId));
        UserTemplate workTemplate = new UserTemplate();
        workTemplate.setUserId(currentUserProvider.get().getId());
        workTemplate.setFormTemplate(formTemplate);
        workTemplate.setWorkFlow(new WorkFlow());
        workTemplateRepository.save(workTemplate);
        return Results.json().render(new ResponseJson());
    }

    @Delete
    @Route("/work/flow-templater/{templateid}")
    @Transactional
    public Result delUserTemplates(@PathParam("templateid") Integer templateid) {
        Optional<UserTemplate> optional = workTemplateRepository.findByFormTeamTemplate(templateid);
        if (optional.isPresent()) {
            workTemplateRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson());
    }

    @Get
    @Route("/work/flow-templater/{templateid}/flows")
    public Result selUser(@PathParam("templateid") Integer templateid) {
        Optional<UserTemplate> optional = workTemplateRepository.findByFormTeamTemplate(templateid);
        if (optional.isPresent()) {
            workTemplateRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson());
    }

}