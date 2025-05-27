package local.arch.domain.entities.page;

import java.util.Calendar;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class News {
    private Integer newsID;

    @NotBlank(message = "Заголовок не должен быть пустым")
    private String headline;
    
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;

    private Calendar dateCreation;

    private String image;

    private String imageUrl;
}
