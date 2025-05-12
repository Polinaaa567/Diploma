package local.arch.infrastructure.storage.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"center\"")
public class ECenter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "center_center_id_seq")
    @SequenceGenerator(name = "center_center_id_seq", sequenceName = "center_center_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"center_id\"")
    private Integer centerID;

    @Column(name = "\"name\"")
    private String name;

    @Column(name = "\"description\"")
    private String description;

    @Column(name = "\"image_url\"")
    private String imageURL;
    
    @Column(name = "\"date_change\"")
    private Timestamp dateChange;
    
    @Column(name = "\"contacts\"")
    private String contacts;
    
    @Column(name = "\"address\"")
    private String address;
}
