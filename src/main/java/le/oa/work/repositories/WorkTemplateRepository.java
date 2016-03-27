package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.WorkTemplate;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkTemplateRepository extends BaseRepository<WorkTemplate> {

    @Inject
    public WorkTemplateRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<WorkTemplate> findById(Integer id) {
        List<WorkTemplate> templates = emProvider.get().createQuery("from WorkTemplate a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
}
