package le.oa.persist;

import com.google.inject.Inject;
import com.google.inject.Injector;
import ninja.lifecycle.Start;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;

@Singleton
public class AuditInitializer {

    private Injector injector;

    @Inject
    public AuditInitializer(Injector injector) {
        this.injector = injector;
    }

    @Start(order = 90)      // SUPPRESS
    public void start() {
        requestInjection(injector.getInstance(EntityManagerFactory.class));
    }

    private void requestInjection(EntityManagerFactory emFactory) {
        SessionFactory sessionFactory = emFactory.unwrap(SessionFactory.class);
        Interceptor interceptor = sessionFactory.getSessionFactoryOptions().getInterceptor();
        injector.injectMembers(interceptor);
    }
}
