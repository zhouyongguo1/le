package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.models.Status;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.Doc;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DocRepository extends BaseRepository<Doc> {

    @Inject
    public DocRepository(javax.inject.Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Doc> findByProject(Integer projectId) {
        List<Doc> list = createQuery("from Doc a where a.projectId=:projectId and a.isDel=:isDel " +
                "order by a.sequence,a.id")
                .setParameter("projectId", projectId)
                .setParameter("isDel", false)
                .getResultList();
        return list;
    }

    public Optional<Doc> findById(Integer id) {
        List<Doc> list = createQuery("from Doc a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }


}
