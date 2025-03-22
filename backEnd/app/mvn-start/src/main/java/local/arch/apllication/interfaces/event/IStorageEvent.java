package local.arch.apllication.interfaces.event;

import java.util.List;

import local.arch.domain.entities.Event;
import local.arch.domain.entities.User;
import local.arch.domain.entities.UserEvent;

public interface IStorageEvent {
    public List<Event> receiveEvents();

    public List<Event> receivePastEventsUser(Integer userID);

    public List<Event> receiveFutureEventsUser(Integer userID);

    public Event receiveEventInfo(UserEvent userEvent);

    public String signUpForEvent(UserEvent data);

    public String deleteUsersEvent(UserEvent data);

    public Boolean addEvent(Event data);

    public Boolean deleteEvent(Integer eventID);

    public Boolean changeEventInfo(Integer eventID, Event data);

    public List<User> receiveUsersByEvent(Integer eventID);
}
