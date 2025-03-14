package local.arch.domain.entities;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private int eventID;

    private String nameEvent;

    private String descriptionEvent;

    private Timestamp dateEvent;

    private String addressEvent;

    private String eventFormat;

    private String timeEvent;

    private int numberParticipants;

    private int maxNumberParticipants;

    private String eventType;

    private Integer ageRestrictions;

    private Integer numberPointsEvent;

    private String stateEvent;

    private Timestamp dateCreation;

    private String linkDobroRF;

    private byte[] image;
}
