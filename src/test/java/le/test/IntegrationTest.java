package le.test;

import com.google.inject.Injector;
import ninja.utils.NinjaConstant;
import ninja.utils.NinjaTestServer;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class IntegrationTest {

    private static NinjaTestServer ninjaTestServer;

    static {
        System.setProperty(NinjaConstant.MODE_KEY_NAME, NinjaConstant.MODE_TEST);
    }

    @BeforeClass
    public static void setUpClass() {
        ninjaTestServer = new NinjaTestServer();
    }

    public static String getServerAddress() {
        return ninjaTestServer.getServerAddress();
    }

    public static Injector getInjector() {
        return ninjaTestServer.getInjector();
    }

    @Inject
    private EntityManager em;

    @Before
    public void setUp() {
        getInjector().injectMembers(this);
        clearDb();
    }

    private void clearDb() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createQuery("delete from Para").executeUpdate();
        tx.commit();
    }

}
