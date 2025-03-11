package local.arch.infrastructure.controller.education;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/education")
public class Education {

    @GET
    @Produces("application/json")
    @Path("/{userID}")
    public Response getAllLessonSheet(@PathParam("userID") String userID) {

        // Получить список всех уроков

        // 
        
        return Response.ok("OK get all lessons").build();
    }

    // @GET
    // @Produces("application/json")
    // @Path("/{userID}")
    // public Response getListComplitedLessons(@PathParam("userID") String userID) {

    //     // список пройденных уроков

    //     return Response.ok("OK get users lessons").build();
    // }

    @POST
    @Produces("application/json")
    @Path("/{userID}/{lessonID}")
    public Response sendPointsToUser(@PathParam("userID") String userID, @PathParam("lessonID") String lessonID) {

        // отправка баллов пользователю за прохождения урока

        return Response.ok("Points sent to user " + userID + " for lesson " + lessonID).build();
    }
}
