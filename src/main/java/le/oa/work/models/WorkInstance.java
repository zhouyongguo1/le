package le.oa.work.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import le.oa.core.models.User;
import le.oa.core.models.base.BaseModel;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wf_Instance")
public class WorkInstance extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer formId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private Boolean isArchived = false;//是否归档了这个工作流
    @JsonIgnore
    @Column(name = "flow_data")
    @Type(type = "le.jpa.JsonType", parameters = {
            @Parameter(name = "class", value = "le.oa.work.models.WorkFlow")
    })
    private WorkFlow workFlow;

    @Enumerated(EnumType.STRING)
    private WorkInstanceStatus status = WorkInstanceStatus.DOING;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workflowInstance", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WorkInstanceItem> workInstanceItems = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public WorkInstanceStatus getStatus() {
        return status;
    }

    public void setStatus(WorkInstanceStatus status) {
        this.status = status;
    }

    public List<WorkInstanceItem> getWorkInstanceItems() {
        return workInstanceItems;
    }

    public void setWorkInstanceItems(List<WorkInstanceItem> workInstanceItems) {
        this.workInstanceItems = workInstanceItems;
    }
}
