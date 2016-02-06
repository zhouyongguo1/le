package seeds;

import com.google.inject.Injector;
import le.test.Builder;
import le.test.TestUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class BaseSeeder {

    protected static final int DEFAULT_SCHOOL_ID = 1;

    @Inject
    protected Injector injector;

    @Inject
    protected EntityManager em;

    public <T extends Builder> T withBuilder(Class<T> clazz) {
        TestUtils.setCurrentTeam(em, DEFAULT_SCHOOL_ID);
        return injector.getInstance(clazz);
    }

    public abstract void seed();
}
