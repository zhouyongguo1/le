package le.oa.home.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseTeamController;
import le.oa.core.ResponseJson;
import le.oa.core.models.Role;
import le.oa.core.models.Status;
import le.oa.core.models.User;
import le.oa.core.repositories.UserRepository;
import le.oa.core.services.RoleService;
import le.oa.home.controllers.form.UserForm;
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

import java.util.List;
import java.util.Optional;

@Controller
public class UserController extends BaseTeamController {

    private UserRepository userRepository;
    private RoleService roleService;

    @Inject
    public UserController(UserRepository userRepository, RoleService roleService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Get
    @Route("/users")
    public Result index() {
        List<Role> roles = roleService.findRoles();
        List<User> users = userRepository.findUsers();
        return Results.html()
                .render("users", users)
                .render("roles", roles);

    }

    @Get
    @Route("/users/group")
    public Result users() {
        List<Role> roles = roleService.findRoles();
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
        roleService.save(role);
        return Results.json().render(new ResponseJson(true, "分组保存成功"));
    }

    @Delete
    @Route("/users/group/{id}")
    @Transactional
    public Result delGroup(@PathParam("id") Integer id) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            roleService.delete(role);
        }
        return Results.json().render(new ResponseJson(true, "分组删除成功"));
    }


    @Get
    @Route("/users/new")
    public Result add(@Param("roleId") Integer roleId) {
        List<Role> roles = roleService.findRoles();
        return Results.html()
                .render("roleId", roleId)
                .render("roles", roles);

    }
    @Get
    @Route("/users/{id}")
    public Result edit(@PathParam("id") Integer userId) {
        User user = this.checkEntity(userRepository.findUserById(userId));
        List<Role> roles = roleService.findRoles();
        return Results.html()
                .render("user", user)
                .render("roles", roles);
    }

    @Get
    @Route("/users/selUserDialog")
    public Result selUserDialog() {
        List<Role> roles = roleService.findRoles();
        List<User> users = userRepository.findUsers();
        return Results.html()
                .render("roles", roles)
                .render("users", users);

    }
    
    

    @Post
    @Transactional
    @Route("/users/save")
    public Result save(UserForm userForm, Validation validation, FlashScope flashScope) {
        Optional<User> userOptional = userRepository.findUserByName(userForm.getEmail());
        if (userOptional.isPresent()) {
            validation.addBeanViolation(createFieldError("email", "电子邮箱地址已经存在"));
            List<Role> roles = roleService.findRoles();
            flashScope.error("电子邮箱地址已经存在");
            return Results.html().template(named("add"))
                    .render(VALIDATION_KEY, validation)
                    .render("user", userForm)
                    .render("roles", roles)
                    .render("roleId", userForm.getRoleId());
        } else {
            Role role = null;
            if (userForm.getRoleId() != null) {
                Optional<Role> roleOptional = roleService.findById(userForm.getRoleId());
                if (roleOptional.isPresent()) {
                    role = roleOptional.get();
                }
            }
            userForm.setResetPassword(true);
            User user = userForm.toUser(null,role);
            userRepository.save(user);
            flashScope.success("数据保存成功");
            return this.redirect("/users");
        }
    }
    @Post
    @Transactional
    @Route("/users/update")
    public Result update(UserForm userForm, Validation validation, FlashScope flashScope) {
        Optional<User> userOptional = userRepository.findUserByName(userForm.getEmail());
        if (userOptional.isPresent() && !userOptional.get().getId().equals(userForm.getId())) {
            validation.addBeanViolation(createFieldError("email", "电子邮箱地址已经存在"));
            List<Role> roles = roleService.findRoles();
            flashScope.error("电子邮箱地址已经存在");
            return Results.html().template(named("edit"))
                    .render(VALIDATION_KEY, validation)
                    .render("user", userForm.toUser(null,null))
                    .render("roles", roles);
        } else {
            userOptional= userRepository.findUserById(userForm.getId());
            Role role = null;
            if (userForm.getRoleId() != null) {
                Optional<Role> roleOptional = roleService.findById(userForm.getRoleId());
                if (roleOptional.isPresent()) {
                    role = roleOptional.get();
                }
            }
            User user = userForm.toUser(userOptional.get(),role);
            userRepository.save(user);
            flashScope.success("数据保存成功");
            return this.redirect("/users");
        }
    }

    @Delete
    @Route("/users/{id}")
    @Transactional
    public Result delete(@PathParam("id") Integer id) {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(Status.INACTIVE);
            userRepository.save(user);
        }
        return this.redirect("/users");
    }


}
