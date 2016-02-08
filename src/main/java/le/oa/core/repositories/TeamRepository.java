package le.oa.core.repositories;

import com.google.inject.Inject;
import le.oa.core.models.Team;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TeamRepository extends BaseRepository<Team> {

    @Inject
    public TeamRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }


    public List<Team> findByUserId(Integer userId) {
        this.disableTeamFilter();
        List<Team> list = createQuery("from Team a where a.id in" +
                " (select b.teamId from TeamUser b where b.user.id=:userId)")
                .setParameter("userId", userId)
                .getResultList();
        return list;
    }

    public Optional<Team> findById(Integer id) {
        List<Team> list = createQuery("from Team a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }
}
