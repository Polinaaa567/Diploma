package local.arch.infrastructure.controller.event;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.config.IFileConfig;
import local.arch.application.interfaces.page.event.IEventsService;
import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.UserEvent;
import local.arch.infrastructure.builder.event_annotation.BuiltEvent;
import local.arch.infrastructure.token.ITokenKey;

@Path("/admin/events")
public class EventsAdmin {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);
    }

    @Inject
    ITokenKey tokenKey;

    @Inject
    @BuiltEvent
    IEventsService eventsService;

    @Inject
    IFileConfig fileConfig;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addEventsAdmin(
            @Valid Event event,
            @HeaderParam("token") String token) {

        // добавить мероприятие

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

            if (event.getImage() != null && !event.getImage().isBlank()) {
                String imageUrl = fileConfig.saveImageFromBase64(event.getImage(), "events");
                event.setImageUrl(imageUrl);
            }

            eventsService.addEvent(event);

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
    @DELETE
    @Path("/{eventID}")
    public Response deleteEventsAdmin(
            @PathParam("eventID") Integer eventID,
            @HeaderParam("token") String token) {

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

        if (eventID != null) {
            try {

                eventsService.deleteEvent(eventID);

                return Response.ok(buildErrorMessage("Мероприятие успешно удалено", true).build())
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
        } else {
            return Response.ok(buildErrorMessage("Неверный ID", false).build()).build();
        }

        // удалить мероприятие
    }

    // +
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeEventInfoAdmin(
            @Valid Event event,
            @HeaderParam("token") String token) {

        // изменить информацию о мероприятии

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

            eventsService.changeEventInfo(event.getEventID(), event);

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
    @GET
    @Produces("application/json")
    @Path("/{eventsID}/users")
    public Response getUsersByEvent(@PathParam("eventsID") Integer eventsID) {

        // Получить пользователей по мероприятию

        List<UserEvent> users = eventsService.receiveUsersByEvent(eventsID);

        if (users != null) {

            users.forEach(u -> {
                arrayBuilder.add(
                        Json.createObjectBuilder()
                                .add("id", u.getUser().getUserID())
                                .add("lastName", u.getUser().getLastName() != null ? u.getUser().getLastName() : "")
                                .add("name", u.getUser().getName() != null ? u.getUser().getName() : "")
                                .add("patronymic",
                                        u.getUser().getPatronymic() != null ? u.getUser().getPatronymic() : "")
                                .add("isParticipant", u.getUser().getStatus())
                                .add("stampParticipate",
                                        u.getStampParticipate() != null ? u.getStampParticipate() : false)
                                .add("timeParticipate", u.getTimeParticipate() != null ? u.getTimeParticipate() : 0.0));
            });

            return Response.ok(arrayBuilder.build()).build();
        } else {
            return Response.ok("[]").build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{eventID}/participance")
    public Response changeInfoUserInEvents(
            @PathParam("eventID") Integer eventID,
            List<UserEvent> userEvent,
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

            userEvent.forEach(ue -> eventsService.saveInfoParticipance(eventID, ue));

            return Response.ok(buildErrorMessage("Данные сохранены", true).build())
                    .build();
        } catch (Exception e) {
            return Response.ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .build();
        }
    }

    @GET
    @Path("/types")
    public Response getTypesEvents() {

        List<String> types = eventsService.getTypesEvents();

        return Response.ok(types).build();
    }
}
