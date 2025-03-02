package local.arch.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/rating")
public class rating {
    
    @GET
    @Produces("application/json")
    public Response getAllUsersInRating() {

        // Список пользователей в рейтинге по баллам

        // имя, фамилия, кол-во баллов или уровень

        return Response.ok("").build();
    }

    @GET
    @Produces("application/json")
    @Path("/{userID}/achievements")
    public Response getAchievements(@PathParam("userID") String userID) {

        // достижения пользователя

        // грамоты , как то их собрать в пдф
        
        return Response.ok("").build();
    }
}
