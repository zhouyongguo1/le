package seeds;

import le.builder.ProjectBuilder;
import le.oa.core.models.User;

public class ProjectDataSeeder extends BaseSeeder {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void seed() {

        this.withBuilder(ProjectBuilder.class).user(user).create();
    }
}
