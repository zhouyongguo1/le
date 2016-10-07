package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.Task;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskRepository extends BaseRepository<Task> {

    @Inject
    public TaskRepository(javax.inject.Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Task> findByProjectId(Integer projectId) {
        List<Task> list = createQuery("from Task a where a.project.id=:projectId order by a.sequence")
                .setParameter("projectId", projectId)
                .getResultList();
        return list;
    }

    public List<Task> findByNotFinish(Integer projectId) {
        List<Task> list = createQuery("select a from Task a where a.project.id=:projectId " +
                "and (a.status='NONE' or a.status='START') and a.isDel=:isDel order by a.sequence")
                .setParameter("projectId", projectId)
                .setParameter("isDel", false)
                .getResultList();
        return list;
    }

    public List<Task> findFinishByDate(Integer projectId, LocalDate begin, LocalDate end) {
        return createQuery("select a from Task a where a.project.id=:projectId " +
                "and (a.status='FINISH' or a.status='END') and a.isDel=:isDel " +
                "and (a.endDate between :begin and :end) order by a.sequence ")
                .setParameter("projectId", projectId)
                .setParameter("isDel", false)
                .setParameter("begin", begin)
                .setParameter("end", end)
                .getResultList();
    }


    public Optional<Task> findById(Integer id) {
        List<Task> list = createQuery("from Task a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }


}
