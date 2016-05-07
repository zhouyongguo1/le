package le.oa.core.repositories;

import com.google.inject.Inject;
import le.oa.core.models.Role;
import le.oa.core.models.Status;

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

        List<Role> list = emProvider.get()
                .createQuery("from Role where status=:status", Role.class)
                .setParameter("status", Status.ACTIVE)
                .getResultList();
        return list;
    }

    public Optional<Role> findById(Integer id) {
        List<Role> list = createQuery("from Role a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);
    }


}
