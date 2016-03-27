package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.Workflow;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkflowRepository extends BaseRepository<Workflow> {

    @Inject
    public WorkflowRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<Workflow> findById(Integer id) {
        List<Workflow> templates = emProvider.get().createQuery("from Workflow a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
}
