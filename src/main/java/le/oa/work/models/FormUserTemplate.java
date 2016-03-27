package le.oa.work.models;

import le.oa.core.models.User;
import le.oa.core.models.base.BaseModel;
import le.oa.core.models.base.TeamModel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fm_user_template")
public class FormUserTemplate extends TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_template_id")
    private FormTeamTemplate formTeamTemplate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FormTeamTemplate getFormTeamTemplate() {
        return formTeamTemplate;
    }

    public void setFormTeamTemplate(FormTeamTemplate formTeamTemplate) {
        this.formTeamTemplate = formTeamTemplate;
    }
}
