package le.oa.persist;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import ninja.lifecycle.Start;
import ninja.utils.NinjaProperties;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

@Singleton
public class JpaInitializer {

    private final PersistService persistService;

    private final NinjaProperties ninjaProperties;

    @Inject
    public JpaInitializer(PersistService persistService, NinjaProperties ninjaProperties) {
        this.persistService = persistService;
        this.ninjaProperties = ninjaProperties;
    }

    @Start(order = 1)
    public void start() throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, NoSuchFieldException {
        String persistentUnit = (String) getFieldValue(persistService, "persistenceUnitName");
        Properties properties = (Properties) getFieldValue(persistService, "persistenceProperties");

        String prefix = persistentUnit + ".";
        int prefixLength = prefix.length();
        ninjaProperties.getAllCurrentNinjaProperties().forEach((key, value) -> {
            if (key.toString().startsWith(prefix)) {
                properties.put(key.toString().substring(prefixLength), value);
            }
        });
    }

    private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
