package le.oa.home.controllers;

import com.google.inject.Inject;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

public class UserController {

    @Inject
    public UserController() {
    }

    @Get
    @Route("/")
    public Result index() {

        return Results.html();

    }
}
