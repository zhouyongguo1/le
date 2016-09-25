package le.oa.work.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.work.models.FormTemplate;
import le.oa.work.models.FormType;
import le.oa.work.repositories.FormTemplateRepository;
import le.oa.work.repositories.FormTypeRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Delete;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

import java.util.List;
import java.util.Optional;

@Controller
public class FormTypeController extends BaseTeamController {

    private FormTypeRepository formTypeRepository;
    private FormTemplateRepository templateRepository;

    @Inject
    public FormTypeController(FormTypeRepository formTypeRepository,
                              FormTemplateRepository templateRepository) {
        this.formTypeRepository = formTypeRepository;
        this.templateRepository = templateRepository;
    }

    @Post
    @Transactional
    @Route("/work/type")
    public Result save(@Param("id") Integer id, @Param("name") String name) {
        FormType formType = null;
        if (id != null) {
            Optional<FormType> opt = formTypeRepository.findById(id);
            if (opt.isPresent()) {
                formType = opt.get();
            }
        }
        if (formType == null) {
            formType = new FormType();
        }
        formType.setName(name);
        formTypeRepository.save(formType);
        return Results.json().render(new ResponseJson(true, "分类保存成功"));
    }

    @Delete
    @Route("/work/type/{id}")
    @Transactional
    public Result delete(@PathParam("id") Integer id) {
        List<FormTemplate> templates = templateRepository.findByType(id);
        if (templates.size() > 0) {
            return Results.json().render(new ResponseJson(false, "分类存在表单,不能删除"));
        } else {
            Optional<FormType> optional = formTypeRepository.findById(id);
            if (optional.isPresent()) {
                FormType formType = optional.get();
                formTypeRepository.delete(formType);
            }
            return Results.json().render(new ResponseJson(true, "分类删除成功"));
        }
    }
}
