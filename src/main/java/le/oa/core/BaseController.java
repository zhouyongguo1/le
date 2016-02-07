package le.oa.core;

import com.google.inject.Inject;
import le.oa.core.models.User;
import le.web.ContextProvider;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.exceptions.BadRequestException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@FilterWith({UserFilter.class})
public abstract class BaseController {

    public static final String VALIDATION_KEY = "validation";
    public static final String ERROR_MESSAGE = "errorMessage";
    protected static final String APPLICATION_MS_EXCEL = "application/vnd.ms-excel";
    protected static final String APPLICATION_ZIP = "application/zip";

    @Inject
    protected ContextProvider contextProvider;

    @Inject
    protected CurrentUserProvider currentUserProvider;

    /**
     * @param templateName template name
     * @return path of the named template
     */
    protected String named(final String templateName) {
        String controllerPackageName = getClass().getPackage().getName();
        String parentPackageOfController = controllerPackageName.replace("controllers", "views");
        String maybeEnhancedClassName = getClass().getSimpleName();
        int dollarIndex = maybeEnhancedClassName.indexOf('$');
        String className = dollarIndex >= 0 ? maybeEnhancedClassName.substring(0, dollarIndex) : maybeEnhancedClassName;
        return String.format("%s/%s/%s.ftl.html", parentPackageOfController.replace('.', '/'), className, templateName);
    }

    protected <T> T checkEntity(Optional<T> optional) {
        if (!optional.isPresent()) {
            throw new BadRequestException("所查询的记录无效");
        }
        return optional.get();
    }

    protected Result redirect(String url) {
        return Results.redirect(url);
    }

    protected String fileTypeOf(String fileName) {
        int idx = fileName.lastIndexOf(".");
        return fileName.substring(idx + 1, fileName.length());
    }

    protected String getContextUrl(HttpServletRequest httpServletRequest) {
        return String.format(
                "%s://%s%s",
                httpServletRequest.getScheme(),
                contextProvider.get().getHostname(),
                contextProvider.get().getContextPath()
        );
    }
}
