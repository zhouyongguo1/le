package le.oa.work.models.Field;

import java.util.ArrayList;
import java.util.List;

public class SelectField extends FormField {

    private List<String> options = new ArrayList<>();
    public SelectField() {
        this.type = FieldType.SELECT;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
