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

    @Column(name = "\"time_event\"")
    private String timeEvent;

    @Column(name = "\"max_number_participants\"")
    private Integer maxNumberParticipants;

    @Column(name = "\"event_type\"")
    private String eventType;

    @Column(name = "\"age_restrictions\"")
    private Integer ageRestrictions;

    @Column(name = "\"number_points_event\"")
    private Integer numberPointsEvent;

    @Column(name = "\"date_creation\"")
    private Timestamp dateCreation;

    @Column(name = "\"link_dobro_rf\"")
    private String linkDobroRF;

    @Column(name = "\"image\"")
    private byte[] image;

    @Column(name = "\"date_event\"")
    private Timestamp dateEvent;
}
