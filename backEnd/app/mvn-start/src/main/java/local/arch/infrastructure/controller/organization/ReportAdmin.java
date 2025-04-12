package local.arch.infrastructure.controller.organization;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.organization.IOrganizationService;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;

@Path("/admin/reporting")
public class ReportAdmin {

    StringBuilder reportJson = new StringBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltOrganization
    IOrganizationService organizationService;

    // -
    @GET
    @Produces("application/json")
    public Response getReporting() {

        // Получить отчётность по месяцам

        return Response.ok("get reporting").build();
    }
}
