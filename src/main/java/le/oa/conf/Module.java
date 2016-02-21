package le.oa.conf;

import com.google.inject.AbstractModule;
import le.oa.persist.AuditInitializer;
import le.oa.persist.JpaInitializer;

public class Module extends AbstractModule {

    public Module() {
    }

    @Override
    protected void configure() {
        bind(AuditInitializer.class);
        bind(FreemarkerConfigurer.class);
        bind(JpaInitializer.class);
    }
}