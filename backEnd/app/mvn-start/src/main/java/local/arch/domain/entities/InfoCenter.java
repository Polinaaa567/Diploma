package local.arch.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoCenter {
    private String nameCenter;

    private String description;
    
    private Byte imageData;
    
    private String contacts;
    
    private String address;
}
