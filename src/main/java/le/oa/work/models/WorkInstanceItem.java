package le.oa.work.models;

import le.oa.core.models.User;
import le.oa.core.models.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wf_Instance_item")
public class WorkInstanceItem extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String taskId;
    @ManyToOne
    @JoinColumn(name = "instance_id")
    private WorkInstance workInstance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "send_user_id")
    private User sendUser;

    @Enumerated(EnumType.STRING)
    private WorkInstanceItemStatus status = WorkInstanceItemStatus.START;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public WorkInstance getWorkInstance() {
        return workInstance;
    }

    public void setWorkInstance(WorkInstance workInstance) {
        this.workInstance = workInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WorkInstanceItemStatus getStatus() {
        return status;
    }

    public void setStatus(WorkInstanceItemStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }
}
