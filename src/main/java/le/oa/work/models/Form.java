package le.oa.work.models;


import le.oa.core.models.base.BaseModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Integer templateId;
    private String fields;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FormParameter> parameters = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public List<FormParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<FormParameter> parameters) {
        this.parameters = parameters;
    }
}
