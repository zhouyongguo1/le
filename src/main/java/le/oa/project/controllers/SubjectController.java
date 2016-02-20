package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.models.Status;
import le.oa.core.models.form.ResponseJson;
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
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.FlashScope;

import java.util.List;

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
        List<Subject> subjects = subjectRepository.findByStatus(projectId, Status.ACTIVE);
        return Results.html()
                .render("subjects", subjects)
                .render("project", project);
    }

    @Post
    @Route("/project/{id}/subject")
    @Transactional
    public Result saveSubject(@PathParam("id") Integer projectId, FlashScope flashScope, SubjectForm subjectForm) {
        Subject subject = subjectForm.toSubject(null);
        subject.setProjectId(projectId);
        subjectRepository.save(subject);
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
    public Result subjectItems(@PathParam("id") Integer projectId, @PathParam("subjectId") Integer subjectId) {
        List<SubjectItem> items = subjectItemRepository.findByStatus(subjectId, Status.ACTIVE);
        return Results.html()
                .render("items", items);
    }

    @Post
    @Route("/project/{id}/subject/{subjectId}")
    @Transactional
    public Result saveSubjectItem(@PathParam("id") Integer projectId,
                                  @PathParam("subjectId") Integer subjectId,
                                  SubjectItemForm subjectItemForm) {
        SubjectItem subjectItem = subjectItemForm.toSubjectItem();
        subjectItem.setProjectId(projectId);
        subjectItem.setOwnerId(subjectId);
        subjectItemRepository.save(subjectItem);
        return Results.json()
                .render(new ResponseJson(true, "发表建议成功"));
    }
    
    
}
