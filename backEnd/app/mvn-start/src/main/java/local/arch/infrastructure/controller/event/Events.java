package local.arch.infrastructure.controller.event;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.apllication.interfaces.event.IEventsService;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.builder.BuiltEvent;

@Path("/events")
public class Events {

    private Jsonb jsonb = JsonbBuilder.create();
    StringBuilder eventJson = new StringBuilder();

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltEvent
    IEventsService eventsService;

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getEvents() {

        List<Event> ev = eventsService.receiveEvents();

        for (Event event : ev) {

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("id", event.getEventID())
                    .add("date", event.getDateEvent().toString())
                    .add("name", event.getNameEvent())
                    .add("description", event.getDescriptionEvent())
                    .add("photo", event.getImage() != null ? event.getImage().toString() : "[]");

            arrayBuilder.add(objBuilder);
        }
        return Response.ok(arrayBuilder.build()).build();

    }

    @GET
    @Produces("application/json")
    @Path("/past")
    public Response getPastUsersEvents(@QueryParam("userID") Integer userID) {

        // Список прошедших мероприятий пользователя
        try {
            List<Event> ev = eventsService.receivePastEventsUser(userID);

            for (Event event : ev) {

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("id", event.getEventID())
                        .add("date", event.getDateEvent().toString())
                        .add("name", event.getNameEvent())
                        .add("description", event.getDescriptionEvent())
                        .add("photo", event.getImage() != null ? event.getImage().toString() : "[]")
                        .add("stampParticipate",
                                event.getStampParticipate() != null ? event.getStampParticipate().toString() : "null")
                        .add("timeParticipate",
                                event.getTimeParticipate() != null ? event.getTimeParticipate().toString() : "null");
                ;
                arrayBuilder.add(objBuilder);
            }

            return Response.ok(arrayBuilder.build()).build();

        } catch (Exception e) {
            return Response.ok("Ошибка: " + e).build();

        }
    }

    @GET
    @Produces("application/json")
    @Path("/future")
    public Response getFutureUsersEvents(@QueryParam("userID") Integer userID) {

        // Список будущих мероприятий пользователя

        try {
            List<Event> ev = eventsService.receiveFutureEventsUser(userID);

            for (Event event : ev) {

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("id", event.getEventID())
                        .add("date", event.getDateEvent().toString())
                        .add("name", event.getNameEvent())
                        .add("description", event.getDescriptionEvent())
                        .add("photo", event.getImage() != null ? event.getImage().toString() : "[]");

                arrayBuilder.add(objBuilder);
            }

            return Response.ok(arrayBuilder.build()).build();

        } catch (Exception e) {
            return Response.ok("Ошибка: " + e).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{eventID}")
    public Response getEventInfo(@PathParam("eventID") Integer eventID, @QueryParam("userID") Integer userID) {
        UserEvent userEvent = new UserEvent();
        userEvent.setEventID(eventID);
        userEvent.setUserID(userID);

        Event event = eventsService.receiveEventInfo(userEvent);

        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("date", event.getDateEvent().toString())
                .add("name", event.getNameEvent().toString())
                .add("description", event.getDescriptionEvent().toString())
                .add("photo", event.getImage() != null ? event.getImage().toString() : "[]")
                .add("status", event.getStatusParticipate())
                .add("address", event.getAddressEvent() != null ? event.getAddressEvent().toString() : "null")
                .add("format", event.getEventFormat() != null ? event.getEventFormat().toString() : "null")
                .add("type", event.getEventType() != null ? event.getEventType().toString() : "null")
                .add("maxCountParticipants", event.getMaxNumberParticipants() != null ? event.getMaxNumberParticipants() : 0)
                .add("countParticipants", event.getNumberParticipants())
                .add("age", event.getAgeRestrictions() != null ? event.getAgeRestrictions() : 16)
                .add("points", event.getNumberPointsEvent() != null ?event.getNumberPointsEvent() : 0 );

        return Response.ok(objBuilder.build()).build();
    }

    @POST
    @Produces("application/json")
    @Path("/sign-up")
    public Response signUpForEvent(String signUpInfoJSON) {

        UserEvent userEvent = jsonb.fromJson(signUpInfoJSON, UserEvent.class);

        String ev = eventsService.signUpForEvent(userEvent);

        return Response.ok(ev).build();
    }

    @POST
    @Produces("application/json")
    @Path("/{eventID}/users/{userID}")
    public Response deleteUsersEvent(@PathParam("eventID") Integer eventID, @PathParam("userID") Integer userID) {

        UserEvent userEvent = new UserEvent();
        userEvent.setEventID(eventID);
        userEvent.setUserID(userID);

        String ev = eventsService.deleteUsersEvent(userEvent);

        return Response.ok(ev).build();

        // Отменить запись на мероприятие у пользователя
    }
}
