package le.oa.project.controllers.form;

import com.google.common.base.Strings;
import le.oa.core.models.User;
import le.oa.project.models.Priority;
import le.oa.project.models.Project;
import le.oa.project.models.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskForm {
    private Integer id;
    private String name;
    private String content;
    private int points = 1;//任务点数
    private Priority priority = Priority.MEDIUM;
    private Integer ownerId;
    private String planEndDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Task valOf(Task task) {
        task.setName(name);
        task.setContent(content);
        task.setPriority(priority);
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
        return task;
    }

    public Task toTask(Project project) {
        Task task = new Task();
        task.setProject(project);
        return valOf(task);
    }
}
