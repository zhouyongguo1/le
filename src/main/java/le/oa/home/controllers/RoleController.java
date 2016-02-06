package le.oa.home.controllers;

import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;


@Controller
public class RoleController {
    @Get
    @Route("/roles")
    public Result index() {
        return Results.html();
    }
}