package le.oa.core.models.base;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel extends DateModel implements Iteam {
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
