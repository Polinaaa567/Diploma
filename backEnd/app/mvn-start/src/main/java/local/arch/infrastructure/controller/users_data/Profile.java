package local.arch.infrastructure.controller.users_data;

import jakarta.inject.Inject;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import local.arch.apllication.interfaces.user.IUserService;
import local.arch.infrastructure.builder.BuiltUser;

@Path("/profile/users")
public class Profile{
    // @Inject
    // @BuiltUser
    // IUserService userService;
    
    @GET
    @Produces("application/json")
    @Path("/{userID}")
    public Response getInfoAboutUsers(@PathParam("userID") int userID) {
        
        // Получить данные о пользователе
        // String msg = userService.receiveUserData(userID);

        return Response.ok("check").build();
    }
    
    @PUT
    @Produces("application/json")
    public Response changeDataAboutUsers(String userDataJSON) {
        
        // Изменить данные о пользователе
        // String msg = userService.updateUserData(userDataJSON);

        return Response.ok("check").build();
    }
}
