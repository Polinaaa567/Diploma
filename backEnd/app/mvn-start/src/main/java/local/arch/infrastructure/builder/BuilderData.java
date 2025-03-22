package local.arch.infrastructure.builder;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.apllication.interfaces.event.IEventsService;
import local.arch.apllication.interfaces.event.IStorageEvent;
import local.arch.apllication.interfaces.event.IStorageEventUsing;
import local.arch.apllication.interfaces.user.IStorageUser;
import local.arch.apllication.interfaces.user.IStorageUserUsing;
import local.arch.apllication.interfaces.user.IUserService;

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
