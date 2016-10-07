package le.oa.project.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.core.models.CheckStatus;
import le.oa.project.models.Task;
import le.oa.project.models.TaskCheck;
import le.oa.project.repositories.TaskCheckRepository;
import le.oa.project.repositories.TaskRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Delete;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import le.web.annotation.http.Put;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskCheckController extends BaseTeamController {

    private TaskRepository taskRepository;
    private TaskCheckRepository taskCheckRepository;

    @Inject
    public TaskCheckController(TaskRepository taskRepository,
                               TaskCheckRepository taskCheckRepository) {
        this.taskRepository = taskRepository;
        this.taskCheckRepository = taskCheckRepository;
    }

    @Get
    @Route("/project/{id}/tasks/{taskid}/check")
    public Result taskChecks(@PathParam("taskid") Integer taskId) {
        List<TaskCheck> checks = taskCheckRepository.findByTaskId(taskId);
        return Results.html().render("checks", checks);
    }

    @Post
    @Transactional
    @Route("/project/{id}/tasks/{taskid}/check")
    public Result taskCheckSave(@PathParam("taskid") Integer taskId,
                                @Param("content") String content) {
        Task task = this.checkEntity(taskRepository.findById(taskId));
        TaskCheck check = new TaskCheck();
        check.setContent(content);
        check.setTask(task);
        check.setStatus(CheckStatus.NONE);
        taskCheckRepository.save(check);
        return Results.json().render(new ResponseJson(true, "任务检查项保存成功"));
    }

    @Put
    @Transactional
    @Route("/project/{id}/tasks/{taskid}/check/{checkId}")
    public Result taskCheckUpdate(@PathParam("checkId") Integer checkId,
                                  @Param("status") String status) {
        TaskCheck check = checkEntity(taskCheckRepository.findById(checkId));
        check.setStatus(CheckStatus.valueOf(status));
        taskCheckRepository.save(check);
        return Results.json().render(new ResponseJson(true, "任务检查项修改成功"));
    }

    @Delete
    @Route("/project/{id}/tasks/{taskid}/check/{checkId}")
    @Transactional
    public Result taskCheckDel(@PathParam("checkId") Integer checkId) {
        Optional<TaskCheck> optional = taskCheckRepository.findById(checkId);
        if (optional.isPresent()) {
            taskCheckRepository.delete(optional.get());
        }
        return Results.json().render(new ResponseJson(true, "任务检查项删除成功"));
    }
}
