package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.models.Status;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.SubjectItem;

import javax.persistence.EntityManager;
import java.util.List;

public class SubjectItemRepository extends BaseRepository<SubjectItem> {

    @Inject
    public SubjectItemRepository(javax.inject.Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<SubjectItem> findAll(Integer subjectId) {
        List<SubjectItem> list = createQuery("from SubjectItem a where a.subjectId=:subjectId order by a.id")
                .setParameter("subjectId", subjectId)
                .getResultList();
        return list;
    }

    public List<SubjectItem> findByStatus(Integer subjectId, Status status) {
        List<SubjectItem> list = createQuery("from SubjectItem a where a.subjectId=:subjectId " +
                " and status=:status order by a.id")
                .setParameter("subjectId", subjectId)
                .setParameter("status", status)
                .getResultList();
        return list;
    }


}
