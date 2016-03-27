package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.TeamTemplate;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TeamTemplateRepository extends BaseRepository<TeamTemplate> {

    @Inject
    public TeamTemplateRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<TeamTemplate> findById(Integer id) {
        List<TeamTemplate> templates = emProvider.get().createQuery("from TeamTemplate a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
}
