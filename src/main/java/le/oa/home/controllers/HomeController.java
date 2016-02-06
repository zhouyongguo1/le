package le.oa.home.controllers;

import com.google.inject.Inject;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;


@Controller
public class HomeController {


    @Inject
    public HomeController() {
    }

    @Get
    @Route("/")
    public Result index() {

        return Results.html();

    }

}
