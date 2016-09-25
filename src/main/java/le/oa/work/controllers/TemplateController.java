package le.oa.work.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.work.models.FormTemplate;
import le.oa.work.models.FormType;
import le.oa.work.repositories.FormTemplateRepository;
import le.oa.work.repositories.FormTypeRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.session.FlashScope;

import java.util.List;

@Controller
public class TemplateController extends BaseTeamController {
    private FormTemplateRepository templateRepository;
    private FormTypeRepository formTypeRepository;

    @Inject
    public TemplateController(FormTemplateRepository templateRepository,
                              FormTypeRepository formTypeRepository) {
        this.templateRepository = templateRepository;
        this.formTypeRepository = formTypeRepository;
    }

    @Get
    @Route("/work/flows")
    public Result index() {
        return Results.html();
    }

    @Get
    @Route("/work/templates")
    public Result templates() {
        List<FormType> types = formTypeRepository.findTypes();
        List<FormTemplate> list = templateRepository.findAll();
        return Results.html()
                .render("list", list)
                .render("types", types);
    }

    @Get
    @Route("/work/templates/new")
    public Result add() {
        List<FormType> types = formTypeRepository.findTypes();
        return Results.html().render("types", types);
    }

    @Post
    @Route("/work/templates")
    @Transactional
    public Result create(@Param("templateid") Integer templateId,
                         @Param("formTypeId") Integer formTypeId,
                         @Param("name") String name, @Param("json") String json,
                         FlashScope flashScope) {
        FormType formType = checkEntity(formTypeRepository.findById(formTypeId));
        FormTemplate template = new FormTemplate();
        template.setName(name);
        template.setFields(json);
        template.setFormType(formType);
        templateRepository.save(template);
        flashScope.success("表单保存成功");
        return this.redirect("/work/flows");
    }

    @Get
    @Route("/form/templates/{id}")
    public Result getTemplate(@PathParam("id") Integer id) {
        FormTemplate template = this.checkEntity(templateRepository.findById(id));
        return Results.json().render(template)
                .render("id", template.getId())
                .render("name", template.getName())
                .render("type", template.getFormType())
                .render("fields", template.getFields());
    }

    @Get
    @Route("/work/templates/{id}/edit")
    public Result edit(@PathParam("id") Integer id) {
        FormTemplate template = this.checkEntity(templateRepository.findById(id));
        return Results.html()
                .render("template", template);
    }
}