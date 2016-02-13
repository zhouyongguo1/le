package le.oa.project.controllers.form;

import com.google.common.base.Strings;
import le.oa.core.models.User;
import le.oa.project.models.Project;
import le.oa.project.models.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskForm {
    private Integer id;
    private String name;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Task toTask(Project project) {
        Task task = new Task();
        task.setProject(project);
        task.setId(id);
        task.setName(name);
        if (ownerId != null) {
            User user = new User();
            user.setId(ownerId);
            user.setName(owner);
            task.setOwner(user);
        }
        if (!Strings.isNullOrEmpty(planEndDate)) {
            LocalDate dateTime = LocalDate.parse(planEndDate, DateTimeFormatter.ISO_LOCAL_DATE);
            task.setPlanEndDate(dateTime);
        }
        return task;
    }
}
