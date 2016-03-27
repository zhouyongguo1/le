package le.oa.work.models.Field;

public class RatingField extends FormField {

    private int max = 5;

    public RatingField() {
        this.type = FieldType.RATING;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
