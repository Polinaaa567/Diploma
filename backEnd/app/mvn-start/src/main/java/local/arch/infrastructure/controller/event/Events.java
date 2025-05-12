package local.arch.infrastructure.controller.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.event.IEventsService;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.builder.event_annotation.BuiltEvent;

@Path("/events")
public class Events {

    public String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(date.getTime());
    }

    public String formatDate2(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm");
        return sdf.format(date.getTime());
    }

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltEvent
    IEventsService eventsService;

    // +
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getEvents() {

        List<Event> ev = eventsService.receiveEvents();

        for (Event event : ev) {

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("id", event.getEventID())
                    .add("date", formatDate(event.getDateC()))
                    .add("name", event.getName())
                    .add("description", event.getDescription())
                    .add("image", event.getImage() != null
                            ? event.getImage().toString()
                            : "[]")
                    .add("isRelevance", event.getIsRelevance())
                    .add("isParticipation", event.getIsParticipation() != null
                            ? event.getIsParticipation()
                            : false);

            arrayBuilder.add(objBuilder);
        }

        return Response.ok(arrayBuilder.build()).build();
    }

    // +
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
                        .add("date", formatDate(event.getDateC()))
                        .add("name", event.getName())
                        .add("description", event.getDescription())
                        .add("image", event.getImage() != null
                                ? event.getImage().toString()
                                : "[]")
                        .add("stampParticipate", event.getStampParticipate() != null
                                ? event.getStampParticipate()
                                : false)
                        .add("timeParticipate", event.getTimeParticipate() != null
                                ? event.getTimeParticipate()
                                : 0.0);

                arrayBuilder.add(objBuilder);
            }

            return Response.ok(arrayBuilder.build()).build();

        } catch (Exception e) {
            return Response.ok("Ошибка: " + e).build();
        }
    }

    // +
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
                        .add("date", formatDate(event.getDateC()))
                        .add("name", event.getName())
                        .add("description", event.getDescription())
                        .add("image", event.getImage() != null
                                ? event.getImage().toString()
                                : "[]");

                arrayBuilder.add(objBuilder);
            }

            return Response.ok(arrayBuilder.build()).build();

        } catch (Exception e) {
            return Response.ok("Ошибка: " + e).build();
        }
    }

    // +
    @GET
    @Produces("application/json")
    @Path("/{eventID}")
    public Response getEventInfo(@PathParam("eventID") Integer eventID, @QueryParam("userID") Integer userID) {
        UserEvent userEvent = new UserEvent();
        userEvent.setEventID(eventID);
        userEvent.setUserID(userID);

        Event event = eventsService.receiveEventInfo(userEvent);

        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("date", formatDate(event.getDateC()))
                .add("dateC", formatDate2(event.getDateC()))
                .add("name", event.getName().toString())
                .add("description", event.getDescription().toString())
                .add("image", event.getImage() != null
                        ? event.getImage().toString()
                        : "[]")
                .add("status", event.getStatusParticipate())
                .add("address", event.getAddress() != null
                        ? event.getAddress().toString()
                        : "")
                .add("format", event.getFormat() != null
                        ? event.getFormat().toString()
                        : "")
                .add("type", event.getType() != null
                        ? event.getType().toString()
                        : "")
                .add("maxCountParticipants", event.getMaxCountParticipants() != null
                        ? event.getMaxCountParticipants()
                        : 0)
                .add("countParticipants", event.getNumberParticipants())
                .add("age", event.getAge() != null
                        ? event.getAge()
                        : 16)
                .add("points", event.getPoints() != null
                        ? event.getPoints()
                        : 0)
                .add("linkDobroRF", event.getLinkDobroRF() != null
                        ? event.getLinkDobroRF()
                        : "");

        return Response.ok(objBuilder.build()).build();
    }

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/sign-up")
    public Response signUpForEvent(UserEvent userEvent) {

        String ev = eventsService.signUpForEvent(userEvent);

        return Response.ok(ev).build();
    }

    // +
    @POST
    @Produces("application/json")
    @Path("/{eventID}/users/{userID}")
    public Response deleteUsersEvent(@PathParam("eventID") Integer eventID, @PathParam("userID") Integer userID) {

        // Отменить запись на мероприятие у пользователя

        UserEvent userEvent = new UserEvent();
        userEvent.setEventID(eventID);
        userEvent.setUserID(userID);

        String ev = eventsService.deleteUsersEvent(userEvent);

        return Response.ok(ev).build();
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/range")
    public Response eventsBetwenDate(@QueryParam("dateStart") String dateStart, @QueryParam("dateEnd") String dateEnd) {

        List<Event> events = eventsService.eventsBetwenDate(dateStart, dateEnd);

        for (Event event : events) {

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("id", event.getEventID())
                    .add("date", formatDate(event.getDateC()))
                    .add("name", event.getName())
                    .add("description", event.getDescription())
                    .add("image", event.getImage() != null
                            ? event.getImage().toString()
                            : "[]")
                    .add("isRelevance", event.getIsRelevance())
                    .add("isParticipation", event.getIsParticipation() != null
                            ? event.getIsParticipation()
                            : false);

            arrayBuilder.add(objBuilder);
        }

        return Response.ok(arrayBuilder.build()).build();
    }
}
