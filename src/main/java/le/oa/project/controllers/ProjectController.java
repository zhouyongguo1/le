package le.oa.project.controllers;

import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

@Controller
public class ProjectController {

    @Get
    @Route("/projects")
    public Result index() {
        return Results.html();
    }

    @Get
    @Route("/project")
    public Result project() {
        return Results.html();
    }

    @Get
    @Route("/project/new")
    public Result add() {
        return Results.html();
    }

    @Get
    @Route("/project/archived")
    public Result archived() {
        return Results.html();
    }

}
