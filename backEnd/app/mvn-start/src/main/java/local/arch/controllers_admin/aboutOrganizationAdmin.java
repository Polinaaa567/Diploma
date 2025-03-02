package local.arch.controllers_admin;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import local.arch.controllers_admin.classes.InfoAboutCenter;

@Path("/admin/organization")
public class aboutOrganizationAdmin {

    private Jsonb jsonb = JsonbBuilder.create();

    @PUT
    @Produces("text/plain")
    public Response changeInfoAboutOrganization(String infoAboutOrgJSON) {
        
        // Принять изменённые данные и изменить в бд

        InfoAboutCenter infoString = jsonb.fromJson(infoAboutOrgJSON, InfoAboutCenter.class);

        Timestamp dateChange = Timestamp.from(Instant.now());

        return Response.ok(infoString.getAddress() + dateChange.toString()).build();
    }
}
