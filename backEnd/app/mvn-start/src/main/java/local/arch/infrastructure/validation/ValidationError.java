package local.arch.infrastructure.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError { 

    private Boolean status;
    
    private String message;
}
