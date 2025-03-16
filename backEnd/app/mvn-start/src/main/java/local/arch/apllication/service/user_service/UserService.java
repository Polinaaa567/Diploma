package local.arch.apllication.service.user_service;

import java.util.List;

import local.arch.apllication.interfaces.user.IStorageUser;
import local.arch.apllication.interfaces.user.IStorageUserUsing;
import local.arch.apllication.interfaces.user.IUserService;
import local.arch.domain.entities.User;

public class UserService implements IUserService, IStorageUserUsing {

    IStorageUser storageUser;

    @Override
    public List<User> receiveUserRating() {
        return null;
    }

    @Override
    public String loginUser(User user) {
        return storageUser.loginUser(user);
    }

    @Override
    public boolean findUser(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'findUser'");
    }

    @Override
    public String changeUserPasswd(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'changeUserPasswd'");
    }

    @Override
    public User updateUserData(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUserData'");
    }

    @Override
    public User receiveUserData(Integer idUser, User user) {
        throw new UnsupportedOperationException("Unimplemented method 'receiveUserData'");
    }

    @Override
    public List<User> receiveCertificate(Integer userID) {
        throw new UnsupportedOperationException("Unimplemented method 'receiveCertificate'");
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
