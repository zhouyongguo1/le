package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.project.models.Doc;
import le.oa.project.models.Project;
import le.oa.project.repositories.DocRepository;
import le.oa.project.repositories.ProjectRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import le.web.annotation.http.Put;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.session.FlashScope;

import java.util.List;

@Controller
public class DocController extends BaseTeamController {

    private ProjectRepository projectRepository;
    private DocRepository docRepository;

    @Inject
    public DocController(ProjectRepository projectRepository,
                         DocRepository docRepository) {
        this.projectRepository = projectRepository;
        this.docRepository = docRepository;
    }

    @Get
    @Route("/project/{id}/docs")
    public Result index(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        List<Doc> docs = docRepository.findByProject(projectId);
        return Results.html()
                .render("docs", docs)
                .render("project", project);
    }

    @Get
    @Route("/project/{id}/docs/{docId}")
    public Result view(@PathParam("id") Integer projectId, @PathParam("docId") Integer docId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        Doc doc = this.checkEntity(docRepository.findById(docId));
        return Results.html()
                .render("project", project)
                .render("doc", doc);
    }

    @Get
    @Route("/project/{id}/docs/new")
    public Result add(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        return Results.html()
                .render("project", project);
    }


    @Get
    @Route("/project/{id}/docs/{docId}/edit")
    public Result edit(@PathParam("id") Integer projectId, @PathParam("docId") Integer docId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        Doc doc = this.checkEntity(docRepository.findById(docId));
        return Results.html()
                .render("project", project)
                .render("doc", doc);
    }


    @Post
    @Route("/project/{id}/docs")
    @Transactional
    public Result save(@PathParam("id") Integer projectId, FlashScope flashScope,
                       @Param("name") String name,
                       @Param("content") String content) {
        Doc doc = new Doc();
        doc.setProjectId(projectId);
        doc.setName(name);
        doc.setContent(content);
        docRepository.save(doc);
        flashScope.success("建立文档成功");
        return this.redirect("/project/" + projectId + "/docs");
    }

    @Put
    @Route("/project/{id}/docs/{docId}")
    @Transactional
    public Result update(@PathParam("id") Integer projectId, @PathParam("docId") Integer docId,
                         FlashScope flashScope,
                         @Param("name") String name,
                         @Param("content") String content) {

        Doc doc = this.checkEntity(docRepository.findById(docId));
        doc.setName(name);
        doc.setContent(content);
        docRepository.update(doc);
        flashScope.success("修改文档成功");
        return this.redirect("/project/" + projectId + "/docs");
    }
}
