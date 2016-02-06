package le.builder;

import com.google.inject.Inject;
import le.oa.core.models.Team;
import le.oa.core.repositories.TeamRepository;
import le.test.Builder;

public class TeamBuilder implements Builder<Team> {

    @Inject
    private TeamRepository teamRepository;

    private String name = "乐趣办公";

    @Override
    public Team build() {
        Team team = new Team();
        team.setName(name);
        return team;
    }

    @Override
    public Team create() {
        Team team = build();
        teamRepository.save(team);
        return team;
    }

    public TeamBuilder name(String name) {
        this.name = name;
        return this;
    }
}
