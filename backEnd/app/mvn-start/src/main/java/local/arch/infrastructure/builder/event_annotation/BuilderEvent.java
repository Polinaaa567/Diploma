package local.arch.infrastructure.builder.event_annotation;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import local.arch.application.interfaces.event.IEventsService;
import local.arch.application.interfaces.event.IStorageEvent;
import local.arch.application.interfaces.event.IStorageEventUsing;

public class BuilderEvent {
     @Inject
    private IStorageEvent storageEvent;

    @Inject
    private IEventsService eventService;

    @BuiltEvent
    @Produces
    public IEventsService buildEventsService() {
        ((IStorageEventUsing) eventService).useStorage(storageEvent);
        return eventService;
    }
}
