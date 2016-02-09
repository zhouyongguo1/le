package le.oa.project.controllers;

import le.oa.core.BaseTeamController;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

@Controller
public class TaskController extends BaseTeamController {

    @Get
    @Route("/project/{id}/tasks")
    public Result index(@PathParam("id") Integer projectId) {
        return Results.html();
    }


}
