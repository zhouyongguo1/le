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

    @Inject
    public TeamFilter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        enableFilter(context);
        return filterChain.next(context);
    }

    private void enableFilter(Context context) {
        String teamId = context.getSession().get(CurrentTeamProvider.TEAM_ID);
        if (teamId != null) {
            try {
                ((Session) entityManager.getDelegate())
                        .enableFilter("team")
                        .setParameter("teamId", Integer.valueOf(teamId));
            } catch (Exception e) {
                LOGGER.warn("can not enable filter.", e);
            }
        }
    }
}
