package local.arch.infrastructure.controller.organization;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.organization.IOrganizationService;
import local.arch.domain.entities.MonthData;
import local.arch.domain.entities.YearData;
import local.arch.domain.entities.page.Event;
import local.arch.infrastructure.builder.organization_annotation.BuiltOrganization;
import local.arch.infrastructure.token.ITokenKey;

@Path("/admin/reporting")
public class ReportAdmin {

        private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
                return Json.createObjectBuilder()
                                .add("message", msg)
                                .add("status", status);
        }

        @Inject
        ITokenKey tokenKey;

        @Inject
        @BuiltOrganization
        IOrganizationService organizationService;

        public String formatDate(Calendar date) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                return sdf.format(date.getTime());
        }

        // +
        @GET
        @Produces("application/json")
        public Response getReporting(@HeaderParam("token") String token) {
                // Получить отчётность по месяцам

                if (!tokenKey.isTokenValid(token)) {
                        return Response.status(Response.Status.UNAUTHORIZED)
                                        .entity(buildErrorMessage("Недействительный токен", false).build())
                                        .build();
                }

                if (!tokenKey.isAdmin(token)) {
                        return Response.status(Response.Status.FORBIDDEN)
                                        .entity(buildErrorMessage("Недостаточно прав", false).build())
                                        .build();
                }

                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                JsonArrayBuilder monthsArrayBuilder = Json.createArrayBuilder();
                JsonArrayBuilder eventArrayBuilder = Json.createArrayBuilder();

                List<YearData> yearData = organizationService.receiveReports();

                for (YearData year : yearData) {

                        for (MonthData monthData : year.getMonth()) {
                                for (Event e : monthData.getEvents()) {
                                        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                                        .add("id", e.getEventID())
                                                        .add("name", e.getName())
                                                        .add("description", e.getDescription())
                                                        .add("date", formatDate(e.getDateC()))
                                                        .add("usersNumber", e.getNumberParticipants())
                                                        .add("image", Optional.ofNullable(e.getImageUrl()).orElse(""));

                                        eventArrayBuilder.add(objBuilder);
                                }
                                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                                .add("month", monthData.getMonthName())
                                                .add("events", eventArrayBuilder);

                                monthsArrayBuilder.add(objBuilder);

                        }
                        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                        .add("year", year.getYear())
                                        .add("months", monthsArrayBuilder);

                        arrayBuilder.add(objBuilder);
                }

                return Response.ok(arrayBuilder.build()).build();
        }
}
