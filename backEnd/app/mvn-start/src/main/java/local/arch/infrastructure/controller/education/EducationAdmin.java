package local.arch.infrastructure.controller.education;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.education.IEducationService;
import local.arch.domain.entities.page.Lesson;
import local.arch.infrastructure.builder.education_annotation.BuiltEducation;
import local.arch.infrastructure.token.ITokenKey;

@Path("/admin/education")
public class EducationAdmin {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);
    }

    @Inject
    ITokenKey tokenKey;

    @Inject
    @BuiltEducation
    IEducationService educationService;

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addLessons(
            Lesson lessonData,
            @HeaderParam("token") String token) {

        try {
            if (!tokenKey.isTokenValid(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(buildErrorMessage("Недействительный токен", false).build())
                        .build();
            }

            if (!tokenKey.isAdmin(token)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(buildErrorMessage("Недостаточно прав", false).build())
                        .build();
            }

            educationService.addLesson(lessonData);

            return Response.ok(buildErrorMessage("Данные сохранены", true).build())
                    .build();
        } catch (NotFoundException e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // +
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeLesson(
            @QueryParam("lessonID") Integer lessonID,
            Lesson lessonData,
            @HeaderParam("token") String token) {

        // изменить урок

        if (!tokenKey.isTokenValid(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(buildErrorMessage("Недействительный токен", false).build())
                    .build();
        }

        if (!tokenKey.isAdmin(token)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(buildErrorMessage("Недостаточно прав", false).build())
                    .build();
        }

        if (lessonID != null) {
            educationService.changeLessonInfo(lessonID, lessonData);

            return Response.ok(buildErrorMessage("Данные успешно изменены", true).build()).build();
        } else {
            return Response.ok(buildErrorMessage("Неверный ID", false).build()).build();
        }
    }

    // ? или это
    @DELETE
    public Response removeLesson(
            @QueryParam("lessonID") Integer lessonID,
            @HeaderParam("token") String token) {

        // удалить урок

        if (!tokenKey.isTokenValid(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(buildErrorMessage("Недействительный токен", false).build())
                    .build();
        }

        if (!tokenKey.isAdmin(token)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(buildErrorMessage("Недостаточно прав", false).build())
                    .build();
        }

        if (lessonID != null) {
            educationService.deleteLesson(lessonID);
            
            return Response.ok(buildErrorMessage("Урок удалён", true).build()).build();
        } else {
            return Response.ok(buildErrorMessage("Неверный ID", false).build()).build();
        }
    }

    // ? или это
    @DELETE
    @Path("/{lessonID}")
    public Response removeLesson1(
            @PathParam("lessonID") Integer lessonID,
            @HeaderParam("token") String token) {

        // удалить урок

        if (!tokenKey.isTokenValid(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(buildErrorMessage("Недействительный токен", false).build())
                    .build();
        }

        if (!tokenKey.isAdmin(token)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(buildErrorMessage("Недостаточно прав", false).build())
                    .build();
        }

        if (lessonID != null) {
            educationService.deleteLesson(lessonID);
            return Response.ok(buildErrorMessage("Урок удалён", true).build()).build();
        } else {
            return Response.ok(buildErrorMessage("Неверный ID", false).build()).build();
        }
    }
}
