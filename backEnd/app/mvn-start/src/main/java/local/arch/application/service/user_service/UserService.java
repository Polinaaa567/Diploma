package local.arch.application.service.user_service;

import java.util.List;

import local.arch.application.interfaces.page.user.IStorageUser;
import local.arch.application.interfaces.page.user.IStorageUserUsing;
import local.arch.application.interfaces.page.user.IUserService;
import local.arch.application.interfaces.token.TokenManagerInjection;
import local.arch.domain.entities.page.Rating;
import local.arch.domain.entities.page.User;
import local.arch.infrastructure.token.ITokenKey;

public class UserService implements IUserService, IStorageUserUsing, TokenManagerInjection {

    IStorageUser storageUser;

    ITokenKey tokenKey;

    @Override
    public List<Rating> receiveUserRating(Integer userID) {
        return storageUser.receiveUserRating(userID);
    }

    @Override
    public User loginUser(User user) {
        User authResult = storageUser.loginUser(user);

        if (authResult.getStatus()) {
            String token = tokenKey.generateToken(user.getLogin(), authResult.getRole());
            authResult.setToken(token);
        }

        return authResult;
    }

    @Override
    public String changeUserPasswd(User user) {
        return storageUser.changeUserPasswd(user);
    }

    @Override
    public void updateUserData(User user) {
        storageUser.updateUserData(user);
    }

    @Override
    public User receiveUserData(Integer idUser) {
        return storageUser.receiveUserData(idUser);
    }

    @Override
    public Rating receiveCertificate(Integer userID) {
        return storageUser.receiveCertificate(userID);
    }

    @Override
    public User registrationUser(User user) {
        User registerResult =  storageUser.registrationUser(user);

        if (registerResult.getStatus()) {
            String token = tokenKey.generateToken(user.getLogin(), "Пользователь");
            registerResult.setToken(token);
        }

        return registerResult;
    }

    @Override
    public void useStorage(IStorageUser storageUser) {
        this.storageUser = storageUser;
    }

    @Override
    public void injectTokenManager(ITokenKey manager) {
        tokenKey = manager;
    }
}
