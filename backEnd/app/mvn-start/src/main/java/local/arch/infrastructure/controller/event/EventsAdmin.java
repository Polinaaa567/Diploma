package local.arch.infrastructure.controller.event;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import local.arch.infrastructure.builder.BuiltEvent;
import local.arch.application.interfaces.event.IEventsService;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.User;

@Path("/admin/events")
public class EventsAdmin {

    private Jsonb jsonb = JsonbBuilder.create();
    StringBuilder eventJson = new StringBuilder();

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltEvent
    IEventsService eventsService;

    // +
    @POST
    @Produces("application/json")
    public void addEventsAdmin(String eventDataJSON) {

        Event userEvent = jsonb.fromJson(eventDataJSON, Event.class);

        eventsService.addEvent(userEvent);

        // добавить мероприятие
    }

    // +
    @Path("/{eventID}")
    @DELETE
    public void deleteEventsAdmin(@PathParam("eventID") Integer eventID) {

        eventsService.deleteEvent(eventID);

        // удалить мероприятие
    }

    // +
    @PUT
    @Produces("application/json")
    @Path("/{eventID}")
    public void changeEventInfoAdmin(@PathParam("eventID") Integer eventID, String eventDataJSON) {

        // изменить информацию о мероприятии

        Event userEvent = jsonb.fromJson(eventDataJSON, Event.class);

        eventsService.changeEventInfo(eventID, userEvent);

    }

    // -
    @GET
    @Produces("application/json")
    @Path("/{eventsID}/users")
    public Response getUsersByEvent(@PathParam("eventsID") Integer eventsID) {
        
        // Получить пользователей по мероприятию

        List<User> users = eventsService.receiveUsersByEvent(eventsID);

        if (users != null) {
            for (User u : users) {

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("id", u.getUserID())
                        .add("lastName", u.getLastName() != null ? u.getLastName() : "")
                        .add("name", u.getName() != null ? u.getName() : "")
                        .add("patronymic", u.getPatronymic() != null ? u.getPatronymic() : "")
                        .add("status", u.getStatus());

                arrayBuilder.add(objBuilder);
            }

            return Response.ok(arrayBuilder.build()).build();
        } else {
            return Response.ok("{\n\"status\": false\n}").build();
        }
    }

    // @PUT
    // @Produces("application/json")
    // @Path("/{eventsID}/participance")
    // public Response changeInfoUserInEvents(@PathParam("eventsID") Integer
    // eventsID, String eventDataJSON) {

    // return Response.ok("check users by events").build();
    // }
}
