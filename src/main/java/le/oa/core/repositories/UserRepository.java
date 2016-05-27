package le.oa.core.repositories;


import com.google.inject.Inject;
import le.oa.core.models.Status;
import le.oa.core.models.User;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserRepository extends BaseRepository<User> {

    @Inject
    public UserRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<User> findUsers() {
        List<User> list = emProvider.get()
                .createQuery("from User where status=:status", User.class)
                .setParameter("status", Status.ACTIVE)
                .getResultList();
        return list;
    }


    public Optional<User> findUserByName(String name) {
        List<User> list = createQuery("from User a where (a.email=:name or a.name=:name) and status=:status")
                .setParameter("name", name)
                .setParameter("status", Status.ACTIVE)
                .getResultList();
        return this.first(list);
    }


    public Optional<User> findUserById(Integer id) {
        List<User> list = createQuery("from User a where a.id=:id")
                .setParameter("id", id)
                .getResultList();
        return this.first(list);
    }
}
