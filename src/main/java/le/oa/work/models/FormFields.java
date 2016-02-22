package le.oa.work.models;

import java.util.ArrayList;
import java.util.List;

public class FormFields {
    private List<FormField> fields = new ArrayList<>();

    public List<FormField> getFields() {
        return fields;
    }

    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }
}
