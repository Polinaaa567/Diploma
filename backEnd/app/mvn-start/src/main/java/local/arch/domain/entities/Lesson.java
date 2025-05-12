package local.arch.domain.entities;

import lombok.Data;

@Data
public class Lesson {
    private Integer lessonID;
   
    private String headline;
   
    private String link;
   
    private Integer numberPoints; 

    private Boolean status;

    private String description;
}
