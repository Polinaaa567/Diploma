package local.arch.apllication.service.event_service;

import java.util.List;

import local.arch.apllication.interfaces.IEventsService;
import local.arch.apllication.interfaces.IStorageEvent;
import local.arch.apllication.interfaces.IStorageEventUsing;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.User;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.storage.model.EEvent;

public class EventsService implements IEventsService, IStorageEventUsing {

    IStorageEvent storageEvent;

    @Override
    public List<Event> receiveEvents() {
        List <Event> ev = storageEvent.receiveEventss();
        return ev;
    }

    @Override
    public List<Event> receiveEventsUser(Integer userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveEventsUser'");
    }

    @Override
    public Event receiveEventInfo(Integer eventID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveEventInfo'");
    }

    @Override
    public Boolean signUpForEvent(UserEvent data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signUpForEvent'");
    }

    @Override
    public Boolean deleteUsersEvent(UserEvent data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUsersEvent'");
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
