package local.arch.infrastructure.controller.users_data;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import local.arch.apllication.service.user_service.IUserService;
import local.arch.infrastructure.builder.Built;

@Path("/rating")
public class Rating {
    @Inject
    @Built
    IUserService userService;
        
    @GET
    @Produces("application/json")
    public Response getAllUsersInRating() {

        // Список пользователей в рейтинге по баллам

        // имя, фамилия, кол-во баллов или уровень

        String msg = userService.receiveUserRating();
        
        return Response.ok("").build();
    }

    @GET
    @Produces("application/json")
    @Path("/{userID}/achievements")
    public Response getAchievements(@PathParam("userID") int userID) {

        // достижения пользователя

        // грамоты , как то их собрать в пдф

        
        return Response.ok("").build();
    }
}
