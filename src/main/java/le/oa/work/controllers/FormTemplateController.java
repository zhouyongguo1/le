package le.oa.work.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.work.models.FormTeamTemplate;
import le.oa.work.repositories.FormTeamTemplateRepository;
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
public class FormTemplateController extends BaseTeamController {
    private FormTeamTemplateRepository templateRepository;

    @Inject
    public FormTemplateController(FormTeamTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;

    }
    
    @Get
    @Route("/form/template/{id}")
    public Result getTemplate(@PathParam("id") Integer id) {
        FormTeamTemplate template = this.checkEntity(templateRepository.findById(id));
        return Results.json().render(template)
                .render("id", template.getId())
                .render("name", template.getName())
                .render("fields", template.getFields());
    }
    
    @Get
    @Route("/work/template")
    @Transactional
    public Result index() {
        List<FormTeamTemplate> list = templateRepository.findAll();
        return Results.html()
                .render("list", list);
    }

   


    @Get
    @Route("/work/template/new")
    public Result add() {
        return Results.html();
    }

    @Get
    @Route("/work/template/{id}/edit")
    public Result edit(@PathParam("id") Integer id) {
        FormTeamTemplate template = this.checkEntity(templateRepository.findById(id));
        return Results.html()
                .render("template", template);
    }

    @Post
    @Route("/work/template")
    @Transactional
    public Result create(@Param("templateid") Integer templateId,
                         @Param("name") String name, @Param("json") String json,
                         FlashScope flashScope) {
        FormTeamTemplate template = new FormTeamTemplate();
        template.setName(name);
        template.setFields(json);
        templateRepository.save(template);
        flashScope.success("表单保存成功");
        return this.redirect("/");
    }


}
