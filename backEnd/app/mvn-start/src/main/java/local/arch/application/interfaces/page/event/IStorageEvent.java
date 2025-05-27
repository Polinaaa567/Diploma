package local.arch.application.interfaces.page.event;

import java.util.List;

import local.arch.domain.entities.Pagination;
import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.User;
import local.arch.domain.entities.page.UserEvent;

public interface IStorageEvent {
    public List<Event> receiveEvents();

    public Pagination receivePastEventsUser(Integer userID, Integer page, Integer limit);

    public Pagination receiveFutureEventsUser(Integer userID, Integer page, Integer limit);

    public Event receiveEventInfo(UserEvent userEvent);

    public UserEvent signUpForEvent(UserEvent data);

    public UserEvent deleteUsersEvent(UserEvent data);

    public void addEvent(Event data);

    public void deleteEvent(Integer eventID);

    public void changeEventInfo(Integer eventID, Event data);

    public List<UserEvent> receiveUsersByEvent(Integer eventID);

    public String saveInfoParticipance(Integer eventID, UserEvent ue);

    public Pagination eventsBetwenDate(String dateStart, String dateEnd, Integer page, Integer limit);

    public List<String> getTypesEvents();

    public Pagination receiveAllEvents(Integer page, Integer limit);

    public Event findEvent(Integer eventID);

    public User findUser(Integer userID);
}
