package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.FormTeamTemplate;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FormTeamTemplateRepository extends BaseRepository<FormTeamTemplate> {

    @Inject
    public FormTeamTemplateRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<FormTeamTemplate> findById(Integer id) {
        List<FormTeamTemplate> templates = emProvider.get().createQuery("from FormTeamTemplate a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
    
    
}
