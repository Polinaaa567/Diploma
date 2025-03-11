package local.arch.infrastructure.controller.education;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import local.arch.domain.entities.Lesson;

@Path("/admin/education")
public class EducationAdmin {

    private Jsonb jsonb = JsonbBuilder.create();

    @POST
	@Produces("text/plain")
    public Response addLessons(String lessonDataJSON) {
    
        // добавить урок

        Lesson lessonData = jsonb.fromJson(lessonDataJSON, Lesson.class);
        
        return Response.ok(lessonData.getHeadline()).build();
    }

    @PUT
    @Produces("application/json")
    public Response changeLesson(@QueryParam("lessonID") String lessonID, String lessonDataJSON) {
        
        // null убрать, обновить часть
        
        // изменить урок
       
        Lesson lessonData = jsonb.fromJson(lessonDataJSON.toString(), Lesson.class);

        return Response.ok(lessonData.getHeadline()).build();
    }

    @DELETE
	@Produces("application/json")
    @Path("/{lessonID}") 
    public Response removeLesson(@PathParam("lessonID") String lessonID) {

        // удалить урок

        return Response.ok(lessonID).build();
    }
}
