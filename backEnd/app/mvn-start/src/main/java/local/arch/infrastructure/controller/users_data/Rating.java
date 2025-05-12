package local.arch.infrastructure.controller.users_data;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.user.IUserService;
import local.arch.domain.entities.User;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;

@Path("/rating")
public class Rating {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltUser
    IUserService userService;

    // -
    @GET
    @Produces("application/json")
    public Response getAllUsersInRating(@QueryParam("userID") Integer userID) {

        // Список пользователей в рейтинге по баллам

        // имя, фамилия, кол-во баллов и уровень

        // List<User> results = null;

        // userService.receiveUserRating(userID);

        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("id", 1)
                .add("lastName", "mal")
                .add("name", "ol")
                .add("points", 200)
                .add("maxPoint", 1000)
                .add("level", 1)
                .add("percent", 8.98);

        arrayBuilder.add(objBuilder);

        JsonObjectBuilder objBuilder2 = Json.createObjectBuilder()
                .add("id", 2)
                .add("lastName", "kol")
                .add("name", "po")
                .add("points", 2000)
                .add("maxPoint", 2800)
                .add("level", 3)
                .add("percent", 4.33);

        arrayBuilder.add(objBuilder2);

        return Response.ok(arrayBuilder.build()).build();
    }

    // -
    @GET
    @Produces("application/json")
    @Path("/{userID}/achievements")
    public Response getAchievements(@PathParam("userID") int userID) {

        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("points", 200)
                .add("level", 1)
                .add("certificate", "[]");
        
        // достижения пользователя

        // грамоты , как то их собрать в пдф

        return Response.ok(objBuilder.build()).build();
    }
}
