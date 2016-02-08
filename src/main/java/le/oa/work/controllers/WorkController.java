package le.oa.work.controllers;


import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

@Controller
public class WorkController {
    @Get
    @Route("/works")
    public Result index() {
        return Results.html();
    }

    @Get
    @Route("/work")
    public Result work() {
        return Results.html();
    }

    @Get
    @Route("/work/new")
    public Result add() {
        return Results.html();
    }

    @Get
    @Route("/work/archived")
    public Result archived() {
        return Results.html();
    }
}
