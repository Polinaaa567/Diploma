package local.arch.infrastructure.controller.event;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.event.IEventsService;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.builder.event_annotation.BuiltEvent;

@Path("/admin/events")
public class EventsAdmin {

    StringBuilder eventJson = new StringBuilder();

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltEvent
    IEventsService eventsService;

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addEventsAdmin(@Valid Event event) {

        try {
            eventsService.addEvent(event);
            return Response.ok().build();

        } catch (ConstraintViolationException e) {
            throw e;
        }

        // добавить мероприятие
    }

    // +
    @DELETE
    @Path("/{eventID}")
    public Response deleteEventsAdmin(@PathParam("eventID") Integer eventID) {
        if (eventID != null) {
            try {
                eventsService.deleteEvent(eventID);
                return Response.ok("{\n\"status\": true \n}").build();

            } catch (NotFoundException e) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"status\": false, \n\"message\": \"Мероприятие не найдена\" \n}")
                        .build();
            } catch (Exception e) {
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\n\"status\": false, \n\"message\": \"Внутренняя ошибка сервера\" \n}")
                        .build();
            }
        } else {
            return Response.ok("{\n\"status\": false \n\"message\": \"Неверный ID\" \n}").build();
        }

        // удалить мероприятие

    }

    // +
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeEventInfoAdmin(@Valid Event event) {

        // изменить информацию о мероприятии

        try {
            eventsService.changeEventInfo(event.getEventID(), event);
            return Response.ok().build();

        } catch (ConstraintViolationException e) {
            return Response.ok("{\n\"status\": false \n}").build();
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
            for (UserEvent u : users) {

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("id", u.getUser().getUserID())
                        .add("lastName", u.getUser().getLastName() != null ? u.getUser().getLastName() : "")
                        .add("name", u.getUser().getName() != null ? u.getUser().getName() : "")
                        .add("patronymic", u.getUser().getPatronymic() != null ? u.getUser().getPatronymic() : "")
                        .add("isParticipant", u.getUser().getStatus())
                        .add("stampParticipate", u.getStampParticipate() != null ? u.getStampParticipate() : false)
                        .add("timeParticipate", u.getTimeParticipate() != null ? u.getTimeParticipate() : 0.0);

                arrayBuilder.add(objBuilder);
            }

            return Response.ok(arrayBuilder.build()).build();
        } else {
            return Response.ok("[]").build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{eventID}/participance")
    public Response changeInfoUserInEvents(@PathParam("eventID") Integer eventID, List<UserEvent> userEvent) {

        try {
            for (UserEvent ue : userEvent) {
                eventsService.saveInfoParticipance(eventID, ue);
            }
            return Response.ok("ok").build();
        } catch (Exception e) {
            return Response.ok(e).build();
        }
    }

    @GET
    @Path("/types")
    public Response getTypesEvents() {

        List<String> types = eventsService.getTypesEvents();

        return Response.ok(types).build();
    }
}
