package le.oa.conf;

import com.google.inject.AbstractModule;
import le.oa.persist.AuditInitializer;

public class Module extends AbstractModule {

    public Module() {
    }

    @Override
    protected void configure() {
        bind(AuditInitializer.class);
        bind(FreemarkerConfigurer.class);
    }
}