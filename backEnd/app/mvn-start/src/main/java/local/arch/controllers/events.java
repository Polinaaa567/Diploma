package local.arch.controllers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.controllers.classes.usersEvents;

@Path("/events")
public class events {
    
    private Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Produces("application/json")
    public Response getEvents() {
    
        // Получить все мероприятия
        
        // ждёт: дата когда будет, название и краткое описание

        return Response.ok("check all events").build();
    }
    
    @GET
    @Produces("application/json")
    @Path("/users/{userID}")
    public Response getUsersEvents(@PathParam("userID") String userID) {
       
        // Список мероприятий пользователя

        // ждёт: дата когда будет, название и краткое описание

        return Response.ok("check list events for users " + userID).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{eventID}")
    public Response getEventInfo(@PathParam("eventID") String eventID) {
        
        // информация о мероприятии

        return Response.ok("check info about event " + eventID).build();
    }

    @POST
    @Produces("application/json")
    @Path("/sign-up")
    public Response signUpForEvent(String signUpInfoJSON) {
        
        usersEvents userEvent = jsonb.fromJson(signUpInfoJSON, usersEvents.class);
        
        // записаться на мероприятие
        
        return Response.ok(userEvent.getEventID()).build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/{eventID}/users/{userID}")
    public Response deleteUsersEvent(@PathParam("eventID") Integer eventID, @PathParam("userID") Integer userID) {

        return Response.ok("remove").build(); 
        
        // Отменить запись на мероприятие у пользователя
    }
}
