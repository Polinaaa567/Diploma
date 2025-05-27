package local.arch.infrastructure.controller.users_data;

import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.user.IUserService;
import local.arch.domain.entities.page.Rating;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;
import local.arch.infrastructure.token.ITokenKey;

@Path("/rating")
public class RatingController {

    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);

    }

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    ITokenKey tokenKey;

    @Inject
    @BuiltUser
    IUserService userService;

    // +
    @GET
    @Produces("application/json")
    public Response getAllUsersInRating(
            @QueryParam("userID") Integer userID) {

        // Список пользователей в рейтинге по баллам

        // имя, фамилия, кол-во баллов и уровень

        List<Rating> results = userService.receiveUserRating(userID);
        
        results.forEach(r -> {
            Double percent = ((double) r.getPoint() / (double) r.getMaxPoint()) * 100;
            Double roundedNumber = Math.round(percent * 100.0) / 100.0;

            arrayBuilder.add(
                Json.createObjectBuilder()
                    .add("id", r.getInfo().getUserID())
                    .add("lastName", r.getInfo().getLastName())
                    .add("name", r.getInfo().getName())
                    .add("points", r.getPoint())
                    .add("maxPoint", r.getMaxPoint())
                    .add("level", r.getLevel())
                    .add("percent", roundedNumber)
            );
        });
        
        return Response.ok(arrayBuilder.build()).build();
    }

    // -
    @GET
    @Produces("application/json")
    @Path("/{userID}/achievements")
    public Response getAchievements(
            @PathParam("userID") Integer userID,
            @HeaderParam("token") String token) {

        if (!tokenKey.isTokenValid(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(buildErrorMessage("Недействительный токен", false).build())
                    .build();
        }

        Rating achievements = userService.receiveCertificate(userID);

        Double percent = ((double) achievements.getPoint() / (double) achievements.getMaxPoint()) * 100;
            Double roundedNumber = Math.round(percent * 100.0) / 100.0;

        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("points", achievements.getPoint())
                .add("level", achievements.getLevel())
                .add("maxPoint", achievements.getMaxPoint())
                .add("percent", roundedNumber)
                .add("certificate", Optional.ofNullable(achievements.getCertificates()).map(Object::toString).orElse(""));

        // достижения пользователя

        // грамоты , как то их собрать в пдф

        return Response.ok(objBuilder.build()).build();
    }
}
