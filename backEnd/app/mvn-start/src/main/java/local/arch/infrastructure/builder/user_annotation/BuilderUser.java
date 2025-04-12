package local.arch.infrastructure.builder.user_annotation;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import local.arch.application.interfaces.user.IStorageUser;
import local.arch.application.interfaces.user.IStorageUserUsing;
import local.arch.application.interfaces.user.IUserService;

public class BuilderUser {
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
