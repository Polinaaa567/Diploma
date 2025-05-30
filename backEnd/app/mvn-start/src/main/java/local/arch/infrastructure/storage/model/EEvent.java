package local.arch.infrastructure.storage.model;

import java.sql.Timestamp;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Cacheable(true)
@Entity
@Table(name = "\"events\"")
public class EEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_event_id_seq")
    @SequenceGenerator(name = "events_event_id_seq", sequenceName = "events_event_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"event_id\"")
    private Integer eventID;

    @Column(name = "\"name_event\"")
    private String nameEvent;

    @Column(name = "\"description_event\"")
    private String descriptionEvent;

    @Column(name = "\"address_event\"")
    private String addressEvent;

    @Column(name = "\"event_format\"")
    private String eventFormat;

    @Column(name = "\"max_number_participants\"")
    private Integer maxNumberParticipants;

    @OneToOne
    @JoinColumn(name = "\"fk_type\"", referencedColumnName = "\"id_type\"")
    private EType eventType;

    @Column(name = "\"age_restrictions\"")
    private Integer ageRestrictions;

    @Column(name = "\"number_points_event\"")
    private Integer numberPointsEvent;

    @Column(name = "\"date_creation\"")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy' 'HH:mm:ss")
    private Timestamp dateCreation;

    @Column(name = "\"link_dobro_rf\"")
    private String linkDobroRF;

    @Column(name = "\"image\"")
    private String image;

    @Column(name = "\"date_event\"", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateEvent;

    @Column(name = "\"is_participation\"")
    private Boolean isParticipation;
}
