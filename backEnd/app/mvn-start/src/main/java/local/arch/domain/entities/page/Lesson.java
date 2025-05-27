package local.arch.domain.entities.page;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Lesson {
    private Integer lessonID;
   
    @NotBlank(message = "Название не должно быть пустым")
    private String headline;
   
    @NotBlank(message = "Ссылка не должна быть пустой")
    private String link;
   
    private Integer numberPoints; 

    private Boolean status;

    private String description;

    private String message;
}
