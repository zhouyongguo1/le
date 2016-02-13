package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.ProjectUser;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

public class ProjectUserRepository extends BaseRepository<ProjectUser> {

    @Inject
    public ProjectUserRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<ProjectUser> findByProjectId(Integer projectId) {
        List<ProjectUser> list = createQuery("from ProjectUser a where a.projectId=:projectId")
                .setParameter("projectId", projectId)
                .getResultList();
        return list;

    }
}
