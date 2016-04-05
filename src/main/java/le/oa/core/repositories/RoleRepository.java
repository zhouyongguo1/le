package le.oa.core.repositories;

import com.google.inject.Inject;
import le.oa.core.models.Role;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class RoleRepository extends BaseRepository<Role> {

    @Inject
    public RoleRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Role> findRoles() {

        return this.findAll();
    }

    public Optional<Role> findById(Integer id) {
        List<Role> list = createQuery("from Role a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);
    }
}
