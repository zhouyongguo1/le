package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.models.Status;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.Project;
import le.oa.project.models.SubjectItem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class SubjectItemRepository extends BaseRepository<SubjectItem> {

    @Inject
    public SubjectItemRepository(javax.inject.Provider<EntityManager> emProvider) {
        super(emProvider);
    }
    public Optional<SubjectItem> findById(Integer id) {
        List<SubjectItem> list = createQuery("from SubjectItem a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }
    public List<SubjectItem> findBySubjectId(Integer subjectId) {
        List<SubjectItem> list = createQuery("from SubjectItem a where a.subjectId=:subjectId order by a.id")
                .setParameter("subjectId", subjectId)
                .getResultList();
        return list;
    }

}
