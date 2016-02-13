package le.oa.project.models;

import le.oa.core.models.Status;
import le.oa.core.models.User;
import le.oa.core.models.base.TeamModel;

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
@Table(name = "pro_user")
public class ProjectUser extends TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer projectId;
    @Enumerated(EnumType.STRING)
    private ProjectRole role = ProjectRole.MEMBER;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isOwner = false;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
