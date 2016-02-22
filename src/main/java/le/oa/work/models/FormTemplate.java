package le.oa.work.models;

import le.oa.core.models.base.DateModel;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fm_template")
public class FormTemplate extends DateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String html;

    @Column(name = "fields")
    @Type(type = "le.jpa.JsonType", parameters = {
            @Parameter(name = "class", value = "le.oa.work.models.FormFields")
    })
    private FormFields fields;
}