package le.oa.core;

import le.oa.core.models.User;
import le.web.ContextProvider;
import ninja.Context;

import javax.inject.Inject;
import javax.inject.Provider;

public class CurrentUserProvider implements Provider<User> {

    public static final String USER_ID = "userId";
    public static final String CURRENT_USER = "currentUser";

    private ContextProvider contextProvider;

    @Inject
    public CurrentUserProvider(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }


    @Override
    public User get() {
        Object currentUser = contextProvider.get().getAttribute(CURRENT_USER);
        return currentUser != null ? (User) currentUser : null;
    }

    public boolean isPresent() {
        Context context = contextProvider.get();
        if (context == null) {
            return false;
        }
        return context.getAttribute(CURRENT_USER) != null;
    }

}
