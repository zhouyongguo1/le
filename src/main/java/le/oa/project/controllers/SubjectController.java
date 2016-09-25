package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.core.models.Status;
import le.oa.project.controllers.form.SubjectForm;
import le.oa.project.controllers.form.SubjectItemForm;
import le.oa.project.models.Project;
import le.oa.project.models.Subject;
import le.oa.project.models.SubjectItem;
import le.oa.project.repositories.ProjectRepository;
import le.oa.project.repositories.SubjectItemRepository;
import le.oa.project.repositories.SubjectRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Delete;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.FlashScope;

import java.util.List;
import java.util.Optional;

@Controller
public class SubjectController extends BaseTeamController {

    private ProjectRepository projectRepository;
    private SubjectRepository subjectRepository;
    private SubjectItemRepository subjectItemRepository;

    @Inject
    public SubjectController(ProjectRepository projectRepository,
                             SubjectRepository subjectRepository,
                             SubjectItemRepository subjectItemRepository) {
        this.projectRepository = projectRepository;
        this.subjectRepository = subjectRepository;
        this.subjectItemRepository = subjectItemRepository;
    }

    @Get
    @Route("/project/{id}/subjects")
    public Result index(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        return Results.html()
                .render("project", project);
    }

    @Get
    @Route("/project/{id}/subjects/items")
    public Result subjects(@PathParam("id") Integer projectId) {
        List<Subject> subjects = subjectRepository.findByStatus(projectId, Status.ACTIVE);
        return Results.html()
                .render("subjects", subjects);
    }


    @Delete
    @Route("/project/{id}/subjects/{subjectId}")
    @Transactional
    public Result delSubject(@PathParam("subjectId") Integer id) {
        List<SubjectItem> subjectItems = subjectItemRepository.findBySubjectId(id);
        subjectItems.forEach(subjectItemRepository::delete);
        Optional<Subject> optional = subjectRepository.findById(id);
        if (optional.isPresent()) {
            subjectRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson(true, "讨论主题删除成功"));
    }

    @Post
    @Route("/project/{id}/subject")
    @Transactional
    public Result saveSubject(@PathParam("id") Integer projectId, FlashScope flashScope, SubjectForm subjectForm) {
        Subject subject = subjectForm.toSubject(null);
        subject.setProjectId(projectId);
        if (subject.getId() == null) {
            subjectRepository.save(subject);
        } else {
            subjectRepository.save(subject);
        }
        flashScope.success("建立讨论主题成功");
        return this.redirect("/project/" + projectId + "/subjects");
    }

    @Get
    @Route("/project/{id}/subject/{subjectId}")
    public Result subject(@PathParam("id") Integer projectId, @PathParam("subjectId") Integer subjectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        Subject subject = this.checkEntity(subjectRepository.findById(subjectId));
        return Results.html()
                .render("subject", subject)
                .render("project", project);

    }

    @Get
    @Route("/project/{id}/subject/{subjectId}/items")
    public Result subjectitems(@PathParam("id") Integer projectId, @PathParam("subjectId") Integer subjectId) {
        List<SubjectItem> subjectItems = subjectItemRepository.findBySubjectId(subjectId);
        return Results.html()
                .render("subjectItems", subjectItems);
    }

    @Delete
    @Route("/project/{id}/subject/{subjectId}/items/{itemId}")
    @Transactional
    public Result delSubjectItem(@PathParam("itemId") Integer id) {
        Optional<SubjectItem> optional = subjectItemRepository.findById(id);
        if (optional.isPresent()) {
            subjectItemRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson(true, "讨论删除成功"));
    }

    @Post
    @Route("/project/{id}/subject/{subjectId}/items")
    @Transactional
    public Result saveSubjectItem(@PathParam("id") Integer projectId,
                                  @PathParam("subjectId") Integer subjectId,
                                  SubjectItemForm subjectItemForm) {
        SubjectItem subjectItem = subjectItemForm.toSubjectItem();
        subjectItem.setProjectId(projectId);
        subjectItem.setSubjectId(subjectId);
        subjectItemRepository.save(subjectItem);
        return Results.json()
                .render(new ResponseJson(true, "发表建议成功"));
    }


}
