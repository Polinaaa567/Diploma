package local.arch.apllication.service.user_service;

public interface IUserService {
    public String registrationUser(String userInfoRegJSON);

    public String loginUser(String userInfoLoginJSON);

    public String changeUserPasswd(String userInfoLoginJSON);
    
    public String updateUserData(String userInfoJSON);

    // public User receiveUserData(int userID);
    public String receiveUserData(int userID);

    // public List<User> receiveUserRating();
    public String receiveUserRating();

    // public List<User> receiveCertificate(int userID);
    public String receiveCertificate(int userID);

    // отдавть права админа

} 
