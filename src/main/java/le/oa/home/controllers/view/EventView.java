package le.oa.home.controllers.view;

import le.oa.core.models.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventView {
    private LocalDate eventDate;
    private List<Event> events;

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public static List<EventView> of(List<Event> events) {
        Map<LocalDate, EventView> map = new HashMap<>();
        List<EventView> list = new ArrayList<>();
        for (Event event : events) {
            EventView eventView = null;
            LocalDate date = event.getEventTime().toLocalDate();
            if (map.containsKey(date)) {
                eventView = map.get(date);

            } else {
                eventView = new EventView();
                eventView.setEventDate(date);
                map.put(date, eventView);
                list.add(eventView);
            }
            eventView.getEvents().add(event);
        }
        return list;
    }
}
