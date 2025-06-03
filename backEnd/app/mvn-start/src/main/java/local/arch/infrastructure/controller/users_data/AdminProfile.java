package local.arch.infrastructure.controller.users_data;

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
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.user.IUserService;
import local.arch.domain.entities.page.Rating;
import local.arch.domain.entities.page.User;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;
import local.arch.infrastructure.token.ITokenKey;

@Path("/admin/profile/users")
public class AdminProfile {
    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);
    }

    @Inject
    ITokenKey tokenKey;

    @Inject
    @BuiltUser
    IUserService userService;

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @GET
    @Path("/{userID}")
    @Produces("application/json")
    public Response getInfoAboutUsers(
            @PathParam("userID") Integer userID,
            @HeaderParam("token") String token) {

        // Получить данные о пользователе

        if (!tokenKey.isTokenValid(token)) {
            return Response.ok(buildErrorMessage("Недействительный токен", false).build())
                    .build();
        }

        if (!tokenKey.isAdmin(token)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(buildErrorMessage("Недостаточно прав", false).build())
                    .build();
        }

        if (userID != null) {
            User user = userService.receiveUserData(userID);
            Rating achievements = userService.receiveCertificate(userID);

            Double percent = ((double) achievements.getPoint() / (double) achievements.getMaxPoint()) * 100;
            Double roundedNumber = Math.round(percent * 100.0) / 100.0;
         achievements.getCertificates().forEach(arrayBuilder::add);

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("lastName", user.getLastName() != null ? user.getLastName() : "")
                    .add("name", user.getName() != null ? user.getName() : "")
                    .add("patronymic", user.getPatronymic() != null ? user.getPatronymic() : "")
                    .add("clothingSize", user.getClothingSize() != null ? user.getClothingSize() : "xs")
                    .add("ageStamp", user.getAgeStamp() != null ? user.getAgeStamp() : "16-17")
                    .add("formEducation", user.getFormEducation() != null ? user.getFormEducation() : "очная")
                    .add("basisEducation", user.getBasisEducation() != null ? user.getBasisEducation() : "бюджет")
                    .add("points", achievements.getPoint())
                    .add("level", achievements.getLevel())
                    .add("maxPoint", achievements.getMaxPoint())
                    .add("percent", roundedNumber)
                    .add("certificates", arrayBuilder);
            if (achievements.getCertificates().isEmpty()) {
                
            }
                    return Response.ok(objBuilder.build()).build();

        } else {
            return Response.ok(buildErrorMessage("Ошибка при нахождении пользователя", false).build()).build();
        }
    }
}
