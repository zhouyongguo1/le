package le.oa.project.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.project.models.Project;
import le.oa.project.models.dto.ProjectDto;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ProjectRepository extends BaseRepository<Project> {

    @Inject
    public ProjectRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }


    public List<Project> findByUserId(Integer userId) {
        List<Project> list = createQuery("from Project a where a.id in" +
                " (select b.projectId from ProjectUser b where b.user.id=:userId)")
                .setParameter("userId", userId)
                .getResultList();
        return list;
    }

    public Optional<Project> findById(Integer id) {
        List<Project> list = createQuery("from Project a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);

    }

    public List<ProjectDto> findProjectDtoByUserId(Integer userId, int teamId) {
        String sql = "SELECT a.id,a.name,a.team_id,(SELECT count(1) FROM pro_user b WHERE a.id=b.project_id) AS member_count " +
                "FROM pro_project a LEFT JOIN pro_user c ON a.id=c.project_id " +
                "WHERE c.user_id=:userId and a.team_id=:teamId";
        List<ProjectDto> list = this.emProvider.get().createNativeQuery(sql, ProjectDto.class)
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getResultList();
        return list;
    }
}