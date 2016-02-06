package le.oa.core;

import le.web.ContextProvider;
import ninja.Context;

import javax.inject.Inject;
import javax.inject.Provider;

public class CurrentTeamProvider implements Provider<Integer> {

    public static final String CURRENT_TEAM = "currentTeam";
    public static final String TEAM_ID = "teamId";

    private ContextProvider contextProvider;

    @Inject
    public CurrentTeamProvider(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override
    public Integer get() {
        Object currentTeam = contextProvider.get().getAttribute(CURRENT_TEAM);
        return currentTeam != null ? (Integer) currentTeam : null;
    }

    public boolean isPresent() {
        Context context = contextProvider.get();
        if (context == null) {
            return false;
        }
        return context.getAttribute(CURRENT_TEAM) != null;
    }
}
