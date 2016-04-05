package le.oa.conf;

import le.web.HttpMethodFilter;
import ninja.servlet.NinjaServletDispatcher;

public class ServletModule extends com.google.inject.servlet.ServletModule {

    @Override
    protected void configureServlets() {
        bind(NinjaServletDispatcher.class).asEagerSingleton();
        filter("/*").through(HttpMethodFilter.class);
        filter("/*").through(PersistFilter.class);
        serve("/*").with(NinjaServletDispatcher.class);
    }

}
