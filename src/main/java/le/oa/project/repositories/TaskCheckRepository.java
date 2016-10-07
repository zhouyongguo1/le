package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.TaskCheck;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TaskCheckRepository extends BaseRepository<TaskCheck> {

    @Inject
    public TaskCheckRepository(javax.inject.Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<TaskCheck> findById(Integer id) {
        List<TaskCheck> list = createQuery("from TaskCheck a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }

    public List<TaskCheck> findByTaskId(Integer taskId) {
        List<TaskCheck> list = createQuery("from TaskCheck a where a.task.id=:taskId")
                .setParameter("taskId", taskId)
                .getResultList();
        return list;
    }

}
