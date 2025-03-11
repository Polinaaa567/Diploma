package local.arch.infrastructure.builder;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.apllication.service.user_service.IUserService;

public class BuilderUserData {
    
    @Inject
    @Default
    private IUserService userService;

    @Built
    @Produces
    public IUserService buildUserService() {
        return userService;
    }
}
