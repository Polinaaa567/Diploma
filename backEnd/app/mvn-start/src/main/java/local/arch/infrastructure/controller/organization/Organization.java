package local.arch.infrastructure.controller.organization;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/organization")
public class Organization {
    
    @GET
    @Produces("application/json")
    public Response parseInfoAboutOrganization() {
	    
        //Получить данные об организации  
		
        return Response.ok("check").build();
	}
}