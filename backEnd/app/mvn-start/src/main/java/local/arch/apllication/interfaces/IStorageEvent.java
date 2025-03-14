package local.arch.apllication.interfaces;

import java.util.List;

import local.arch.domain.entities.Event;
import local.arch.infrastructure.storage.model.EEvent;

public interface IStorageEvent {
    public List<Event> receiveEventss();
}
