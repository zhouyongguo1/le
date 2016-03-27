package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.Template;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WorkTemplateRepository extends BaseRepository<Template> {

    @Inject
    public WorkTemplateRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<Template> findById(Integer id) {
        List<Template> templates = emProvider.get().createQuery("from Template a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }
}
