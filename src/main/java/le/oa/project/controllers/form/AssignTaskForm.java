package le.oa.project.controllers.form;

import com.google.common.base.Strings;
import le.oa.core.models.User;
import le.oa.project.models.Priority;
import le.oa.project.models.Task;
import le.oa.project.models.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AssignTaskForm {
    private Integer id;
    private Integer ownerId;
    private String planEndDate;
    private int points = 1;//任务点数
    private Priority priority = Priority.MEDIUM;
    private TaskStatus status = TaskStatus.NONE;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Task valOf(Task task) {
        task.setPoints(points);
        if (ownerId != null) {
            User user = new User();
            user.setId(ownerId);
            task.setOwner(user);
        }
        if (!Strings.isNullOrEmpty(planEndDate)) {
            LocalDate dateTime = LocalDate.parse(planEndDate, DateTimeFormatter.ISO_LOCAL_DATE);
            task.setPlanEndDate(dateTime);
        }
        task.setStatus(status);
        task.setPriority(priority);
        return task;
    }

}
