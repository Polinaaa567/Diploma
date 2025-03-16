package local.arch.domain.entities;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private Integer eventID;

    private String nameEvent;

    private String descriptionEvent;

    private Timestamp dateEvent;

    private String addressEvent;

    private String eventFormat;

    private String timeEvent;

    private Integer numberParticipants;

    private Integer maxNumberParticipants;

    private String eventType;

    private Integer ageRestrictions;

    private Integer numberPointsEvent;

    private String stateEvent;

    private Timestamp dateCreation;

    private String linkDobroRF;

    private byte[] image;
}
