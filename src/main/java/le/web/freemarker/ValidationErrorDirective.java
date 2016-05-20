package le.web.freemarker;

import com.google.common.base.Strings;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import ninja.validation.FieldViolation;
import ninja.validation.Validation;

import java.io.IOException;
import java.util.Map;

public class ValidationErrorDirective implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        if (!params.containsKey("field")) {
            throw new TemplateModelException("the field parameter can't be empty");
        }

        TemplateScalarModel field = (TemplateScalarModel) params.get("field");
        if (Strings.isNullOrEmpty(field.getAsString())) {
            throw new TemplateModelException("the field parameter can't be empty");
        }

        StringModel wrapper = (StringModel) env.getVariable("validation");
        if (wrapper == null) {
            return;
        }
        Validation validation = (Validation) wrapper.getWrappedObject();
        String message = getFieldError(validation, field.getAsString());
        env.setVariable("message", ObjectWrapper.DEFAULT_WRAPPER.wrap(message));
        body.render(env.getOut());
    }

    private String getFieldError(Validation validation, String field) {
        for (FieldViolation fieldViolation : validation.getBeanViolations()) {
            if (fieldViolation.field.equalsIgnoreCase(field)) {
                return fieldViolation.constraintViolation.getDefaultMessage();
            }
        }
        return null;
    }
}
