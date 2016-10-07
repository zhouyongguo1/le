package le.oa.project.controllers.form;

import le.oa.project.models.Priority;
import le.oa.project.models.Task;

import java.time.format.DateTimeFormatter;

public class TaskJson {

    private Integer id;
    private String name;
    private String content;
    private String status;
    private int points = 1;//任务点数
    private Priority priority = Priority.MEDIUM;
    private Integer ownerId;
    private String owner;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public static TaskJson of(Task task) {
        TaskJson json = new TaskJson();
        json.setId(task.getId());
        json.setName(task.getName());
        json.setContent(task.getContent());
        json.setStatus(task.getStatus().toString());
        json.setPoints(task.getPoints());
        json.setPriority(task.getPriority());

        if (task.getOwner() != null) {
            json.setOwnerId(task.getOwner().getId());
            json.setOwner(task.getOwner().getName());
        } else {
            json.setOwner("");
        }
        if (task.getPlanEndDate() != null) {
            json.setPlanEndDate(task.getPlanEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        return json;
    }
}
