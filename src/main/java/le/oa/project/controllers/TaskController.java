package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.core.models.User;
import le.oa.project.controllers.form.AssignTaskForm;
import le.oa.project.controllers.form.TaskForm;
import le.oa.project.controllers.form.TaskJson;
import le.oa.project.models.Project;
import le.oa.project.models.ProjectUser;
import le.oa.project.models.Task;
import le.oa.project.models.TaskStatus;
import le.oa.project.repositories.ProjectRepository;
import le.oa.project.repositories.ProjectUserRepository;
import le.oa.project.repositories.TaskCheckRepository;
import le.oa.project.repositories.TaskRepository;
import le.util.DateTimeUtils;
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
import ninja.validation.Validation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController extends BaseTeamController {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private ProjectUserRepository projectUserRepository;
    private TaskCheckRepository taskCheckRepository;

    @Inject
    public TaskController(ProjectRepository projectRepository,
                          TaskRepository taskRepository,
                          ProjectUserRepository projectUserRepository,
                          TaskCheckRepository taskCheckRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectUserRepository = projectUserRepository;
        this.taskCheckRepository = taskCheckRepository;
    }

    @Get
    @Route("/project/{id}/tasks")
    public Result index(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        List<ProjectUser> projectUsers = projectUserRepository.findByProjectId(projectId);
        List<Task> task = taskRepository.findByNotFinish(projectId);
        return Results.html()
                .render("project", project)
                .render("projectUsers", projectUsers)
                .render("noneTasks", task.stream().filter(m -> m.getStatus().equals(TaskStatus.NONE)))
                .render("runTasks", task.stream().filter(m -> m.getStatus().equals(TaskStatus.START)));
    }

    @Get
    @Route("/project/{id}/none-tasks")
    public Result noneTasks(@PathParam("id") Integer projectId) {
        List<Task> task = taskRepository.findByNotFinish(projectId);
        List<Task> nones = task.stream().filter(m -> m.getStatus().equals(TaskStatus.NONE))
                .collect(Collectors.toList());
        List<Task> runs = task.stream().filter(m -> m.getStatus().equals(TaskStatus.START))
                .collect(Collectors.toList());
        return Results.html()
                .render("noneTasks", nones)
                .render("runTasks", runs);
    }

    @Get
    @Route("/project/{id}/finish-tasks")
    public Result finishTasks(@PathParam("id") Integer projectId, @Param("interval") String queryInterval) {
        LocalDateTime now = DateTimeUtils.utcNow();
        LocalDateTime begin, end;
        if ("month".equals(queryInterval)) {
            begin = now.minusDays(now.getDayOfMonth());
            end = begin.plusMonths(1);
        } else {
            begin = now.minusDays(now.getDayOfWeek().getValue());
            end = begin.plusDays(7);
        }
        List<Task> tasks = taskRepository.findFinishByDate(projectId, begin.toLocalDate(), end.toLocalDate());
        List<User> users = tasks.stream().map(m -> m.getOwner()).distinct().collect(Collectors.toList());
        return Results.html()
                .render("users", users)
                .render("tasks", tasks);
    }

    @Get
    @Route("/project/{id}/tasks/new")
    public Result add(@PathParam("id") Integer projectId) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        List<ProjectUser> projectUsers = projectUserRepository.findByProjectId(projectId);
        return Results.html()
                .render("project", project)
                .render("users", projectUsers.stream().
                        map(m -> m.getUser()).
                        collect(Collectors.toList()));
    }

    @Post
    @Route("/project/{id}/tasks")
    @Transactional
    public Result saveTask(@PathParam("id") Integer projectId, TaskForm taskForm, FlashScope flashScope) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        Task task = taskForm.toTask(project);
        taskRepository.save(task);
        flashScope.success("数据保存成功");
        return this.redirect(String.format("/project/%d/tasks", projectId));
    }

    @Put
    @Route("/project/{id}/tasks/{taskid}/assign")
    @Transactional
    public Result assignTask(@PathParam("taskid") Integer taskId,
                             AssignTaskForm form) {
        Task task = this.checkEntity(taskRepository.findById(taskId));
        if (task.getDel() || TaskStatus.END.equals(task.getStatus())) {
            return Results.json()
                    .render(new ResponseJson(false, "任务修改失败"));
        } else {
            if ((TaskStatus.START.equals(task.getStatus()) || TaskStatus.NONE.equals(task.getStatus()))
                    && (TaskStatus.END.equals(form.getStatus()) || TaskStatus.FINISH.equals(form.getStatus()))) {
                task.setEndDate(DateTimeUtils.utcNow().toLocalDate());
            } else if (TaskStatus.START.equals(form.getStatus()) || TaskStatus.NONE.equals(form.getStatus())) {
                task.setEndDate(null);
            }
            form.valOf(task);
            taskRepository.save(task);
            return Results.json()
                    .render(new ResponseJson(true, "任务修改成功"));
        }
    }

    @Get
    @Route("/project/{id}/tasks/{taskid}")
    public Result task(@PathParam("id") Integer projectId, @PathParam("taskid") Integer taskId) {
        Task task = this.checkEntity(taskRepository.findById(taskId));
        return Results.html()
                .render("project", task.getProject())
                .render("task", task);
    }

    //--------
    @Put
    @Route("/project/{id}/tasks")
    @Transactional
    public Result update(@PathParam("id") Integer projectId, TaskForm taskForm, Validation validation,
                         FlashScope flashScope) {
        Project project = this.checkEntity(projectRepository.findById(projectId));
        Task task = checkEntity(taskRepository.findById(taskForm.getId()));
        taskRepository.save(taskForm.valOf(task));
        flashScope.success("数据保存成功");
        return this.redirect(String.format("/project/%d/tasks", projectId));
    }


    @Post
    @Route("/project/{id}/tasks/{taskid}/update-status")
    @Transactional
    public Result updateStatus(@PathParam("taskid") Integer taskId, @Param("status") String status) {
        Task task = this.checkEntity(taskRepository.findById(taskId));
        if (status.equals("NONE")) {
            task.setStatus(TaskStatus.START);
        } else if (status.equals("START")) {
            task.setStatus(TaskStatus.FINISH);
        } else if (status.equals("FINISH")) {
        }
        taskRepository.save(task);
        return Results.json()
                .render("task", TaskJson.of(task));
    }


}
