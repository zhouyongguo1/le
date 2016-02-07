package le.oa.home.controllers;

import com.google.common.base.Strings;
import le.oa.core.CurrentTeamProvider;
import le.oa.core.CurrentUserProvider;
import le.oa.core.models.TeamUser;
import le.oa.core.models.User;
import le.oa.core.repositories.TeamUserRepository;
import le.oa.core.services.AuthService;
import le.oa.home.controllers.form.LoginForm;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Context;
import ninja.Cookie;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    private TeamUserRepository teamUserRepository;
    private AuthService authenticate;

    @Inject
    public LoginController(TeamUserRepository teamUserRepository, AuthService authenticate) {
        this.teamUserRepository = teamUserRepository;
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

            String teamId = null;
            if (context.hasCookie(CurrentTeamProvider.TEAM_ID)) {
                Cookie cookie = context.getCookie(CurrentTeamProvider.TEAM_ID);
                Optional<TeamUser> optional = teamUserRepository.findByUserIdAndTeamId(
                        Integer.valueOf(cookie.getValue()),
                        userOptional.get().getId());
                if (optional.isPresent()) {
                    teamId = cookie.getValue();
                }
            } else {
                List<TeamUser> list = teamUserRepository.findByUserId(userOptional.get().getId());
                if (list.size() == 1) {
                    teamId = String.valueOf(list.get(0).getTeamId());
                }
            }

            if (Strings.isNullOrEmpty(teamId)) {
                return Results.redirect("/teams");
            } else {
                Cookie teamCookie = Cookie.builder(CurrentTeamProvider.TEAM_ID, teamId).build();
                context.getCookies().add(teamCookie);
                context.getSession().put(CurrentTeamProvider.TEAM_ID, teamId);
                return Results.redirect("/");
            }
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
