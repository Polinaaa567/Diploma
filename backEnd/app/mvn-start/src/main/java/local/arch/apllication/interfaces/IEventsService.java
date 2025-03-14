package local.arch.apllication.interfaces;

import java.util.List;

import local.arch.domain.entities.Event;
import local.arch.domain.entities.User;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.storage.model.EEvent;

public interface IEventsService {
    public List<Event> receiveEvents();

    public List<Event> receiveEventsUser(Integer userID);

    public Event receiveEventInfo(Integer eventID);

    public Boolean signUpForEvent(UserEvent data);

    public Boolean deleteUsersEvent(UserEvent data);

    public Boolean addEvent(Event data);

    public Boolean deleteEvent(Integer eventID);

    public Boolean changeEventInfo(Integer eventID, Event data);

    public List<User> receiveUsersByEvent(Integer eventID);
}
