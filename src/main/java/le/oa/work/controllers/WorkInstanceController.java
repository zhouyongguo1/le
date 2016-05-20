package le.oa.work.controllers;


import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.work.controllers.form.InstanceData;
import le.oa.work.models.Form;
import le.oa.work.models.WorkInstance;
import le.oa.work.models.WorkTemplate;
import le.oa.work.repositories.FormRepository;
import le.oa.work.repositories.FormTeamTemplateRepository;
import le.oa.work.repositories.WorkInstanceRepository;
import le.oa.work.repositories.WorkTemplateRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.FlashScope;

import java.io.IOException;

@Controller
public class WorkInstanceController extends BaseTeamController {

    private WorkTemplateRepository workTemplateRepository;
    private FormTeamTemplateRepository formTeamTemplateRepository;
    private FormRepository formRepository;
    private WorkInstanceRepository workInstanceRepository;

    @Inject
    public WorkInstanceController(WorkTemplateRepository workTemplateRepository,
                                  FormTeamTemplateRepository formTeamTemplateRepository,
                                  FormRepository formRepository,
                                  WorkInstanceRepository workInstanceRepository) {

        this.workTemplateRepository = workTemplateRepository;
        this.formTeamTemplateRepository = formTeamTemplateRepository;
        this.formRepository = formRepository;
        this.workInstanceRepository = workInstanceRepository;
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
    @Route("/work/instance/new")
    public Result add(@Param("template-id") Integer templateId) {
//        WorkTemplate template = this.checkEntity(workTemplateRepository.findById(templateId));
        return Results.html();
    }

    @Post
    @Route("/work/instance")
    @Transactional
    public Result create(InstanceData data,
                         FlashScope flashScope) throws IOException {
        Form form = data.toForm();
        formRepository.save(form);
        WorkInstance instance = data.toWorkInstance(form, currentUserProvider.get());
        workInstanceRepository.save(instance);
        flashScope.success("工作申请成功");
        return this.redirect("/");
    }


    @Get
    @Route("/work/instance/archived")
    public Result archived() {
        return Results.html();
    }
}
