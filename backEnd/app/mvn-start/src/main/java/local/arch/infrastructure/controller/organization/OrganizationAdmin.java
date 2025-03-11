package local.arch.infrastructure.controller.organization;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import local.arch.domain.entities.InfoCenter;

@Path("/admin/organization")
public class OrganizationAdmin {

    private Jsonb jsonb = JsonbBuilder.create();

    @PUT
    @Produces("text/plain")
    public Response changeInfoAboutOrganization(String infoAboutOrgJSON) {
        
        // Принять изменённые данные и изменить в бд

        InfoCenter infoString = jsonb.fromJson(infoAboutOrgJSON, InfoCenter.class);

        Timestamp dateChange = Timestamp.from(Instant.now());

        return Response.ok(infoString.getAddress() + dateChange.toString()).build();
    }
}
