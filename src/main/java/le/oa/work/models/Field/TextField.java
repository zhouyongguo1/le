package le.oa.work.models.Field;

public class TextField extends FormField {

    private boolean required = false;
    private boolean isNumber = false;

    public TextField() {
        this.type = FieldType.TEXT;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public void setNumber(boolean isNumber) {
        this.isNumber = isNumber;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
