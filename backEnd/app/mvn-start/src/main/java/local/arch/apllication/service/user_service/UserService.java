package local.arch.apllication.service.user_service;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import local.arch.domain.entities.User;

public class UserService implements IUserService {
    private Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String registrationUser(String userInfoRegJSON) {
        User userData = jsonb.fromJson(userInfoRegJSON, User.class);

        return userData.getEmail();
    }

    @Override
    public String loginUser(String userInfoRegJSON) {
        User userData = jsonb.fromJson(userInfoRegJSON, User.class);

        return jsonb.toJson(userData);
    }

    @Override
    public String changeUserPasswd(String userInfoLoginJSON) {
        User userData = jsonb.fromJson(userInfoLoginJSON, User.class);
        
        return null;
    }

    @Override
    public String updateUserData(String userInfoJSON) {
        User userData = jsonb.fromJson(userInfoJSON, User.class);

        return "null";
    }

    @Override
    public String receiveUserData(int userID) {
        return null;
    }

    @Override
    public String receiveCertificate(int userID) {
        return null;
    }

    @Override
    public String receiveUserRating() {
        return null;
    }
}
