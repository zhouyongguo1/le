package le.oa.core;

import le.oa.core.models.Team;
import ninja.Context;

import javax.inject.Inject;
import javax.inject.Provider;

public class CurrentTeamProvider implements Provider<Team> {

    public static final String CURRENT_TEAM = "currentTeam";
    public static final String TEAM_ID = "teamId";
    private Context context;

    @Inject
    public CurrentTeamProvider(Context context) {
        this.context = context;
    }

    @Override
    public Team get() {
        Object currentTeam = context.getAttribute(CURRENT_TEAM);
        return currentTeam != null ? (Team) currentTeam : null;
    }

    public boolean isPresent() {
        if (context == null) {
            return false;
        }
        return context.getAttribute(CURRENT_TEAM) != null;
    }
}
