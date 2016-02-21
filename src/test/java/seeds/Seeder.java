package seeds;

import com.google.inject.Injector;
import com.google.inject.persist.Transactional;
import le.builder.TeamBuilder;
import le.builder.TeamUserBuilder;
import le.builder.UserBuilder;
import le.oa.core.CurrentTeamProvider;
import le.oa.core.CurrentUserProvider;
import le.oa.core.models.Team;
import le.oa.core.models.User;
import le.test.TestUtils;
import le.test.Transaction;
import ninja.servlet.NinjaBootstrap;
import ninja.utils.NinjaMode;
import ninja.utils.NinjaPropertiesImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;

public class Seeder {
    @Inject
    protected EntityManager em;
    @Inject
    private CoreDataSeeder coreDataSeeder;
    @Inject
    private ProjectDataSeeder projectDataSeeder;
    @Inject
    private WorkDataSeeder workDataSeeder;

    public static void main(String[] args) {
        NinjaPropertiesImpl ninjaProperties = new NinjaPropertiesImpl(NinjaMode.prod);
        NinjaBootstrap ninjaBootstrap = new NinjaBootstrap(ninjaProperties);
        ninjaBootstrap.boot();
        Injector injector = ninjaBootstrap.getInjector();
        try {
            String dbConnection = ninjaProperties.get("db.connection.url");
            System.out.println("===" + dbConnection);
            Seeder seeder = injector.getInstance(Seeder.class);
            seeder.cleanDb();
            seeder.seed();
        } finally {
            ninjaBootstrap.shutdown();
        }
    }

    private void cleanDb() {
        TestUtils.clearDb(em);
    }


    @Transactional
    public void seed() {
        User user = coreDataSeeder.withBuilder(UserBuilder.class).create();
        TestUtils.setCurrentUser(user);
        coreDataSeeder.withBuilder(TeamBuilder.class).create();
        coreDataSeeder.withBuilder(TeamUserBuilder.class).user(user).create();

        coreDataSeeder.seed();
        projectDataSeeder.setUser(user);
        projectDataSeeder.seed();
        workDataSeeder.seed();
    }

    public void seeduser(){

        try (Transaction txn = new Transaction(em.getTransaction())) {
             coreDataSeeder.withBuilder(UserBuilder.class).create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    

}
