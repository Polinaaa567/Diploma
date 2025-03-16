package local.arch.apllication.interfaces.event;

import java.util.List;

import local.arch.domain.entities.Event;

public interface IStorageEvent {
    public List<Event> receiveEvents();
}
