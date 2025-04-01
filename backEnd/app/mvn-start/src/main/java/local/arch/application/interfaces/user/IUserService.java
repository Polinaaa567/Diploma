package local.arch.application.interfaces.user;

import java.util.List;

import local.arch.domain.entities.User;

public interface IUserService {
    public String registrationUser(User user);

    public String loginUser(User user);

    public boolean findUser(User user);

    public String changeUserPasswd(User user);

    public void updateUserData(User user);

    public User receiveUserData(Integer idUser);

    public List<User> receiveUserRating();

    public List<User> receiveCertificate(Integer userID);
    // public String receiveCertificate(int userID);

    // отдавть права админа

}
