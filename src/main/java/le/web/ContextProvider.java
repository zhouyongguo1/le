package le.web;

import com.google.inject.Singleton;
import ninja.Context;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

@Singleton
public class ContextProvider implements Provider<Context> {

    private static final ThreadLocal<Context> LOCAL_CONTEXT = new ThreadLocal<>();
    private Provider<HttpServletRequest> requestProvider;

    @Inject
    public ContextProvider(Provider<HttpServletRequest> requestProvider) {
        this.requestProvider = requestProvider;
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

    public String getContextPath() {
        if (getContext() != null) {
            return getContext().getContextPath();
        }
        return "";
    }
}
