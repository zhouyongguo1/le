package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.WorkflowItem;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkflowItemRepository extends BaseRepository<WorkflowItem> {

    @Inject
    public WorkflowItemRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<WorkflowItem> findById(Integer id) {
        List<WorkflowItem> templates = emProvider.get().createQuery("from WorkflowItem a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
}
