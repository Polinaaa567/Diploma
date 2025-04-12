package local.arch.domain.entities;

import java.sql.Timestamp;
// import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private Integer eventID;

    private String name;

    private String description;

    private String date;

    private String address;

    private String format;

    private Integer numberParticipants;

    private Integer maxCountParticipants;

    private String type;

    private Integer age;

    private Integer points;

    private String stateEvent;

    private Timestamp dateCreation;

    private String linkDobroRF;

    private Byte[] image;

    private Boolean stampParticipate;

    private Double timeParticipate;

    private Boolean statusParticipate;

    private Boolean status;

    private Boolean isRelevance;

    private String message;

    private Boolean isParticipation;
}
