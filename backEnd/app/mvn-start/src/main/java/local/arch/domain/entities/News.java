package local.arch.domain.entities;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class News {
    private int newsID;

    private String headlineNews;
    
    private String descriptionNews;

    private Timestamp dateCreation;

    private Byte imageData;
}
