package le.oa.core.repositories;


import com.google.inject.Inject;
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

        return this.findAll();
    }


    public Optional<User> findUserByName(String name) {
        List<User> list = createQuery("from User a where a.email=:name or a.account=:name")
                .setParameter("name", name)
                .getResultList();
        return this.first(list);

    }
}
