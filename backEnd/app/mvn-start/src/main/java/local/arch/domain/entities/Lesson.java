package local.arch.domain.entities;

import lombok.Data;

@Data
public class Lesson {
    private int videoLessonID;
   
    private String headline;
   
    private String link;
   
    private Integer numberPointsForLesson; 
}
