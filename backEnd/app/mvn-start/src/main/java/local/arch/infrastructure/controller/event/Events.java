package local.arch.infrastructure.controller.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.event.IEventsService;
import local.arch.domain.entities.Pagination;
import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.UserEvent;
import local.arch.infrastructure.builder.event_annotation.BuiltEvent;
import local.arch.infrastructure.token.ITokenKey;

@Path("/events")
public class Events {

    private String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(date.getTime());
    }

    private String formatDate2(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm");
        return sdf.format(date.getTime());
    }

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    private JsonObjectBuilder buildEventJson(Event event) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("id", Optional.ofNullable(event.getEventID()).orElse(0))
                .add("date", formatDate(event.getDateC()))
                .add("name", event.getName())
                .add("description", event.getDescription())
                .add("image", Optional.ofNullable(event.getImageUrl()).orElse(""));
        if (event.getIsRelevance() != null) {
            builder.add("isRelevance", event.getIsRelevance())
                    .add("isParticipation", Optional.ofNullable(event.getIsParticipation()).orElse(false));
        }

        if (event.getFormat() != null || event.getAddress() != null) {
            builder.remove("id")
                    .add("dateC", formatDate2(event.getDateC()))
                    .add("status", event.getStatusParticipate())
                    .add("address", Optional.ofNullable(event.getAddress()).orElse("Пр. Советский 73, к.2, л.1"))
                    .add("format", Optional.ofNullable(event.getFormat()).orElse("Очно"))
                    .add("type", Optional.ofNullable(event.getType()).orElse("Событийное"))
                    .add("maxCountParticipants", Optional.ofNullable(event.getMaxCountParticipants()).orElse(0))
                    .add("countParticipants", event.getNumberParticipants())
                    .add("age", Optional.ofNullable(event.getAge()).orElse(16))
                    .add("points", Optional.ofNullable(event.getPoints()).orElse(50))
                    .add("linkDobroRF", Optional.ofNullable(event.getLinkDobroRF()).orElse(""));
        }

        return builder;
    }

    private JsonObjectBuilder buildUserEventJson(Event event) {
        JsonObjectBuilder builder = buildEventJson(event);

        builder.add("stampParticipate", Optional.ofNullable(event.getStampParticipate()).orElse(false))
                .add("timeParticipate", Optional.ofNullable(event.getTimeParticipate()).orElse(0.0));

        return builder;
    }

    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {

        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);
    }

    @Inject
    @BuiltEvent
    IEventsService eventsService;

    @Inject
    ITokenKey tokenKey;

    // +
    @GET
    @Produces("application/json")
    public Response getEvents(
            @QueryParam("dateStart") String dateStart,
            @QueryParam("dateEnd") String dateEnd,
            @QueryParam("page") Integer page,
            @QueryParam("limit") @DefaultValue("5") Integer limit) {

        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

        int total = 0;
        boolean hasMore = false;

        try {
            if (dateStart != null || dateEnd != null) {
                Pagination eventsPagination = eventsService.eventsBetwenDate(dateStart, dateEnd, page, limit);

                eventsPagination.getEvents().stream()
                        .map(this::buildEventJson)
                        .forEach(arrayBuilder::add);

                total = eventsPagination.getTotalCount();
                hasMore = page != null && page > 0 && (page * limit) < total;
            } else if (page != null && page > 0) {
                Pagination eventLimit = eventsService.receiveAllEvents(page, limit);
                eventLimit.getEvents().stream()
                        .map(this::buildEventJson)
                        .forEach(arrayBuilder::add);

                total = eventLimit.getTotalCount();
                hasMore = (page * limit) < total;
            } else {
                List<Event> events = eventsService.receiveEvents();
                events.stream()
                        .map(this::buildEventJson)
                        .forEach(arrayBuilder::add);

                total = events.size();
                hasMore = false;
            }

            responseBuilder
                    .add("total", total)
                    .add("hasMore", hasMore)
                    .add("events", arrayBuilder);

            return Response.ok(responseBuilder.build()).build();

        } catch (Exception e) {
            return Response
                    .ok(buildErrorMessage("Ошибка обработки запроса: " + e.getMessage(), false).build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // +
    @GET
    @Produces("application/json")
    @Path("/past")
    public Response getPastUsersEvents(
            @QueryParam("userID") Integer userID,
            @QueryParam("page") Integer page,
            @QueryParam("limit") @DefaultValue("5") Integer limit,
            @HeaderParam("token") String token) {

        // Список прошедших мероприятий пользователя

        try {
            boolean hasMore = false;

            if (token != null) {
                if (tokenKey.isTokenValid(token)) {
                    if (userID != null) {
                        Pagination resPagination = eventsService.receivePastEventsUser(userID, page, limit);

                        resPagination.getEvents()
                                .stream().map(this::buildUserEventJson)
                                .forEach(arrayBuilder::add);
                        hasMore = page != null && page > 0 && (page * limit) < resPagination.getTotalCount();

                        JsonObjectBuilder builder = Json.createObjectBuilder()
                                .add("total", resPagination.getTotalCount())
                                .add("hasMore", hasMore)
                                .add("events", arrayBuilder);

                        return Response.ok(builder.build()).build();
                    } else {
                        return Response.ok(buildErrorMessage("UserID = null", false).build()).build();
                    }
                } else {
                    return Response.ok(buildErrorMessage("token невалидный", false).build()).build();
                }
            } else {
                return Response.ok(buildErrorMessage("token невалидный", false).build()).build();
            }

        } catch (Exception e) {
            return Response.ok(buildErrorMessage("Ошибка при получении прошедших мероприятий", false).build()).build();
        }
    }

    // +
    @GET
    @Produces("application/json")
    @Path("/future")
    public Response getFutureUsersEvents(
            @QueryParam("userID") Integer userID,
            @QueryParam("page") Integer page,
            @QueryParam("limit") @DefaultValue("5") Integer limit,
            @HeaderParam("token") String token) {

        // Список будущих мероприятий пользователя

        try {
            boolean hasMore = false;
            if (token != null) {
                if (tokenKey.isTokenValid(token)) {
                    if (userID != null) {

                        Pagination resPagination = eventsService.receiveFutureEventsUser(userID, page, limit);

                        resPagination.getEvents()
                                .stream().map(this::buildEventJson)
                                .forEach(arrayBuilder::add);

                        hasMore = page != null && page > 0 && (page * limit) < resPagination.getTotalCount();

                        JsonObjectBuilder builder = Json.createObjectBuilder()
                                .add("total", resPagination.getTotalCount())
                                .add("hasMore", hasMore)
                                .add("events", arrayBuilder);

                        return Response.ok(builder.build()).build();

                    } else {
                        return Response.ok(buildErrorMessage("UserID = null", false).build()).build();
                    }
                } else {
                    return Response.ok(buildErrorMessage("token невалидный", false).build()).build();
                }
            } else {
                return Response.ok(buildErrorMessage("token невалидный", false).build()).build();
            }
        } catch (Exception e) {
            return Response.ok(buildErrorMessage("Ошибка при получении прошедших мероприятий", false).build()).build();
        }
    }

    // +
    @GET
    @Produces("application/json")
    @Path("/{eventID}")
    public Response getEventInfo(
            @PathParam("eventID") Integer eventID,
            @QueryParam("userID") Integer userID) {

        if (eventID != null) {
            UserEvent userEvent = new UserEvent();
            userEvent.setEventID(eventID);
            userEvent.setUserID(userID);

            Event event = eventsService.receiveEventInfo(userEvent);

            return Response.ok(buildEventJson(event)
                    .build()).build();
        } else {
            return Response.ok(buildErrorMessage("EventID = null", false).build()).build();
        }
    }

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/sign-up")
    public Response signUpForEvent(UserEvent userEvent) {

        if (userEvent.getEventID() != null && userEvent.getUserID() != null) {
            UserEvent ev = eventsService.signUpForEvent(userEvent);

            JsonObjectBuilder builder = buildErrorMessage(ev.getMsg(), ev.getStatus());

            if (ev.getCountParticipants() != null) {
                builder.add("countParticipants", ev.getCountParticipants());
            }

            return Response.ok(builder.build()).build();
        } else {
            return Response.ok(buildErrorMessage("EventID = null и UserID = null", false).build()).build();
        }
    }

    // +
    @POST
    @Produces("application/json")
    @Path("/{eventID}/users/{userID}")
    public Response deleteUsersEvent(@PathParam("eventID") Integer eventID, @PathParam("userID") Integer userID) {

        // Отменить запись на мероприятие у пользователя

        if (eventID != null && userID != null) {
            UserEvent userEvent = new UserEvent();
            userEvent.setEventID(eventID);
            userEvent.setUserID(userID);

            UserEvent ev = eventsService.deleteUsersEvent(userEvent);

            return Response.ok(buildErrorMessage(ev.getMsg(), ev.getStatus()).build()).build();

        } else {
            return Response.ok(buildErrorMessage("EventID = null и UserID = null", false).build()).build();
        }
    }
}
