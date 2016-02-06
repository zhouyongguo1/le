package le.oa.conf;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    public Module() {
    }

    @Override
    protected void configure() {
        System.out.println("---------bind");
        // bind(AuditInitializer.class);
//        bind(ContextProvider.class);
//
//        bindCecdbPersistService();
//        bind(CurrentUser.class).toProvider(CurrentUserProvider.class).in(ServletScopes.REQUEST);
//        bind(User.class).toProvider(CurrentUserProvider.class).in(ServletScopes.REQUEST);
//
//        mapBinder = MapBinder.newMapBinder(binder(), String.class, Object.class);
//        mapBinder.addBinding("contextProvider").to(ContextProvider.class);
//        mapBinder.addBinding("currentUserProvider").to(CurrentUserProvider.class);
//        mapBinder.addBinding("subjectProvider").to(SubjectProvider.class);

    }


}