package local.arch.infrastructure.controller.organization;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/admin/faq")
public class FAQAdmin {
    
    // @POST
    // @Produces("application/json")
    // public Response addFAQ(String faqDataJSON) {
       
    //     // добавить вопрос и ответ
        
    //     return Response.ok("").build();
    // }

    // @PUT
    // @Produces("application/json")
    // @Path("/{questionID}")
    // public void updateFAQ(@PathParam("questionID") Integer questionID, String faqDataJSON) {

    //     // изменить вопрос или ответ
    
    // }
    
    @DELETE
    @Produces("application/json")
    @Path("/{questionID}")
    public void deleteFAQ(@PathParam("questionID") Integer questionID) {

        // удалить вопрос и ответ

    }
}
