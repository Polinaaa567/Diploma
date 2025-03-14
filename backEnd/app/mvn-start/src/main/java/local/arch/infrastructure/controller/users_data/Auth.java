package local.arch.infrastructure.controller.users_data;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import local.arch.apllication.service.user_service.IUserService;
import local.arch.infrastructure.builder.BuiltUser;

@Path("/auth")
public class Auth {

    // @Inject
    // @BuiltUser
    // IUserService userService;
    
    @POST
    @Produces("application/json")
    @Path("/register") 
    public Response registration(String userInfoRegJSON) {
        
        // регистрация

        // String message = userService.registrationUser(userInfoRegJSON);

        return Response.ok("message").build();
    }


    @POST
    @Produces("application/json")
    @Path("/login")
    public Response logInToSystem(String userInfoRegJSON) {
       
        // вход в систему
        // String message = userService.loginUser(userInfoRegJSON);
       
        return Response.ok("message").build();
    } 

    @PUT
    @Produces("application/json")
    @Path("/password/reset") 
    public Response resetPassword(String userInfoRegJSON) {
        
        // изменить пароль в бд

        try {
            // String message = userService.changeUserPasswd(userInfoRegJSON);

            return Response.ok("message").build();
            
        } catch (Exception e) {
            return Response.ok("Ошибка" + e).build();
        }
    }
}