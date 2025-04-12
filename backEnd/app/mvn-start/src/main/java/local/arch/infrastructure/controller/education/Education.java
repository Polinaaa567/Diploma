package local.arch.infrastructure.controller.education;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.education.IEducationService;
import local.arch.domain.entities.Lesson;
import local.arch.infrastructure.builder.education_annotation.BuiltEducation;

@Path("/education")
public class Education {

    StringBuilder educationJson = new StringBuilder();

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject 
    @BuiltEducation
    IEducationService educationService;

    // -
    @GET
    @Produces("application/json")
    @Path("/{userID}")
    public Response getAllLessonSheet(@PathParam("userID") Integer userID) {

        // Получить список всех уроков
        List<Lesson> lessons = educationService.receiveLessons(userID);
        
        return Response.ok(lessons).build();
    }

    // -
    @POST
    @Produces("application/json")
    @Path("/{userID}/{lessonID}")
    public Response sendPointsToUser(@PathParam("userID") Integer userID, @PathParam("lessonID") Integer lessonID) {

        // отправка баллов пользователю за прохождения урока

        Boolean result = educationService.sendPointsToUser(userID, lessonID);

        return Response.ok(result).build();
    }
}
