package le.oa.work.models;


import le.oa.core.models.base.BaseModel;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "fm_form")
public class Form extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_template_id")
    private TeamTemplate teamTemplate;
    private String html;

    @Column(name = "fields")
    @Type(type = "le.jpa.JsonType", parameters = {
            @Parameter(name = "class", value = "le.oa.work.models.FormFields")
    })
    private FormFields fields;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FormParameter> parameters = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TeamTemplate getTeamTemplate() {
        return teamTemplate;
    }

    public void setTeamTemplate(TeamTemplate teamTemplate) {
        this.teamTemplate = teamTemplate;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public FormFields getFields() {
        return fields;
    }

    public void setFields(FormFields fields) {
        this.fields = fields;
    }

    public List<FormParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<FormParameter> parameters) {
        this.parameters = parameters;
    }
}
