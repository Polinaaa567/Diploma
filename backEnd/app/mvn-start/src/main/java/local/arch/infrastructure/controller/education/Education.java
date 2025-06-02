package local.arch.infrastructure.controller.education;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.Cacheable;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.education.IEducationService;
import local.arch.domain.entities.page.Lesson;
import local.arch.infrastructure.builder.education_annotation.BuiltEducation;

@Path("/education")
@Cacheable()
public class Education {

    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);

    }

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltEducation
    IEducationService educationService;

    // +
    @GET
    @Produces("application/json")
    public Response getAllLessonSheet(
            @QueryParam("userID") Integer userID,
            @QueryParam("lessonID") Integer lessonID) {

        // Получить список всех уроков

        if (lessonID == null) {
            List<Lesson> lessons = educationService.receiveLessons(userID);

            if (userID != null) {
                lessons.forEach(l -> {
                    arrayBuilder.add(Json.createObjectBuilder()
                            .add("id", l.getLessonID())
                            .add("headline", l.getHeadline())
                            .add("link", l.getLink())
                            .add("description", l.getDescription() != null ? l.getDescription() : "")
                            .add("numberPoints", l.getNumberPoints())
                            .add("status", l.getStatus()));
                });

                return Response.ok(arrayBuilder.build()).build();
            } else {
                lessons.forEach(l -> {
                    arrayBuilder.add(Json.createObjectBuilder()
                            .add("id", l.getLessonID())
                            .add("headline", l.getHeadline())
                            .add("link", l.getLink())
                            .add("description", l.getDescription() != null ? l.getDescription() : ""));
                });

                return Response.ok(arrayBuilder.build()).build();
            }

        } else {
            Lesson l = educationService.receiveLessonInfo(lessonID);
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("headline", l.getHeadline())
                    .add("link", l.getLink())
                    .add("numberPoints", l.getNumberPoints())
                    .add("description", l.getDescription() != null ? l.getDescription() : "");

            return Response.ok(objBuilder.build()).build();
        }
    }

    // +
    @POST
    @Produces("application/json")
    @Path("/{userID}/{lessonID}")
    public Response sendPointsToUser(
            @PathParam("userID") Integer userID,
            @PathParam("lessonID") Integer lessonID) {

        // отправка баллов пользователю за прохождения урока

        Lesson result = educationService.sendPointsToUser(userID, lessonID);

        return Response.ok(buildErrorMessage(result.getMessage(), result.getStatus()).build()).build();
    }
}
