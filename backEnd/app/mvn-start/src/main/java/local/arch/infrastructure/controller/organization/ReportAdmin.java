package local.arch.infrastructure.controller.organization;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.organization.IOrganizationService;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;

@Path("/admin/reporting")
public class ReportAdmin {

    // JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltOrganization
    IOrganizationService organizationService;

    // -
    @GET
    @Produces("application/json")
    public Response getReporting() {

        // Получить отчётность по месяцам

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        JsonArrayBuilder monthsArrayBuilder = Json.createArrayBuilder();

        JsonObjectBuilder month1 = Json.createObjectBuilder()
                .add("month", "January")
                .add("events", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "крид захотел ...")
                                .add("date", "10-01-2025")
                                .add("description", "cmdkmdks")
                                .add("image", "[]")
                                .add("usersNumber", 4)))
                .add("top5", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("lastName", "slps")
                                .add("name", "monthsArrayBuilder")
                                .add("points", 200)
                                .add("level", 3)));

        JsonObjectBuilder month2 = Json.createObjectBuilder()
                .add("month", "February")
                .add("events", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "лазарева и билана увидели вместе в ...")
                                .add("date", "25-02-2025")
                                .add("description", "cmdkmdks")
                                .add("image", "[]")
                                .add("usersNumber", 2)))
                .add("top5", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("lastName", "slps")
                                .add("name", "monthsArrayBuilder")
                                .add("points", 200)
                                .add("level", 3)));
        

        monthsArrayBuilder.add(month1);
        monthsArrayBuilder.add(month2);

        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("year", 2025)
                .add("months", monthsArrayBuilder);

        arrayBuilder.add(objBuilder);

        return Response.ok(arrayBuilder.build()).build();
    }
}
