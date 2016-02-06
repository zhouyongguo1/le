package le.oa.core.models.base;

import org.hibernate.annotations.Filter;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Filter(name = "team")
public class TeamModel implements Iteam {
    @Basic
    private int teamId;


    @Override
    public int getTeamId() {
        return teamId;
    }

    @Override
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
