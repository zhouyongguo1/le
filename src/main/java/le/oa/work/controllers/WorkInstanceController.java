package le.oa.work.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.models.search.Pagination;
import le.oa.core.models.search.ResultData;
import le.oa.work.controllers.form.InstanceData;
import le.oa.work.models.Form;
import le.oa.work.models.WorkInstance;
import le.oa.work.models.WorkInstanceItem;
import le.oa.work.models.search.WorkInstanceSearch;
import le.oa.work.repositories.FormRepository;
import le.oa.work.repositories.FormTemplateRepository;
import le.oa.work.repositories.WorkInstanceItemRepository;
import le.oa.work.repositories.WorkInstanceRepository;
import le.oa.work.repositories.WorkTemplateRepository;
import le.oa.work.service.WorkInstanceService;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.session.FlashScope;

import java.io.IOException;
import java.util.List;

@Controller
public class WorkInstanceController extends BaseTeamController {

    private WorkTemplateRepository workTemplateRepository;
    private FormTemplateRepository formTemplateRepository;
    private FormRepository formRepository;
    private WorkInstanceRepository workInstanceRepository;
    private WorkInstanceItemRepository instanceItemRepository;

    private WorkInstanceService workInstanceService;

    @Inject
    public WorkInstanceController(WorkTemplateRepository workTemplateRepository,
                                  FormTemplateRepository formTemplateRepository,
                                  FormRepository formRepository,
                                  WorkInstanceRepository workInstanceRepository,
                                  WorkInstanceItemRepository instanceItemRepository,
                                  WorkInstanceService workInstanceService) {

        this.workTemplateRepository = workTemplateRepository;
        this.formTemplateRepository = formTemplateRepository;
        this.formRepository = formRepository;
        this.workInstanceRepository = workInstanceRepository;
        this.instanceItemRepository = instanceItemRepository;
        this.workInstanceService = workInstanceService;
    }


    @Get
    @Route("/work")
    public Result index(WorkInstanceSearch search) {
        ResultData<WorkInstanceItem> result = instanceItemRepository.findBySearch(search);
        return Results.html().render("search", search)
                .render("pagination", new Pagination(result.getTotalCount(),
                        search.getPageSize(), search.getPageIndex()))
                .render("items", result.getList());
    }

    @Get
    @Route("/work/instance/new")
    public Result add(@Param("template-id") Integer templateId) {
        return Results.html();
    }

    @Get
    @Route("/work/instance/{id}")
    public Result process(@PathParam("id") Integer id) throws JsonProcessingException {
        WorkInstance instance = checkEntity(workInstanceService.findInstance(id));
        Form form = checkEntity(formRepository.findById(instance.getFormId()));
        InstanceData instanceData = InstanceData.of(instance, form);
        
        List<WorkInstanceItem> items = instanceItemRepository.findByInstanceId(id);
        return Results.html()
                .render("instance", instanceData)
                .render("items", items);
    }

    @Post
    @Route("/work/instance")
    @Transactional
    public Result create(InstanceData data,
                         FlashScope flashScope) throws IOException {
        Form form = data.toForm();
        formRepository.save(form);
        WorkInstance instance = data.toWorkInstance(form, currentUserProvider.get());
        workInstanceService.startInstance(instance);
        flashScope.success("工作申请成功");
        return this.redirect("/");
    }


    @Get
    @Route("/work/instance/archived")
    public Result archived() {
        return Results.html();
    }
}
