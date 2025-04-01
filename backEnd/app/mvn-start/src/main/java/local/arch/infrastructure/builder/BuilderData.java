package local.arch.infrastructure.builder;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.application.interfaces.event.IEventsService;
import local.arch.application.interfaces.event.IStorageEvent;
import local.arch.application.interfaces.event.IStorageEventUsing;
import local.arch.application.interfaces.user.IStorageUser;
import local.arch.application.interfaces.user.IStorageUserUsing;
import local.arch.application.interfaces.user.IUserService;

public class BuilderData {

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

    // --------------------------------------------------

    @Inject
    private IUserService userService;

    @Inject
    private IStorageUser storageUser;

    @BuiltUser
    @Produces
    public IUserService buildUserService() {
        ((IStorageUserUsing) userService).useStorage(storageUser);
        return userService;
    }
}
