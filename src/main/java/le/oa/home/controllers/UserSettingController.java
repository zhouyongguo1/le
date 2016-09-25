package le.oa.home.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.core.models.User;
import le.oa.core.repositories.UserRepository;
import le.oa.home.controllers.form.UserForm;
import le.oa.home.controllers.form.UserSettingForm;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Cookie;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;

import java.util.Optional;

@Controller
public class UserSettingController extends BaseTeamController {

    private UserRepository userRepository;

    @Inject
    public UserSettingController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Get
    @Route("/setting")
    public Result index() {
        User user = this.currentUserProvider.get();
        return Results.html().render("user", user);
    }

    @Post
    @Route("/setting")
    @Transactional
    public Result save(UserSettingForm setting) {
        User user = this.currentUserProvider.get();
        Optional<User> userOptional = userRepository.findUserByName(setting.getEmail());
        if (userOptional.isPresent() && !userOptional.get().getId().equals(user.getId())) {
            return Results.json().render(new ResponseJson(false, "电子邮箱地址已经存在"));
        } else {
            user = setting.of(user);
            userRepository.save(user);
            return Results.json().render(new ResponseJson(true, "用户设置成功"));
        }
    }

    @Get
    @Route("/setting/theme")
    public Result theme(@Param("theme") String theme) {
        this.contextProvider.get().getRequestPath();
        Cookie cookie = Cookie.builder(UserForm.USER_THEME_COOKIE_NAME, theme).build();
        return this.redirect("/").addCookie(cookie);

    }
}
