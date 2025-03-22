package local.arch.infrastructure.controller.news;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/admin/news")
public class NewsAdmin {

    // @POST
    // @Produces("application/json")
    // public void addNewsInfo(String newsInfoJSON) {

    //     // добавить новость

    // }

    // @PUT
    // @Produces("application/json")
    // @Path("/{newsID}")
    // public void changeNewsInfo(@PathParam("newsID") Integer newsID, String newsInfoJSON) {

    //     // полная инфа о новости

    // }

    @DELETE
    @Produces("application/json")
    @Path("/{newsID}")
    public  Response deleteNews(@PathParam("newsID") Integer newsID) {

        // удалить новость

        return Response.ok("").build();

    }
}
