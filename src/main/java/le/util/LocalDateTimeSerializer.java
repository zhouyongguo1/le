package le.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value,
                          JsonGenerator jgen,
                          SerializerProvider provider) throws IOException, JsonProcessingException {
        ZonedDateTime zoned = value.atZone(ZoneOffset.UTC);
        jgen.writeString(zoned.toString());
    }

}
