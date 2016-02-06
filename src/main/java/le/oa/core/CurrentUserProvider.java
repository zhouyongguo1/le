package le.oa.core;

import le.oa.core.models.User;
import ninja.Context;

import javax.inject.Inject;
import javax.inject.Provider;

public class CurrentUserProvider implements Provider<User> {

    public static final String USER_ID = "userId";
    public static final String CURRENT_USER = "currentUser";

    private Context context;

    @Inject
    public CurrentUserProvider(Context context) {
        this.context = context;
    }

    @Override
    public User get() {
        Object currentUser = context.getAttribute(CURRENT_USER);
        return currentUser != null ? (User) currentUser : null;
    }

    public boolean isPresent() {
        if (context == null) {
            return false;
        }
        return context.getAttribute(CURRENT_USER) != null;
    }
    
    
    
}
