package le.oa.work.models.Field;

public class TextAreaField extends FormField {

    private boolean required = false;

    public TextAreaField() {
        this.type = FieldType.TEXT;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
