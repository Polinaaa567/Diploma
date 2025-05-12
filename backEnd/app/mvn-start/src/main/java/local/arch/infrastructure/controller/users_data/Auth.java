package local.arch.infrastructure.controller.users_data;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.user.IUserService;
import local.arch.domain.entities.User;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;

@Path("/auth")
public class Auth {

    private Jsonb jsonb = JsonbBuilder.create();

    @Inject
    @BuiltUser
    IUserService userService;

    // +
    @POST
    @Produces("application/json")
    @Path("/register")
    public Response registration(String userInfoRegJSON) {

        User user = jsonb.fromJson(userInfoRegJSON, User.class);

        return Response.ok(userService.registrationUser(user)).build();
    }

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/login")
    public Response logInToSystem(User user) {

        // User user = jsonb.fromJson(userInfoRegJSON, User.class);

        return Response.ok(userService.loginUser(user)).build();
    }

    // -
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/password/reset")
    public Response resetPassword(User user) {

        // изменить пароль в бд

        if (user.getPassword() == null) {
            return Response.ok(userService.findUser(user)).build();
        } else {
            return Response.ok(userService.changeUserPasswd(user)).build();
        }
    }
}
