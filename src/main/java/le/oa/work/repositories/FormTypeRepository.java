package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.FormType;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FormTypeRepository extends BaseRepository<FormType> {
    @Inject
    public FormTypeRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<FormType> findTypes() {

        return emProvider.get().createQuery("from FormType", FormType.class)
                .getResultList();
    }

    public Optional<FormType> findById(Integer id) {
        List<FormType> list = createQuery("from FormType a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);
    }
}
