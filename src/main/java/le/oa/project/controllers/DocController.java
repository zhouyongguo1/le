package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.project.controllers.form.SubjectForm;
import le.oa.project.models.Project;
import le.oa.project.models.Subject;
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

@Controller
public class DocController extends BaseTeamController {

    private ProjectRepository projectRepository;
    private SubjectRepository subjectRepository;
    private SubjectItemRepository subjectItemRepository;

    @Inject
    public DocController(ProjectRepository projectRepository,
                         SubjectRepository subjectRepository,
                         SubjectItemRepository subjectItemRepository) {
        this.projectRepository = projectRepository;
        this.subjectRepository = subjectRepository;
        this.subjectItemRepository = subjectItemRepository;
    }

    @Get
    @Route("/project/{id}/docs")
    public Result index(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        return Results.html()
                .render("project", project);
    }

    @Get
    @Route("/project/{id}/doc/{docId}")
    public Result subject(@PathParam("id") Integer projectId, @PathParam("docId") Integer docId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        Subject subject = this.checkEntity(subjectRepository.findById(docId));
        return Results.html()
                .render("subject", subject)
                .render("project", project);
    }

    @Post
    @Route("/project/{id}/doc")
    @Transactional
    public Result saveSubject(@PathParam("id") Integer projectId, FlashScope flashScope, SubjectForm subjectForm) {
        flashScope.success("建立讨论主题成功");
        return this.redirect("/project/" + projectId + "/subjects");
    }
}
