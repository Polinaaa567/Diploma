package local.arch.infrastructure.controller.education;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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

    // +
    @GET
    @Produces("application/json")
    public Response getAllLessonSheet(@QueryParam("userID") Integer userID, @QueryParam("lessonID") Integer lessonID) {

        // Получить список всех уроков

        if (lessonID == null) {
            List<Lesson> lessons = educationService.receiveLessons(userID);
            if (userID != null) {
                for (Lesson l : lessons) {
                    JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                            .add("id", l.getLessonID())
                            .add("headline", l.getHeadline())
                            .add("link", l.getLink())
                            .add("description", l.getDescription() != null ? l.getDescription() : "null")
                            .add("numberPoints", l.getNumberPoints())
                            .add("status", l.getStatus());

                    arrayBuilder.add(objBuilder);
                }

                return Response.ok(arrayBuilder.build()).build();
            } else {
                for (Lesson l : lessons) {
                    JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                            .add("id", l.getLessonID())
                            .add("headline", l.getHeadline())
                            .add("link", l.getLink())
                            .add("description", l.getDescription() != null ? l.getDescription() : "null");

                    arrayBuilder.add(objBuilder);
                }

                return Response.ok(arrayBuilder.build()).build();
            }

        } else {
            Lesson l = educationService.receiveLessonInfo(lessonID);
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("headline", l.getHeadline())
                    .add("link", l.getLink())
                    .add("numberPoints", l.getNumberPoints())
                    .add("description", l.getDescription() != null ? l.getDescription() : "null");

            return Response.ok(objBuilder.build()).build();
        }
    }

    // +
    @POST
    @Produces("application/json")
    @Path("/{userID}/{lessonID}")
    public Response sendPointsToUser(@PathParam("userID") Integer userID, @PathParam("lessonID") Integer lessonID) {

        // отправка баллов пользователю за прохождения урока

        Boolean result = educationService.sendPointsToUser(userID, lessonID);

        return Response.ok(result).build();
    }
}
