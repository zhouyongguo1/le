package le.oa.home.controllers;

import com.google.inject.Inject;
import le.oa.core.BaseTeamController;
import le.oa.core.models.Event;
import le.oa.core.models.search.EventSearch;
import le.oa.core.models.search.Pagination;
import le.oa.core.models.search.ResultData;
import le.oa.core.repositories.EventRepository;
import le.oa.home.controllers.view.EventView;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Get;
import ninja.Result;
import ninja.Results;

import java.util.List;

@Controller
public class EventController extends BaseTeamController {
    private EventRepository eventRepository;

    @Inject
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Get
    @Route("/events")
    public Result index(EventSearch search) {
        ResultData<Event> result = eventRepository.findBySearch(search);
        List<EventView> events = EventView.of(result.getList());
        return Results.html().render("search", search)
                .render("pagination", new Pagination(result.getTotalCount(),
                        search.getPageSize(), search.getPageIndex()))
                .render("events", events);
    }


}
