package le.web.binding;

import ninja.Context;
import ninja.bodyparser.BodyParserEngine;

import javax.inject.Inject;
import java.util.Map;

public class FormBinder implements BodyParserEngine {

    private DataBinder dataBinder;

    @Inject
    public FormBinder(DataBinder dataBinder) {
        this.dataBinder = dataBinder;
    }

    @Override
    public <T> T invoke(Context context, Class<T> classOfT) {
        Map<String, String[]> parameters = context.getParameters();
        return dataBinder.bind(parameters, classOfT);
    }

    @Override
    public String getContentType() {
        return "application/x-www-form-urlencoded";
    }
}
