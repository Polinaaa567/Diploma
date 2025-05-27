package local.arch.infrastructure.controller.users_data;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.user.IUserService;
import local.arch.domain.entities.page.User;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;

@Path("/auth")
public class Auth {

    @Inject
    @BuiltUser
    IUserService userService;

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/register")
    public Response registration(User user) {

        User response = userService.registrationUser(user);

        if (response.getStatus()) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("status", response.getStatus())
                    .add("message", response.getMsg())
                    .add("id", response.getUserID())
                    .add("token", response.getToken());

            return Response.ok(objBuilder.build()).build();

        } else {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("status", response.getStatus())
                    .add("message", response.getMsg())
                    .add("token", "BAD");


            return Response.ok(objBuilder.build()).build();
        }
    }

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/login")
    public Response logInToSystem(User user) {
        User response = userService.loginUser(user);

        if (response.getStatus()) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("status", response.getStatus())
                    .add("message", response.getMsg())
                    .add("nameRole", response.getRole())
                    .add("id", response.getUserID())
                    .add("token", response.getToken());

            return Response.ok(objBuilder.build())
                    .build();

        } else {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("status", response.getStatus())
                    .add("message", response.getMsg())
                    .add("token", "BAD");
                    

            return Response.ok(objBuilder.build()).build();
        }
    }

    // -
    // @PUT
    // @Produces("application/json")
    // @Consumes("application/json")
    // @Path("/password/reset")
    // public Response resetPassword(User user) {

    //     // изменить пароль в бд

    //     if (user.getPassword() == null) {
    //         // return Response.ok(userService.findUser(user)).build();
    //     } else {
    //         return Response.ok(userService.changeUserPasswd(user)).build();
    //     }
    // }
}
