package local.arch.controllers;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/profile")
public class profile {
 
    @GET
    @Produces("application/json")
    @Path("/users/{userID}")
    public Response getInfoAboutUsers(@PathParam("userID") String userID) {
        
        // Получить данные о пользователе

        return Response.ok("check").build();
    }
    
    // @PUT
    // @Produces("application/json")
    // @Path("/users/{userID}")
    // public Response changeDataAboutUsers(@PathParam("userID") Integer userID, String userDataJSON) {
        
    //     // Изменить данные о пользователе

    //     return Response.ok("check").build();
    // }
}
