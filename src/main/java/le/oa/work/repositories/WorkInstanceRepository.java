package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.WorkInstance;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkInstanceRepository extends BaseRepository<WorkInstance> {

    @Inject
    public WorkInstanceRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<WorkInstance> findById(Integer id) {
        List<WorkInstance> templates = emProvider.get().createQuery("from WorkInstance a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
}
