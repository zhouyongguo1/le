package le.oa.work.models.Field;

public class DateField extends FormField {

    private boolean required = false;

    public DateField() {
        this.type = FieldType.DATE;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
