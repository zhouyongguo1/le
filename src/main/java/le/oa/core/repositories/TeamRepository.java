package le.oa.core.repositories;

import le.oa.core.models.Team;

import javax.inject.Provider;
import javax.persistence.EntityManager;

public class TeamRepository extends BaseRepository<Team> {
    
    public TeamRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }
}
