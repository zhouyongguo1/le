package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.ProjectUser;

import javax.inject.Provider;
import javax.persistence.EntityManager;

public class ProjectUserRepository extends BaseRepository<ProjectUser> {

    @Inject
    public ProjectUserRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }
    
}
