package le.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import le.oa.work.models.WorkFlow;

import java.io.IOException;
import java.util.Map;

public final class JsonUtils {

    private JsonUtils() {
    }

    public static <T> String marshal(T object) throws JsonProcessingException {
        ObjectMapper mapper = ObjectMapperBuilder.create();
        return mapper.writeValueAsString(object);
    }

    public static <T> T unMarshal(String json, TypeReference typeReference) throws IOException {
        ObjectMapper mapper = ObjectMapperBuilder.create();
        return mapper.readValue(json, typeReference);
    }

    public static <T> T unMarshal(String json, Class<T> jType) throws IOException {
        ObjectMapper mapper = ObjectMapperBuilder.create();
        return mapper.readValue(json, jType);

    }

    public static JsonNode unMarshalAsTree(String json) throws IOException {
        ObjectMapper mapper = ObjectMapperBuilder.create();
        return mapper.readTree(json);
    }

    public static Map read2Map(String json) throws IOException {
        ObjectMapper mapper = ObjectMapperBuilder.create();
        return mapper.readValue(json, Map.class);
    }
}
