package local.arch.domain.entities.page;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer userID;
    
    private String lastName;
    
    private String name;
    
    private String patronymic;
    
    private String login;
    
    private String password;
    
    private String clothingSize;
    
    private String ageStamp;
    
    private Integer roleID;
    
    private String role;

    private Timestamp dateCreation; 

    private String certificate; 

    private Integer points;

    private String formEducation;

    private String basisEducation;

    private String msg;

    private Boolean status;

    private Integer level;

    private String token;
}
