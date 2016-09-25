package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.UserTemplate;
import le.oa.work.models.dto.WorkTemplateDto;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkTemplateRepository extends BaseRepository<UserTemplate> {

    @Inject
    public WorkTemplateRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<UserTemplate> findById(Integer id) {
        List<UserTemplate> templates = emProvider.get().createQuery("from UserTemplate a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }

    public Optional<UserTemplate> findByFormTeamTemplate(Integer teamTemplateId) {
        List<UserTemplate> templates = emProvider.get().createQuery("from UserTemplate a " +
                "where a.formTeamTemplate.id=:teamTemplateId")
                .setParameter("teamTemplateId", teamTemplateId)
                .getResultList();
        return this.first(templates);
    }

    public List<UserTemplate> findByUser(Integer userId) {
        return emProvider.get().createQuery("from UserTemplate a " +
                "where a.userId=:userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<WorkTemplateDto> findDtoAll(Integer userId, Integer teamId) {
        String sql = "SELECT a.id as form_template_id,a.name as form_template_name,b.id as work_template_id FROM fm_template a " +
                "LEFT JOIN wf_template b ON b.team_template_id=a.id AND b.user_id=:userId " +
                "WHERE a.team_id=:teamId ORDER BY b.id ";
        return getEntityManager().createNativeQuery(sql, WorkTemplateDto.class)
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getResultList();
    }

}
