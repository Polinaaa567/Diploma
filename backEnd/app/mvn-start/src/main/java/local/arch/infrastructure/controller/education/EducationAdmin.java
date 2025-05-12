package local.arch.infrastructure.controller.education;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import local.arch.application.interfaces.education.IEducationService;
import local.arch.domain.entities.Lesson;
import local.arch.infrastructure.builder.education_annotation.BuiltEducation;

@Path("/admin/education")
public class EducationAdmin {

    StringBuilder educationJson = new StringBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltEducation
    IEducationService educationService;

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public void addLessons(Lesson lessonData) {

        educationService.addLesson(lessonData);
    }

    // +
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public void changeLesson(@QueryParam("lessonID") Integer lessonID, Lesson lessonData) {

        // изменить урок

        educationService.changeLessonInfo(lessonID, lessonData);
    }

    // ?
    @DELETE
    @Path("/{lessonID}")
    public void removeLesson(@PathParam("lessonID") Integer lessonID) {

        // удалить урок

        educationService.deleteLesson(lessonID);
    }
}
