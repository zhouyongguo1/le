package le.oa.core;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class UserFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFilter.class);
    public static final String USER_ID_KEY = "userId";
    public static final int BASIC_PREFIX_LENGTH = 6;

    @Inject
    public UserFilter() {
    }
    @Override
    public Result filter(FilterChain chain, Context context) {
        Object currentUser = context.getAttribute(CurrentUserProvider.CURRENT_USER);
        if(currentUser==null)
        {
           return Results.redirect("/login");
        }
        else{
            Result result = chain.next(context);
            return result;
        }
    }

}
