package le.oa.home.controllers;

import com.google.inject.Inject;
import le.oa.core.BaseController;
import le.oa.core.BaseTeamController;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

@Controller
public class UserController extends BaseTeamController {

    @Inject
    public UserController() {
    }

    @Get
    @Route("/users")
    public Result index() {

        return Results.html();

    }
}
