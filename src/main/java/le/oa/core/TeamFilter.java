package le.oa.core;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TeamFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamFilter.class);

    private EntityManager entityManager;
    private CurrentTeamProvider currentTeamProvider;

    @Inject
    public TeamFilter(EntityManager entityManager, CurrentTeamProvider currentTeamProvider) {
        this.entityManager = entityManager;
        this.currentTeamProvider = currentTeamProvider;
    }


    @Override
    public Result filter(FilterChain filterChain, Context context) {
        enableFilter();
        return filterChain.next(context);
    }

    private void enableFilter() {
        if (!currentTeamProvider.isPresent()) {
            return;
        }
        try {
            ((Session) entityManager.getDelegate())
                    .enableFilter("team")
                    .setParameter("teamId", currentTeamProvider.get().getId());
        } catch (Exception e) {
            LOGGER.warn("can not enable filter.", e);
        }
    }
}
