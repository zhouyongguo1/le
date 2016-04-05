package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.WorkTemplate;
import le.oa.work.models.dto.WorkTemplateDto;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkTemplateRepository extends BaseRepository<WorkTemplate> {

    @Inject
    public WorkTemplateRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<WorkTemplate> findById(Integer id) {
        List<WorkTemplate> templates = emProvider.get().createQuery("from WorkTemplate a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }

    public Optional<WorkTemplate> findByFormTeamTemplate(Integer teamTemplateId) {
        List<WorkTemplate> templates = emProvider.get().createQuery("from WorkTemplate a " +
                "where a.formTeamTemplate.id=:teamTemplateId")
                .setParameter("teamTemplateId", teamTemplateId)
                .getResultList();
        return this.first(templates);
    }

    public List<WorkTemplate> findByUser(Integer userId) {
        return emProvider.get().createQuery("from WorkTemplate a " +
                "where a.userId=:userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<WorkTemplateDto> findDtoAll(Integer userId, Integer teamId) {
        String sql = "SELECT a.id as form_team_template_id,a.name as form_team_template_name,b.id as work_template_id FROM fm_team_template a " +
                "LEFT JOIN wf_template b ON b.team_template_id=a.id AND b.user_id=:userId " +
                "WHERE a.team_id=:teamId ORDER BY b.id ";
        return getEntityManager().createNativeQuery(sql, WorkTemplateDto.class)
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getResultList();
    }

}
