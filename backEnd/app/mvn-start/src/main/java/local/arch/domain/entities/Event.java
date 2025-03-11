package local.arch.domain.entities;

import java.sql.Timestamp;

import lombok.Data;

@Data
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

    private String ageRestrictions;

    private String numberPointsEvent;

    private String stateEvent;

    private Timestamp dateCreation;

    private String linkDobroRF;

    private Byte imageEvent;
}
