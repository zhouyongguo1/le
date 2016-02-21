package le.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class ObjectMapperBuilder {

    private ObjectMapperBuilder() {
    }

    public static ObjectMapper create() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        module.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        module.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        module.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        mapper.registerModule(module);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

}
