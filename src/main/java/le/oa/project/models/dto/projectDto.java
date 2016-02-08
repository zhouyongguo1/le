package le.oa.project.models.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProjectDto {
    @Id
    private Integer id;
    private String name;
    private Integer memberCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}
