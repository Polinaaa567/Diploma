package local.arch.infrastructure.builder;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.apllication.interfaces.IEventsService;
import local.arch.apllication.interfaces.IStorageEvent;
import local.arch.apllication.interfaces.IStorageEventUsing;
// import local.arch.apllication.service.user_service.IUserService;

public class BuilderData {

    @Inject
    private IStorageEvent storageEvent;
    
    // @Inject
    // @Default
    // private IUserService userService;

    @Inject
    @Default
    private IEventsService eventService;

    // @BuiltUser
    // @Produces
    // public IUserService buildUserService() {
    //     return userService;
    // }

    @BuiltEvent
    @Produces
    public IEventsService buildEventsService() {
        ((IStorageEventUsing) eventService).useStorage(storageEvent);
        return eventService;
    }
}
