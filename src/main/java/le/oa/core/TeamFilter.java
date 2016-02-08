package le.oa.core;

import le.oa.core.models.Team;
import le.oa.core.repositories.TeamRepository;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

public class TeamFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamFilter.class);

    private EntityManager entityManager;
    private TeamRepository teamRepository;

    @Inject
    public TeamFilter(EntityManager entityManager, TeamRepository teamRepository) {
        this.entityManager = entityManager;
        this.teamRepository = teamRepository;
    }

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        Optional<Team> teamOptional = getTeam(context);
        if (teamOptional.isPresent()) {
            context.setAttribute(CurrentTeamProvider.CURRENT_TEAM, teamOptional.get());
            try {
                ((Session) entityManager.getDelegate())
                        .enableFilter("team")
                        .setParameter("teamId", teamOptional.get().getId());
            } catch (Exception e) {
                LOGGER.warn("can not enable filter.", e);
            }
            return filterChain.next(context);
        } else {
            return Results.redirect("/teams");
        }
    }

    private Optional<Team> getTeam(Context context) {
        String teamId = context.getSession().get(CurrentTeamProvider.TEAM_ID);
        if (teamId != null) {
            return teamRepository.findById(Integer.parseInt(teamId));
        } else {
            return Optional.empty();
        }
    }
}
