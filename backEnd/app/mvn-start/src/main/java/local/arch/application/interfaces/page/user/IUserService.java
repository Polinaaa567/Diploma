package local.arch.application.interfaces.page.user;

import java.util.List;

import local.arch.domain.entities.page.Rating;
import local.arch.domain.entities.page.User;

public interface IUserService {
    public User registrationUser(User user);

    public User loginUser(User user);

    public String changeUserPasswd(User user);

    public void updateUserData(User user);

    public User receiveUserData(Integer idUser);

    public List<Rating> receiveUserRating(Integer userID);

    public Rating receiveCertificate(Integer userID);
    
    // отдавать права админа

}
