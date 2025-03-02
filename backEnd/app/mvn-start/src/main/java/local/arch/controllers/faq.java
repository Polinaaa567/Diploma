package local.arch.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/faq")
public class faq {
    
    @GET
    @Produces("application/json")
    public Response getFAQ() {
       
        // получить список часто задаваемых вопросов и ответов
       
        return Response.ok("").build();
    }
}
