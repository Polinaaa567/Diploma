package local.arch.application.service.event_service;

import java.util.List;

import local.arch.application.interfaces.event.IEventsService;
import local.arch.application.interfaces.event.IStorageEvent;
import local.arch.application.interfaces.event.IStorageEventUsing;
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
    public void addEvent(Event data) {
        storageEvent.addEvent(data);
    }

    @Override
    public void deleteEvent(Integer eventID) {
        storageEvent.deleteEvent(eventID);
    }

    @Override
    public void changeEventInfo(Integer eventID, Event data) {
        storageEvent.changeEventInfo(eventID, data);
    }

    @Override
    public List<User> receiveUsersByEvent(Integer eventID) {
        return storageEvent.receiveUsersByEvent(eventID);
    }

    @Override
    public void useStorage(IStorageEvent storageEvent) {
        this.storageEvent = storageEvent;
    }

    @Override
    public String saveInfoParticipance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveInfoParticipance'");
    }

}
