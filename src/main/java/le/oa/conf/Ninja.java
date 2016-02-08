package le.oa.conf;

import com.google.inject.Inject;
import ninja.Context;
import ninja.NinjaDefault;
import ninja.Result;
import ninja.Results;
import ninja.i18n.Messages;
import ninja.utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Ninja extends NinjaDefault {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ninja.class);

    public static final String REFERER = "Referer";

    @Inject
    private Messages messages;

    @Override
    public Result getInternalServerErrorResult(Context context, Exception exception) {
        Result result = super.getInternalServerErrorResult(context, exception);
        LOGGER.error("500 error caused!", exception);
        return result.template("le/oa/core/views/500.ftl.html");
    }

    @Override
    public Result getNotFoundResult(Context context) {
        Result result = super.getNotFoundResult(context);
        return result.template("le/oa/core/views/404.ftl.html");
    }

    @Override
    public Result getBadRequestResult(Context context, Exception exception) {
        String backUrl = context.getHeader(REFERER);
        Result result = super.getBadRequestResult(context, exception);
        LOGGER.warn("400 error caused!", exception);
        return result.template("le/oa/core/views/400.ftl.html")
                .render("backUrl", backUrl)
                .render("errorMessage", exception.getMessage());
    }

    @Override
    public Result onException(Context context, Exception exception) {
        LOGGER.info(exception.getMessage());
        Message message = new Message("404");
        return Results.forbidden().template("le/oa/core/views/404.ftl.html").render("message", message);
    }
}
