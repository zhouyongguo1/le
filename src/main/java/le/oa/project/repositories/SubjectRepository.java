package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.models.Status;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.Subject;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class SubjectRepository extends BaseRepository<Subject> {

    @Inject
    public SubjectRepository(javax.inject.Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Subject> findAll(Integer projectId) {
        List<Subject> list = createQuery("from Subject a where a.projectId=:projectId order by a.isTop,a.id")
                .setParameter("projectId", projectId)
                .getResultList();
        return list;
    }

    public List<Subject> findByStatus(Integer projectId, Status status) {
        List<Subject> list = createQuery("from Subject a where a.projectId=:projectId " +
                " and status=:status order by a.isTop,a.id")
                .setParameter("projectId", projectId)
                .setParameter("status", status)
                .getResultList();
        return list;
    }

    public Optional<Subject> findById(Integer id) {
        List<Subject> list = createQuery("from Subject a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }


}
