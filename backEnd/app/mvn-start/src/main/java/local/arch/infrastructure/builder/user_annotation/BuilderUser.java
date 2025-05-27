package local.arch.infrastructure.builder.user_annotation;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.application.interfaces.page.user.IStorageUser;
import local.arch.application.interfaces.page.user.IStorageUserUsing;
import local.arch.application.interfaces.page.user.IUserService;
import local.arch.application.interfaces.token.TokenManagerInjection;
import local.arch.infrastructure.token.ITokenKey;

public class BuilderUser {
    @Inject
    private IUserService userService;

    @Inject
    private IStorageUser storageUser;

    @Inject
    private ITokenKey tokenKey;

    @BuiltUser
    @Produces
    public IUserService buildUserService() {
        ((IStorageUserUsing) userService).useStorage(storageUser);
        ((TokenManagerInjection) userService).injectTokenManager(tokenKey);
        
        return userService;
    }
}
