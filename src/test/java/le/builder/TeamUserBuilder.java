package le.builder;

import com.google.inject.Inject;
import le.oa.core.models.TeamUser;
import le.oa.core.models.User;
import le.oa.core.repositories.TeamUserRepository;
import le.test.Builder;

public class TeamUserBuilder implements Builder<TeamUser> {

    @Inject
    private TeamUserRepository teamUserRepository;
    private User user;
    private boolean isOwner = true;

    @Override
    public TeamUser build() {
        TeamUser teamUser = new TeamUser();
        teamUser.setOwner(isOwner);
        teamUser.setUser(user);
        return teamUser;
    }

    @Override
    public TeamUser create() {
        TeamUser team = build();
        teamUserRepository.save(team);
        return team;
    }

    public TeamUserBuilder user(User user) {
        this.user = user;
        return this;
    }

    public TeamUserBuilder isOwner(boolean isOwner) {
        this.isOwner = isOwner;
        return this;
    }
}