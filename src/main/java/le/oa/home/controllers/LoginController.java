package le.oa.home.controllers;

import le.oa.core.CurrentTeamProvider;
import le.oa.core.CurrentUserProvider;
import le.oa.core.models.User;
import le.oa.core.services.AuthService;
import le.oa.home.controllers.form.LoginForm;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;

import javax.inject.Inject;
import java.util.Optional;

@Controller
public class LoginController {

    private AuthService authenticate;

    @Inject
    public LoginController(AuthService authenticate) {
        this.authenticate = authenticate;
    }

    @Get
    @Route("/login")
    public Result index(@Param("url") String url) {
        return Results.html().template(named("login"))
                .render("url", url);
    }

    @Post
    @Route("/login")
    public Result login(LoginForm form, Context context) {
        Optional<User> userOptional = authenticate.findByNameAndPass(form.getUserName(), form.getPass());
        if (!userOptional.isPresent()) {
            return Results.html()
                    .render("message", "登入失败，请再试一次！")
                    .render("form", form);
        } else {
            context.getSession().put(CurrentUserProvider.USER_ID, userOptional.get().getId().toString());
            String teamId = String.valueOf(userOptional.get().getTeamId());
            context.getSession().put(CurrentTeamProvider.TEAM_ID, teamId);
            return Results.redirect("/");
        }
    }


    @Get
    @Route("/logout")
    public Result logout(Context context) {
        context.getSession().clear();
        return Results.redirect("/login");
    }

    protected String named(final String templateName) {
        String controllerPackageName = getClass().getPackage().getName();
        String parentPackageOfController = controllerPackageName.replace("controllers", "views");
        String maybeEnhancedClassName = getClass().getSimpleName();
        int dollarIndex = maybeEnhancedClassName.indexOf('$');
        String className = dollarIndex >= 0 ? maybeEnhancedClassName.substring(0, dollarIndex) : maybeEnhancedClassName;
        return String.format("%s/%s/%s.ftl.html", parentPackageOfController.replace('.', '/'), className, templateName);
    }

}
