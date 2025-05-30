package local.arch.infrastructure.controller.organization;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import local.arch.application.interfaces.page.organization.IOrganizationService;
import local.arch.domain.entities.page.FAQData;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;

@Path("/faq")
public class FAQ {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltOrganization
    IOrganizationService organizationService;

    // +
    @GET
    @Produces("application/json")
    public Response getFAQ() {

        List<FAQData> faq = organizationService.receiveFAQ();

        for (FAQData f: faq) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
            .add("id", f.getFaqID())
            .add("question", f.getQuestion())
            .add("answer", f.getAnswer());

            arrayBuilder.add(objBuilder);

        }

        return Response.ok(arrayBuilder.build()).build();
    }
}
