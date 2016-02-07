package le.oa.core;

import le.oa.core.models.User;
import le.oa.core.repositories.UserRepository;
import le.web.ContextProvider;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

public class UserFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFilter.class);
    private UserRepository userRepository;
    @Inject
    public UserFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result filter(FilterChain chain, Context context) {
        ContextProvider.set(context);
        Optional<User> userOptional = getUser(context);
        if (userOptional.isPresent()) {
            context.setAttribute(CurrentUserProvider.CURRENT_USER, userOptional.get());
            String teamId = context.getSession().get(CurrentTeamProvider.TEAM_ID);
            context.setAttribute(CurrentTeamProvider.CURRENT_TEAM, teamId);
            Result result = chain.next(context);
            return result;
        } else {
            return Results.redirect("/login");
        }
    }

    private Optional<User> getUser(Context context) {
        String userId = context.getSession().get(CurrentUserProvider.USER_ID);
        if (userId != null) {
            return userRepository.findUserById(Integer.parseInt(userId));
        } else {
            return Optional.empty();
        }
    }

}
