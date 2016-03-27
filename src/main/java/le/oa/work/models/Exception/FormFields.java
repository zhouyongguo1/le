package le.oa.work.models.Exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import le.oa.work.models.Field.CheckboxField;
import le.oa.work.models.Field.DateField;
import le.oa.work.models.Field.FormField;
import le.oa.work.models.Field.LineField;
import le.oa.work.models.Field.RadioField;
import le.oa.work.models.Field.RatingField;
import le.oa.work.models.Field.SelectField;
import le.oa.work.models.Field.TextAreaField;
import le.oa.work.models.Field.TextField;
import le.oa.work.models.Field.TimeField;
import le.util.ObjectMapperBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormFields {
    private List<FormField> fields = new ArrayList<>();

    public List<FormField> getFields() {
        return fields;
    }

    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = ObjectMapperBuilder.create();
        return mapper.writeValueAsString(fields);
    }

    public static Map<String, Class<? extends FormField>> craterClassMap() {
        Map<String, Class<? extends FormField>> map = new HashMap<>();
        map.put("DATE", DateField.class);
        map.put("TEXT", TextField.class);
        map.put("TEXTAREA", TextAreaField.class);
        map.put("RADIO", RadioField.class);
        map.put("CHECKBOX", CheckboxField.class);
        map.put("SELECT", SelectField.class);
        map.put("TIME", TimeField.class);
        map.put("RATING", RatingField.class);
        map.put("LINE", LineField.class);
        return map;
    }

    public static FormFields of(String json) {
        FormFields formFields = new FormFields();
        ObjectMapper mapper = ObjectMapperBuilder.create();

        Map<String, Class<? extends FormField>> typeMap = craterClassMap();
        try {
            JsonNode jsonNode = mapper.readTree(json);
            for (JsonNode node : jsonNode) {
                Class<? extends FormField> type = typeMap.get(node.get("type").asText());
                if (type != null) {
                    FormField field = mapper.treeToValue(node, type);
                    formFields.fields.add(field);
                }
            }
            return formFields;
        } catch (IOException e) {
            throw new FieldException(e.getMessage());
        }
    }
}
