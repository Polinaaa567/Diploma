package local.arch.infrastructure.storage.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"users\"")
public class EUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_user_seq")
    @SequenceGenerator(name = "users_id_user_seq", sequenceName = "users_id_user_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"id_user\"")
    private Integer idUser;

    @Column(name = "\"last_name\"")
    private String lastName;

    @Column(name = "\"first_name\"")
    private String firstName;

    @Column(name = "\"patronymic\"")
    private String patronymic;
    
    @Column(name = "\"login\"")
    private String login;
    
    @Column(name = "\"password\"")
    private String password;
    
    @Column(name = "\"clothing_size\"")
    private String clothingSize;
    
    @Column(name = "\"age_stamp\"")
    private Boolean ageStamp;
    
    @OneToOne
    @JoinColumn(name = "\"fk_role_id\"", referencedColumnName = "\"id_role\"")
    private ERole fkRoleID;

    @Column(name = "\"date_creation\"")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private Timestamp dateCreation;
}
