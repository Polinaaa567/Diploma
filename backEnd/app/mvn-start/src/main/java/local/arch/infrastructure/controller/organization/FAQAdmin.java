package local.arch.infrastructure.controller.organization;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import local.arch.application.interfaces.page.organization.IOrganizationService;
import local.arch.domain.entities.page.FAQData;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;

@Path("/admin/faq")
public class FAQAdmin {

    StringBuilder faqJson = new StringBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltOrganization
    IOrganizationService organizationService;

    // -
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public void addFAQ(FAQData faq) {

    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{questionID}")
    public void updateFAQ(@PathParam("questionID") Integer questionID, FAQData faq) {

        // изменить вопрос или ответ

    }

    @DELETE
    @Produces("application/json")
    @Path("/{questionID}")
    public void deleteFAQ(@PathParam("questionID") Integer questionID) {

        // удалить вопрос и ответ

    }
}
