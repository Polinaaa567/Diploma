package local.arch.infrastructure.controller.organization;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.organization.IOrganizationService;
import local.arch.domain.entities.InfoCenter;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;

@Path("/admin/organization")
public class OrganizationAdmin {

    StringBuilder organizationJson = new StringBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltOrganization
    IOrganizationService organizationService;

    // -
    @PUT
    @Produces("text/plain")
    @Consumes("application/json")
    public Response changeInfoAboutOrganization(InfoCenter infoAboutOrg) {

        // Принять изменённые данные и изменить в бд

        Timestamp dateChange = Timestamp.from(Instant.now());

        return Response.ok(infoAboutOrg.getAddress() + dateChange.toString()).build();
    }
}
