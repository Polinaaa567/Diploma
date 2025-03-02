package local.arch.controllers;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/organization")
public class aboutOrganization {
    
    @GET
    @Produces("application/json")
    public Response getInfoAboutOrganization() {
	    
        //Получить данные об организации  
		
        return Response.ok("check").build();
	}
}