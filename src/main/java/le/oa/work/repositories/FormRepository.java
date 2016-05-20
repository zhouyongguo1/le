package le.oa.work.repositories;

import com.google.inject.Inject;
import le.oa.core.repositories.BaseRepository;
import le.oa.work.models.Form;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FormRepository extends BaseRepository<Form> {

    @Inject
    public FormRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<Form> findById(Integer id) {
        List<Form> templates = emProvider.get().createQuery("from Form a " +
                "where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(templates);
    }


}
