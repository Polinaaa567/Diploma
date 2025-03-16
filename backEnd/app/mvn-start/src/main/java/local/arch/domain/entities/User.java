package local.arch.domain.entities;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer idUser;
    
    private String lastName;
    
    private String firstName;
    
    private String patronymic;
    
    private String numberPhone;
    
    private String email;
    
    private String password;
    
    private String clothingSize;
    
    private boolean ageStamp;
    
    private Integer roleID;
    
    private Timestamp dateCreation; 

    private Byte photo_certificate_data; 

    private Integer points;

    private Integer levelNumber;
}
