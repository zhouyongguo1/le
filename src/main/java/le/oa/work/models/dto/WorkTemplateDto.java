package le.oa.work.models.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WorkTemplateDto {
    @Id
    private Integer formTeamTemplateId;
    private String  formTeamTemplateName;
    private Integer workTemplateId;

    public Integer getFormTeamTemplateId() {
        return formTeamTemplateId;
    }

    public void setFormTeamTemplateId(Integer formTeamTemplateId) {
        this.formTeamTemplateId = formTeamTemplateId;
    }

    public String getFormTeamTemplateName() {
        return formTeamTemplateName;
    }

    public void setFormTeamTemplateName(String formTeamTemplateName) {
        this.formTeamTemplateName = formTeamTemplateName;
    }

    public Integer getWorkTemplateId() {
        return workTemplateId;
    }

    public void setWorkTemplateId(Integer workTemplateId) {
        this.workTemplateId = workTemplateId;
    }
}
