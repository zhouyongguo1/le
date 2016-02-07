package le.oa.core.repositories;

import com.google.inject.Inject;
import le.oa.core.models.TeamUser;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TeamUserRepository extends BaseRepository<TeamUser> {
    @Inject
    public TeamUserRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<TeamUser> findByUserId(Integer userId) {
        this.disableTeamFilter();
        List<TeamUser> list = createQuery("from TeamUser a where a.user.id=:userId")
                .setParameter("userId", userId)
                .getResultList();
        return list;

    }

    public Optional<TeamUser> findByUserIdAndTeamId(Integer teamId, Integer userId) {
        this.disableTeamFilter();
        List<TeamUser> list = createQuery("from TeamUser a where a.user.id=:userId and a.teamId=:teamId")
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getResultList();
        return first(list);
    }

}
