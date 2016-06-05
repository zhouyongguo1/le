package le.oa.project.controllers;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.core.repositories.UserRepository;
import le.oa.project.models.Project;
import le.oa.project.models.ProjectRole;
import le.oa.project.models.ProjectUser;
import le.oa.project.repositories.ProjectRepository;
import le.oa.project.repositories.ProjectUserRepository;
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
public class UserController extends BaseTeamController {

    private ProjectRepository projectRepository;
    private ProjectUserRepository projectUserRepository;
    private UserRepository userRepository;

    @Inject
    public UserController(ProjectRepository projectRepository,
                          ProjectUserRepository projectUserRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
        this.userRepository = userRepository;
    }

    @Get
    @Route("/project/{id}/users")
    public Result index(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        List<ProjectUser> users = projectUserRepository.findByProjectId(projectId);
        return Results.html()
                .render("project", project)
                .render("users", users);
    }


    @Post
    @Route("/project/{id}/users")
    @Transactional
    public Result save(@PathParam("id") Integer projectId,
                       @Param("userids") String userIds,
                       @Param("role") String role) {
        String[] ids = userIds.split(",");
        ProjectUser user = null;
        for (String id : ids) {
            if (Strings.isNullOrEmpty(id.trim())) {
                continue;
            }

            Integer userId = Integer.valueOf(id.trim());
            Optional<ProjectUser> optional = projectUserRepository.findByUserId(projectId, userId);
            if (optional.isPresent()) {
                user = optional.get();
                user.setRole(ProjectRole.valueOf(role));
                projectUserRepository.save(user);
            } else {
                user = new ProjectUser();
                user.setProjectId(projectId);
                user.setUser(userRepository.findUserById(userId).get());
                user.setRole(ProjectRole.valueOf(role));
                projectUserRepository.save(user);
            }
        }
        return Results.json().render(new ResponseJson(true, "项目成员保存成功"));
    }

    @Delete
    @Route("/project/{id}/users")
    @Transactional
    public Result delete(@PathParam("id") Integer projectId,
                         @Param("users") Integer userId) {
        Optional<ProjectUser> optional = projectUserRepository.findByUserId(projectId, userId);
        if (optional.isPresent()) {
            projectUserRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson(true, "项目成员移除成功"));
    }
}
