package local.arch.infrastructure.validation;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ValidationError> errors = new ArrayList<>();
        
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            ValidationError error = new ValidationError();
            error.setStatus(false);
            error.setMessage(violation.getMessage());
            errors.add(error);
        }
        
        return Response.status(Response.Status.BAD_REQUEST)
                     .entity(errors)
                     .type(MediaType.APPLICATION_JSON)
                     .build();
    }
    
}
