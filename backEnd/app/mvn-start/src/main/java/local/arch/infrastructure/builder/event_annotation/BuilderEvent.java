package local.arch.infrastructure.builder.event_annotation;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.application.interfaces.config.IFileConfig;
import local.arch.application.interfaces.config.IUseFileConfig;
import local.arch.application.interfaces.page.event.IEventsService;
import local.arch.application.interfaces.page.event.IStorageEvent;
import local.arch.application.interfaces.page.event.IStorageEventUsing;

public class BuilderEvent {
    @Inject
    private IStorageEvent storageEvent;

    @Inject
    private IEventsService eventService;

    @Inject
    private IFileConfig fileConfig;

    @BuiltEvent
    @Produces
    public IEventsService buildEventsService() {
        ((IStorageEventUsing) eventService).useStorage(storageEvent);
        ((IUseFileConfig) eventService).useFileConfig(fileConfig);
        
        return eventService;
    }
}
