package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.project.controllers.form.TaskForm;
import le.oa.project.controllers.form.TaskJson;
import le.oa.project.models.Project;
import le.oa.project.models.ProjectUser;
import le.oa.project.models.Task;
import le.oa.project.models.TaskStatus;
import le.oa.project.repositories.ProjectRepository;
import le.oa.project.repositories.ProjectUserRepository;
import le.oa.project.repositories.TaskRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController extends BaseTeamController {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private ProjectUserRepository projectUserRepository;

    @Inject
    public TaskController(ProjectRepository projectRepository,
                          TaskRepository taskRepository,
                          ProjectUserRepository projectUserRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectUserRepository = projectUserRepository;
    }

    @Get
    @Route("/project/{id}/tasks")
    public Result index(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        List<Task> tasks = taskRepository.findByNotArchived(projectId);
        List<Task> noneTasks = new ArrayList<>();
        List<Task> startTasks = new ArrayList<>();
        List<Task> finishTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(TaskStatus.NONE)) {
                noneTasks.add(task);
            } else if (task.getStatus().equals(TaskStatus.START)) {
                startTasks.add(task);
            } else if (task.getStatus().equals(TaskStatus.FINISH)) {
                finishTasks.add(task);
            }
        }
        List<ProjectUser> projectUsers = projectUserRepository.findByProjectId(projectId);
        return Results.html()
                .render("project", project)
                .render("projectUsers", projectUsers)
                .render("noneTasks", noneTasks)
                .render("startTasks", startTasks)
                .render("finishTasks", finishTasks);
    }

    @Post
    @Route("/project/{id}/task")
    @Transactional
    public Result save(@PathParam("id") Integer projectId, TaskForm taskForm) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        Task task = taskForm.toTask(project);
        taskRepository.save(task);
        return Results.json()
                .render("task", TaskJson.of(task));
    }

    @Get
    @Route("/project/{id}/task/{taskid}")
    public Result task(@PathParam("id") Integer projectId, @PathParam("taskid") Integer taskId) {
        Task task = this.checkEntity(taskRepository.findById(taskId));
        return Results.html()
                .render("project", task.getProject())
                .render("task", task);
    }

    @Post
    @Route("/project/{id}/task/{taskid}/update-status")
    @Transactional
    public Result updateStatus(@PathParam("taskid") Integer taskId, @Param("status") String status) {
        Task task = this.checkEntity(taskRepository.findById(taskId));
        if (status.equals("NONE")) {
            task.setStatus(TaskStatus.START);
        } else if (status.equals("START")) {
            task.setStatus(TaskStatus.FINISH);
        } else if (status.equals("FINISH")) {
            task.setIsArchived(true);
        }
        taskRepository.save(task);
        return Results.json()
                .render("task", TaskJson.of(task));
    }

}
