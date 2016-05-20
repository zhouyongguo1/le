package le.oa.work.models;

import le.oa.core.models.base.TeamModel;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "wf_template")
public class WorkTemplate extends TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "team_template_id")
    private FormTeamTemplate formTeamTemplate;

    @Column(name = "data")
    @Type(type = "le.jpa.JsonType", parameters = {
            @Parameter(name = "class", value = "le.oa.work.models.WorkFlow")
    })
    private WorkFlow workFlow=new WorkFlow();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public FormTeamTemplate getFormTeamTemplate() {
        return formTeamTemplate;
    }

    public void setFormTeamTemplate(FormTeamTemplate formTeamTemplate) {
        this.formTeamTemplate = formTeamTemplate;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }
}