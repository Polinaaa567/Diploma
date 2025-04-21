package local.arch.infrastructure.controller.users_data;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.user.IUserService;
import local.arch.domain.entities.User;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;

@Path("/profile/users")
public class Profile {

    @Inject
    @BuiltUser
    IUserService userService;

    @GET
    @Produces("application/json")
    public Response getInfoAboutUsers(@QueryParam("userID") Integer userID) {

        // Получить данные о пользователе

        User user = userService.receiveUserData(userID);

        if (user != null) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("lastName", user.getLastName() != null ? user.getLastName() : "")
                    .add("name", user.getName() != null ? user.getName() : "")
                    .add("patronymic", user.getPatronymic() != null ? user.getPatronymic() : "")
                    .add("clothingSize", user.getClothingSize() != null ? user.getClothingSize() : "xs")
                    .add("ageStamp", user.getAgeStamp() != null ? user.getAgeStamp() : "16-17")
                    .add("formEducation", user.getFormEducation() != null ? user.getFormEducation() : "очная")
                    .add("basisEducation", user.getBasisEducation() != null ?user.getBasisEducation() : "бюджет");

            return Response.ok(objBuilder.build()).build();
        } else {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("status", false)
                    .add("message", "Ошибка при нахождении пользователя " + userID);

            return Response.ok(objBuilder.build()).build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeDataAboutUsers(User user) {

        // Изменить данные о пользователе
        // User user = jsonb.fromJson(userDataJSON, User.class);

        try {
            userService.updateUserData(user);

            return Response.ok("{\n\"status\": true\n}").build();
        } catch (Exception e) {
            return Response.ok("{\n\"status\": false\n}").build();

        }
    }
}
