package le.oa.home.controllers;

import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
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
import ninja.params.PathParam;
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

        return Results.html();

    }

    @Get
    @Route("/users/selUserDialog")
    public Result selUserDialog() {
        List<Role> roles = roleRepository.findAll();
        List<User> users = new ArrayList<>();
        List<User> userList = userRepository.findUsers();
        List<RoleView> views = roles.stream().map(m -> RoleView.valueOf(m)).collect(Collectors.toList());
        for (RoleView roleView : views) {
            for (User user : userList) {
                if (user.getRole() == null) {
                    users.add(user);
                } else if (roleView.getId().equals(user.getRole().getId())) {
                    roleView.getUsers().add(user);
                }
            }
        }
        return Results.html()
                .render("roles", views)
                .render("users", users);

    }

    @Get
    @Route("/users/new")
    public Result add() {
        List<Role> roles = roleRepository.findRoles();
        return Results.html()
                .render("roles", roles);

    }

    @Post
    @Route("/users/new")
    public Result save(UserForm userForm, Validation validation) {
        Optional<User> userOptional = userRepository.findUserByName(userForm.getEmail());
        if (userOptional.isPresent()) {
            validation.addBeanViolation(createFieldError("email", "email已经存在"));
            return Results.html()
                    .render(VALIDATION_KEY, validation)
                    .render("userForm", userForm);
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
