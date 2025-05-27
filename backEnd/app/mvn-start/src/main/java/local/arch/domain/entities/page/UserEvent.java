package local.arch.domain.entities.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEvent {
    private Integer userID;
   
    private Integer eventID;

    private Boolean stampParticipate;

    private Double timeParticipate;

    private User user;

    private String msg;

    private Boolean status;

    private Integer countParticipants;
}
