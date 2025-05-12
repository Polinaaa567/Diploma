package local.arch.application.service.user_service;

import java.util.List;

import local.arch.application.interfaces.user.IStorageUser;
import local.arch.application.interfaces.user.IStorageUserUsing;
import local.arch.application.interfaces.user.IUserService;
import local.arch.domain.entities.User;

public class UserService implements IUserService, IStorageUserUsing {

    IStorageUser storageUser;

    @Override
    public List<User> receiveUserRating(Integer userID) {
        return null;
    }

    @Override
    public String loginUser(User user) {
        return storageUser.loginUser(user);
    }

    @Override
    public boolean findUser(User user) {
        return storageUser.findUser(user);
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
    public User receiveUserData(Integer idUser ) {
        return storageUser.receiveUserData(idUser);
    }

    @Override
    public List<User> receiveCertificate(Integer userID) {
        return storageUser.receiveCertificate(userID);
    }

    @Override
    public String registrationUser(User user) {
        return storageUser.registrationUser(user);
    }

    @Override
    public void useStorage(IStorageUser storageUser) {
        this.storageUser = storageUser;
    }
}
