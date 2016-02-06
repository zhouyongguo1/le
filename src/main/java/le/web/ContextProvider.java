package le.web;

import com.google.common.base.Strings;
import com.google.inject.Singleton;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

@Singleton
public class ContextProvider implements Filter, Provider<Context> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextProvider.class);


    private static final ThreadLocal<Context> LOCAL_CONTEXT = new ThreadLocal<>();

    private Provider<HttpServletRequest> requestProvider;

    @Inject
    public ContextProvider(Provider<HttpServletRequest> requestProvider) {
        this.requestProvider = requestProvider;
    }

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        String requestUrl = formatRequestUrl(requestProvider.get());
        LOGGER.info("start processing request: {}", requestUrl);
        LOCAL_CONTEXT.set(context);
        try {
            return filterChain.next(context);
        } finally {
            LOGGER.info("finish processing request: {}", requestUrl);
        }
    }

    public String formatRequestUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String requestUrl = request.getRequestURI();

        if (!Strings.isNullOrEmpty(queryString)) {
            return requestUrl + "?" + queryString;
        } else {
            return requestUrl;
        }
    }

    @Override
    public Context get() {
        return LOCAL_CONTEXT.get();
    }

    public static Context getContext() {
        return LOCAL_CONTEXT.get();
    }

    public static void set(Context context) {
        LOCAL_CONTEXT.set(context);
    }

    public static void remove() {
        LOCAL_CONTEXT.remove();
    }

    private String getTransactionName(Context context) {
        return context.getMethod() + " " + context.getRoute().getUri();
    }

    public String getContextPath() {
        if (getContext() != null) {
            return getContext().getContextPath();
        }
        return "";
    }
}
