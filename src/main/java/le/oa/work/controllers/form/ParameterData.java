package le.oa.work.controllers.form;

import le.oa.work.models.Field.FieldType;
import le.oa.work.models.FormParameter;

public class ParameterData {
    private String uid;
    private String name;
    private FieldType fieldType = FieldType.TEXT;
    private String parameterValue;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public FormParameter toFormParameter() {
        FormParameter parameter = new FormParameter();
        parameter.setFieldType(fieldType);
        parameter.setName(name);
        parameter.setUid(uid);
        parameter.setStringValue(parameterValue);
        return parameter;
    }

    public static ParameterData of(FormParameter formParameter) {
        ParameterData data = new ParameterData();
        data.setFieldType(formParameter.getFieldType());
        data.setUid(formParameter.getUid());
        data.setName(formParameter.getName());
        data.setParameterValue(formParameter.getStringValue());
        return data;
    }
}
