package local.arch.domain.entities;

import java.sql.Timestamp;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class News {
    private Integer newsID;

    @NotNull(message = "Заголовок не должен быть пустым")
    private String headline;
    
    @NotNull(message = "Описание не должно быть пустым")
    private String description;

    private Timestamp dateCreation;

    private List<Byte[]> imageData ;
}
