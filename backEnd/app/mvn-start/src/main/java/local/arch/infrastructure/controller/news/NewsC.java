package local.arch.infrastructure.controller.news;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/news")
public class NewsC {
    
    @GET
    @Produces("application/json")
    public Response getListAllNews() {
       
        // список новостей организации

        // ждёт: картинка, название, дата размещения

        return Response.ok("").build();
    }

    @GET
    @Produces("application/json")
    @Path("/{newsID}")
    public Response getNewsInfo(@PathParam("newsID") Integer newsID) {
    
        // получить полную инфу о новости

        return Response.ok("").build();
    }
}
