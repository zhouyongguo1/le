package le.oa.home.controllers;

import le.oa.core.BaseTeamController;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

@Controller
public class EventController extends BaseTeamController {
    @Get
    @Route("/events")
    public Result index() {
        return Results.html();
    }
}
