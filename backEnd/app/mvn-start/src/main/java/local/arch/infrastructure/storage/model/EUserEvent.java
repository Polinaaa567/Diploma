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
@Table(name = "\"users_events\"")
public class EUserEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_events_user_event_id_seq")
    @SequenceGenerator(name = "users_events_user_event_id_seq", sequenceName = "users_events_user_event_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"user_event_id\"")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "\"fk_user_id\"", referencedColumnName = "\"id_user\"")
    private EUser fkUserID;

    @OneToOne
    @JoinColumn(name = "\"fk_event_id\"", referencedColumnName = "\"event_id\"")
    private EEvent fkEventID;

    @Column(name = "\"date_creation\"")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private Timestamp dateCreation;

    @Column(name = "\"stamp_participate\"")
    private Boolean stampParticipate;

    @Column(name = "\"time_participate\"")
    private Double timeParticipate;
}
