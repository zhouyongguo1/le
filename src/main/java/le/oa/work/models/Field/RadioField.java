package le.oa.work.models.Field;

import java.util.ArrayList;
import java.util.List;

public class RadioField extends FormField {

    private Orientation orientation = Orientation.LENGTHWAYS;
    private List<String> options = new ArrayList<>();

    public RadioField() {
        this.type = FieldType.RADIO;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
