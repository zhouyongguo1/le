package le.oa.home.controllers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import le.oa.core.BaseController;
import le.oa.core.CurrentTeamProvider;
import le.oa.core.models.Team;
import le.oa.core.repositories.TeamRepository;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import ninja.Cookie;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.FlashScope;

import java.util.List;

@Controller
public class TeamController extends BaseController {

    private TeamRepository teamRepository;

    @Inject
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Get
    @Route("/teams")
    public Result index() {
        List<Team> list = teamRepository.findByUserId(this.currentUserProvider.get().getId());
        return Results.html().render("list", list);
    }

    @Post
    @Route("/teams")
    @Transactional
    public Result create(FlashScope flashScope) {
        String name = contextProvider.get().getParameter("name");
        Team team = Team.of(name);
        teamRepository.save(team);
        flashScope.success("添加成功");
        return this.redirect("/teams");
    }

    @Get
    @Route("/teams/{id}")
    public Result team(@PathParam("id") Integer teamId) {
        Cookie teamCookie = Cookie.builder(CurrentTeamProvider.TEAM_ID, teamId.toString()).build();
        this.contextProvider.get().getCookies().add(teamCookie);
        this.contextProvider.get().getSession().put(CurrentTeamProvider.TEAM_ID, teamId.toString());
        return Results.redirect("/");
    }

}
