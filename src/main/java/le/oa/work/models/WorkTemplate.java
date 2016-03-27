package le.oa.work.models;

import le.oa.core.models.base.TeamModel;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "wf_template")
public class WorkTemplate extends TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer teamTemplateId;
    @Column(name = "data")
    @Type(type = "le.jpa.JsonType", parameters = {
            @Parameter(name = "class", value = "le.oa.work.models.WorkFlow")
    })
    private WorkFlow workFlow;
}