package le.oa.home.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.core.models.Role;
import le.oa.core.models.Status;
import le.oa.core.models.User;
import le.oa.core.repositories.RoleRepository;
import le.oa.core.repositories.UserRepository;
import le.oa.home.controllers.form.UserForm;
import le.oa.home.controllers.view.RoleView;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Delete;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.session.FlashScope;
import ninja.validation.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseTeamController {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Inject
    public UserController(RoleRepository roleRepository,
                          UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Get
    @Route("/users")
    public Result index() {
        List<Role> roles = roleRepository.findRoles();
        List<User> users = userRepository.findUsers();
        return Results.html()
                .render("users", users)
                .render("roles", roles);

    }

    @Get
    @Route("/users/group")
    public Result group() {
        List<Role> roles = roleRepository.findRoles();
        List<User> users = userRepository.findUsers();
        return Results.html()
                .render("users", users)
                .render("roles", roles);
    }


    @Post
    @Transactional
    @Route("/users/group")
    public Result groupSave(@Param("groupName") String groupName) {
        Role role = new Role();
        role.setName(groupName);
        roleRepository.save(role);
        return Results.json().render(new ResponseJson(true,"分组保存成功"));
    }

    @Get
    @Route("/users/selUserDialog")
    public Result selUserDialog() {
        List<Role> roles = roleRepository.findRoles();
        List<User> users = userRepository.findUsers();
        return Results.html()
                .render("roles", roles)
                .render("users", users);

    }

    @Get
    @Route("/users/new")
    public Result add(@Param("roleId") Integer roleId) {
        List<Role> roles = roleRepository.findRoles();
        return Results.html()
                .render("roleId", roleId)
                .render("roles", roles);

    }

    @Post
    @Transactional
    @Route("/users/save")
    public Result save(UserForm userForm, Validation validation, FlashScope flashScope) {
        Optional<User> userOptional = userRepository.findUserByName(userForm.getEmail());
        if (userOptional.isPresent()) {
            validation.addBeanViolation(createFieldError("email", "电子邮箱地址已经存在"));
            List<Role> roles = roleRepository.findRoles();
            flashScope.error("电子邮箱地址已经存在");
            return Results.html().template(named("add"))
                    .render(VALIDATION_KEY, validation)
                    .render("user", userForm)
                    .render("roles", roles)
                    .render("roleId", userForm.getRoleId());
        } else {
            Role role = null;
            if (userForm.getRoleId() != null) {
                Optional<Role> roleOptional = roleRepository.findById(userForm.getRoleId());
                if (roleOptional.isPresent()) {
                    role = roleOptional.get();
                }
            }
            User user = userForm.toUser(role);
            userRepository.save(user);
            flashScope.success("添加成功");
            return this.redirect("/users");
        }
    }

    @Delete
    @Route("/users/{id}")
    public Result delete(@PathParam("id") Integer id) {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(Status.INACTIVE);
            userRepository.update(user);
        }
        return this.redirect("/users");
    }

}
