package local.arch.apllication.service.event_service;

import java.util.List;

import local.arch.apllication.interfaces.event.IEventsService;
import local.arch.apllication.interfaces.event.IStorageEvent;
import local.arch.apllication.interfaces.event.IStorageEventUsing;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.User;
import local.arch.domain.entities.UserEvent;

public class EventsService implements IEventsService, IStorageEventUsing {

    IStorageEvent storageEvent;

    @Override
    public List<Event> receiveEvents() {
        return storageEvent.receiveEvents();
    }

    @Override
    public List<Event> receivePastEventsUser(Integer userID) {
        return storageEvent.receivePastEventsUser(userID);
    }

    @Override
    public List<Event> receiveFutureEventsUser(Integer userID) {
        return storageEvent.receiveFutureEventsUser(userID);

    }

    @Override
    public Event receiveEventInfo(UserEvent userEvent) {
        return storageEvent.receiveEventInfo(userEvent);
    }

    @Override
    public String signUpForEvent(UserEvent userEvent) {
        return storageEvent.signUpForEvent(userEvent);
    }

    @Override
    public String deleteUsersEvent(UserEvent userEvent) {
        return storageEvent.deleteUsersEvent(userEvent);
    }

    @Override
    public Boolean addEvent(Event data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addEvent'");
    }

    @Override
    public Boolean deleteEvent(Integer eventID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteEvent'");
    }

    @Override
    public Boolean changeEventInfo(Integer eventID, Event data) {
        throw new UnsupportedOperationException("Unimplemented method 'changeEventInfo'");
    }

    @Override
    public List<User> receiveUsersByEvent(Integer eventID) {
        throw new UnsupportedOperationException("Unimplemented method 'receiveUsersByEvent'");
    }

    @Override
    public void useStorage(IStorageEvent storageEvent) {
        this.storageEvent = storageEvent;
    }

}
