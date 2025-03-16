package local.arch.domain.entities;

import lombok.Data;

@Data
public class Lesson {
    private Integer videoLessonID;
   
    private String headline;
   
    private String link;
   
    private Integer numberPointsForLesson; 
}
