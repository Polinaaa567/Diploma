package local.arch.domain.entities;

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
    
    private Timestamp dateCreation; 

    private byte[] photo_certificate_data; 

    private Integer points;

    private Integer levelNumber;

    private String formEducation;

    private String basisEducation;

    private String msg;

    private Boolean status;

    private Integer level;
}
