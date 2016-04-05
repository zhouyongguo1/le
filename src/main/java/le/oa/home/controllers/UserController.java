package le.oa.home.controllers;

import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
import le.oa.core.models.Role;
import le.oa.core.models.TeamUser;
import le.oa.core.models.User;
import le.oa.core.repositories.RoleRepository;
import le.oa.core.repositories.TeamUserRepository;
import le.oa.home.controllers.view.RoleView;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseTeamController {

    private RoleRepository roleRepository;
    private TeamUserRepository teamUserRepository;

    @Inject
    public UserController(RoleRepository roleRepository,
                          TeamUserRepository teamUserRepository) {
        this.roleRepository = roleRepository;
        this.teamUserRepository = teamUserRepository;
    }

    @Get
    @Route("/users")
    public Result index() {

        return Results.html();

    }

    @Get
    @Route("/users/selUserDialog")
    public Result selUserDialog() {
        List<TeamUser> teamUsers = teamUserRepository.findAll();
        List<Role> roles = roleRepository.findAll();
        List<User> users = new ArrayList<>();
        List<RoleView> views = roles.stream().map(m -> RoleView.valueOf(m)).collect(Collectors.toList());
        for (RoleView roleView : views) {
            for (TeamUser teamUser : teamUsers) {
                if (teamUser.getRole() == null) {
                    users.add(teamUser.getUser());
                } else if (roleView.getId().equals(teamUser.getRole().getId())) {
                    roleView.getUsers().add(teamUser.getUser());
                }
            }
        }
        return Results.html()
                .render("roles", views)
                .render("users", users);

    }
}
