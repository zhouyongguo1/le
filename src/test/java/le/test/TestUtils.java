package le.test;

import le.oa.core.CurrentUserProvider;
import le.oa.core.models.User;
import ninja.utils.FakeContext;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

public final class TestUtils {

    private TestUtils() {
    }

    public static void clearDb(EntityManager em) {
        ArrayList<String> tables = newArrayList(
                "core_team",
                "core_user",
                "core_user_config",
                "core_role",
                "core_user_team",
                "core_event",
                "core_invite",
                "pro_project",
                "pro_tag",
                "pro_user",
                "pro_task",
                "pro_task_tag",
                "pro_task_check"
        );
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        tables.forEach(t -> em.createNativeQuery("truncate table " + t).executeUpdate());
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        tx.commit();
    }

    public static void setCurrentUser(User user) {
        FakeContext context = new FakeContext();
        context.setAttribute(CurrentUserProvider.CURRENT_USER, user);
    }

    public static void setCurrentTeam(EntityManager em, int teamId) {
        ((Session) em.getDelegate()).enableFilter("team")
                .setParameter("teamId", teamId);
    }

}
