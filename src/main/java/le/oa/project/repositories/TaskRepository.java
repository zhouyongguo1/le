package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.Task;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TaskRepository extends BaseRepository<Task> {

    @Inject
    public TaskRepository(javax.inject.Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Task> findByNotArchived(Integer projectId) {
        List<Task> list = createQuery("from Task a where a.project.id=:projectId " +
                "and a.isArchived=:isArchived and a.isDel=:isDel order by a.sequence")
                .setParameter("projectId", projectId)
                .setParameter("isArchived", false)
                .setParameter("isDel", false)
                .getResultList();
        return list;
    }

    public List<Task> findByProjectId(Integer projectId) {
        List<Task> list = createQuery("from Task a where a.project.id=:projectId order by a.sequence")
                .setParameter("projectId", projectId)
                .getResultList();
        return list;
    }

    public Optional<Task> findById(Integer id) {
        List<Task> list = createQuery("from Task a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }


}
