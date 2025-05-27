package local.arch.infrastructure.controller.organization;

import jakarta.ws.rs.Produces;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import local.arch.application.interfaces.page.organization.IOrganizationService;
import local.arch.domain.entities.page.InfoCenter;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;

@Path("/organization")
public class Organization {

    StringBuilder organizationJson = new StringBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltOrganization
    IOrganizationService organizationService;

    // -
    @GET
    @Produces("application/json")
    public Response parseInfoAboutOrganization() {

        InfoCenter result = organizationService.receiveInfoAboutOrganization();

        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("name", result.getNameCenter() != null
                        ? result.getNameCenter()
                        : "null")
                .add("description", result.getDescription() != null
                        ? result.getDescription()
                        : "null")
                .add("address", result.getAddress() != null
                        ? result.getAddress()
                        : "null")
                .add("contacts", result.getContacts() != null
                        ? result.getContacts()
                        : "null")
                .add("image", result.getImageData() != null
                        ? result.getImageData()
                        : "null");

        return Response.ok(objBuilder.build()).build();
    }
}