package le.oa.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import le.oa.work.models.Field.DateField;
import le.oa.work.models.Field.FormField;
import le.oa.work.models.Field.LineField;
import le.util.ObjectMapperBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {
    @Test
    public void test_toArrayObject() throws IOException {

        List<FormField> fields=new ArrayList<>();
        String json = "[{\"type\":\"DATE\",\"id\":\"\",\"name\":\"未命名\",\"defaultValue\":\"\",\"required\":true}," +
                "{\"type\":\"LINE\",\"id\":\"\",\"name\":\"标题\"}]";
        ObjectMapper mapper = ObjectMapperBuilder.create();
        Map<String, Class<? extends FormField> > typeMap=craterClassMap();
        JsonNode jsonNode = mapper.readTree(json);
        for (JsonNode node : jsonNode) {
            Class<? extends FormField> type= typeMap.get(node.get("type").asText());
            FormField  field = mapper.treeToValue(node,type);
            fields.add(field);
        }
        System.out.println(fields);
    }

    @Test
    public void test_toObject() throws IOException {

        String json = "{\"type\":\"DATE\",\"id\":\"\",\"name\":\"未命名\",\"defaultValue\":\"1\",\"required\":true}";
        ObjectMapper mapper = ObjectMapperBuilder.create();
        DateField field = mapper.readValue(json,DateField.class);
        System.out.println(field);
    }

    @Test
    public void test_toJson() throws IOException {
        String json = "{\"type\":\"DATE\",\"id\":\"\",\"name\":\"未命名\",\"defaultValue\":\"1\",\"required\":true}";

        DateField field=new DateField();
        field.setId("");
        field.setName("未命名");
        ObjectMapper mapper = ObjectMapperBuilder.create();
        System.out.println(json);
        json= mapper.writeValueAsString(field);
        System.out.println(json);
    }
    
    
    public Map<String, Class<? extends FormField> > craterClassMap() {
        Map<String, Class<? extends FormField> > map = new HashMap<>();
        map.put("DATE", DateField.class);
        map.put("LINE", LineField.class);
        return map;
    }
}
