package local.arch.infrastructure.controller.organization;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.organization.IOrganizationService;
import local.arch.domain.entities.FAQData;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;

@Path("/faq")
public class FAQ {

    StringBuilder faqJson = new StringBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltOrganization
    IOrganizationService organizationService;

    // -
    @GET
    @Produces("application/json")
    public Response getFAQ() {

        List<FAQData> faq = organizationService.receiveFAQ();

        return Response.ok(faq).build();
    }
}
