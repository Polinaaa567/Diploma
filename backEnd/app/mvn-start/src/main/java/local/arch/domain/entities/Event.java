package local.arch.domain.entities;

import java.sql.Timestamp;
import java.util.Calendar;

import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.DefaultValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private Integer eventID;
    
    @NotBlank(message="Название не должно быть пустым")
    private String name;

    @NotBlank(message="Описание не должно быть пустым")
    private String description;

    private Calendar dateC;

    @NotBlank(message="Дата проведения мероприятия не должна быть пустой")
    private String date;

    @DefaultValue("Пр. Советский 73, к.2, л.1")
    private String address;

    public void setAddress(String address) {
        if (address == null) {
            this.address = "Пр. Советский 73, к.2, л.1";
        } else {
            this.address = address;
        }
    }

    @DefaultValue("очно")
    private String format;

    public void setFormat(String format) {
        if (format == null) {
            this.format = "очно";
        } else {
            this.format = format;
        }
    }

    private Integer numberParticipants;

    private Integer maxCountParticipants = 0;

    public void setMaxCountParticipants(Integer maxCountParticipants) {
        if (maxCountParticipants == null) {
            this.maxCountParticipants = 0;
        } else {
            this.maxCountParticipants = maxCountParticipants;
        }
    }

    @DefaultValue("Событийное")
    private String type;

    public void setType(String type) {
        if (type == null) {
            this.type = "Событийное";
        } else {
            this.type = type;
        }
    }

    private Integer age;
    public void setAge(Integer age) {
        if (age == null) {
            this.age = 16;
        } else {
            this.age = age;
        }
    }

    private Integer points = 50;
    public void setPoints(Integer points) {
        if (points == null) {
            this.points = 50;
        } else {
            this.points = points;
        }
    }

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
