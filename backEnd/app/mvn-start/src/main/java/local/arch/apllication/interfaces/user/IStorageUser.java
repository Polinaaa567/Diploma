package local.arch.apllication.interfaces.user;

import java.util.List;

import local.arch.domain.entities.User;

public interface IStorageUser {
    public String registrationUser(User user);

    public String loginUser(User user);

    public boolean findUser(User user);

    public String changeUserPasswd(User user);
    
    public User updateUserData(User user);

    public User receiveUserData(Integer idUser, User user);

    public List<User> receiveUserRating();

    public List<User> receiveCertificate(Integer userID);
}
