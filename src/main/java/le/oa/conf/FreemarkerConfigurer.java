package le.oa.conf;

import com.google.inject.Singleton;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import le.oa.core.CurrentTeamProvider;
import le.oa.core.CurrentUserProvider;
import le.web.ContextProvider;
import le.web.freemarker.ValidationErrorDirective;
import ninja.lifecycle.Start;
import ninja.template.TemplateEngineFreemarker;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class FreemarkerConfigurer {

    private final Configuration cfg;

    private ContextProvider contextProvider;
    private CurrentTeamProvider currentTeamProvider;
    private CurrentUserProvider currentUserProvider;
    private final ValidationErrorDirective validationErrorDirective;

    @Inject
    public FreemarkerConfigurer(TemplateEngineFreemarker templateEngine,
                                ExtendedObjectWrapper objectWrapper,
                                ContextProvider contextProvider,
                                CurrentTeamProvider currentTeamProvider,
                                CurrentUserProvider currentUserProvider,
                                ValidationErrorDirective validationErrorDirective) {
        this.contextProvider = contextProvider;
        this.currentTeamProvider = currentTeamProvider;
        this.currentUserProvider = currentUserProvider;
        this.validationErrorDirective = validationErrorDirective;
        try {
            Field field = templateEngine.getClass().getDeclaredField("cfg");
            field.setAccessible(true);
            cfg = (Configuration) field.get(templateEngine);
            cfg.setObjectWrapper(objectWrapper);
            objectWrapper.setExposeFields(true);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Start(order = 90) // SUPPRESS
    public void configure() {
        Map<String, Object> map = new HashMap<>();
        map.put("contextProvider", contextProvider);
        map.put("currentUserProvider", currentUserProvider);
        map.put("currentTeamProvider", currentTeamProvider);
        map.put("validationError", validationErrorDirective);
        try {
            cfg.setAllSharedVariables(new SimpleHash(map));
        } catch (TemplateModelException e) {
            throw new RuntimeException(e);
        }
    }
}
