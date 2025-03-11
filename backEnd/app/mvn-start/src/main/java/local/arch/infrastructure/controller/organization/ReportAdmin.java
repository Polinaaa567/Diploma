package local.arch.infrastructure.controller.organization;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/admin/reporting")
public class ReportAdmin {

    @GET
    @Produces("application/json")
    public Response getReporting() {

        // Получить отчётность по месяцам

        return Response.ok("get reporting").build();
    }
}
