package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.WorkInstanceItem;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkInstanceItemRepository extends BaseRepository<WorkInstanceItem> {

    @Inject
    public WorkInstanceItemRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<WorkInstanceItem> findById(Integer id) {
        List<WorkInstanceItem> templates = emProvider.get().createQuery("from WorkInstanceItem a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
}
