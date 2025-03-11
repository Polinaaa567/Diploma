package local.arch.infrastructure.controller.events;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/admin/events")
public class EventsAdmin {
    
    @POST
    @Produces("application/json")
    public void addEventsAdmin(String eventDataJSON) {

        // добавить меропряитие 
    }

    @DELETE 
    @Produces("application/json")
    @Path("/{eventID}")
    public void deleteEventsAdmin(@PathParam("eventID") String eventID) {

        // удалить меропряитие 
    }

    @PUT
    @Produces("application/json")
    @Path("/{eventID}")
    public Response changeEventInfoAdmin(@QueryParam("eventID") String eventID, String eventDataJSON) {

        // изменить информацию о мероприятии

        return Response.ok("").build();
    }

    @GET
    @Produces("application/json")
    @Path("/{eventsID}/users")
    public Response getUsersByEvent(@PathParam("eventsID") Integer eventsID) {
        
         // Получить пользователей по мероприятию
        
         return Response.ok("check users by events").build();
    }
}
