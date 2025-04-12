package local.arch.infrastructure.controller.organization;

import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.organization.IOrganizationService;
import local.arch.domain.entities.InfoCenter;
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
	    
        InfoCenter infoCenter = organizationService.receiveInfoAboutOrganization(); 
		
        return Response.ok(infoCenter).build();
	}
}