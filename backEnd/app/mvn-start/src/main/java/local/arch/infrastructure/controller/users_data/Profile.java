package local.arch.infrastructure.controller.users_data;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.user.IUserService;
import local.arch.domain.entities.page.User;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;
import local.arch.infrastructure.token.ITokenKey;

@Path("/profile/users")
public class Profile {

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

    // +
    @GET
    @Produces("application/json")
    public Response getInfoAboutUsers(
            @QueryParam("userID") Integer userID,
            @HeaderParam("token") String token) {

        // Получить данные о пользователе

        if (!tokenKey.isTokenValid(token)) {
            return Response.ok(buildErrorMessage("Недействительный токен", false).build())
                    .build();
        }

        if (userID != null) {
            User user = userService.receiveUserData(userID);

            if (user != null) {
                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("lastName", user.getLastName() != null ? user.getLastName() : "")
                        .add("name", user.getName() != null ? user.getName() : "")
                        .add("patronymic", user.getPatronymic() != null ? user.getPatronymic() : "")
                        .add("clothingSize", user.getClothingSize() != null ? user.getClothingSize() : "xs")
                        .add("ageStamp", user.getAgeStamp() != null ? user.getAgeStamp() : "16-17")
                        .add("formEducation", user.getFormEducation() != null ? user.getFormEducation() : "очная")
                        .add("basisEducation", user.getBasisEducation() != null ? user.getBasisEducation() : "бюджет");

                return Response.ok(objBuilder.build()).build();

            } else {
                return Response.ok(buildErrorMessage("userID = null", false).build()).build();
            }
        } else {
            return Response.ok(buildErrorMessage("Ошибка при нахождении пользователя", false).build()).build();
        }
    }

    // +
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeDataAboutUsers(
            User user,
            @HeaderParam("token") String token) {

        // Изменить данные о пользователе

        try {
            if (!tokenKey.isTokenValid(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(buildErrorMessage("Недействительный токен", false).build())
                        .build();
            }

            userService.updateUserData(user);

            return Response.ok(buildErrorMessage("Данные сохранены", true).build())
                    .build();
        } catch (NotFoundException e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
