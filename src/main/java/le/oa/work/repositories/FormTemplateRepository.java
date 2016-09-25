package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.FormTemplate;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FormTemplateRepository extends BaseRepository<FormTemplate> {

    @Inject
    public FormTemplateRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<FormTemplate> findById(Integer id) {
        List<FormTemplate> templates = emProvider.get().createQuery("from FormTemplate a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }

    public List<FormTemplate> findByType(Integer typeId) {
        return emProvider.get().createQuery("from FormTemplate a " +
                "where a.formType.id=:typeId")
                .setParameter("typeId", typeId)
                .getResultList();
    }

    public List<FormTemplate> findAll() {
        return emProvider.get().createQuery("from FormTemplate a").getResultList();
    }

}
