package local.arch.infrastructure.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/images")
public class ImageURL {
    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);
    }

    @GET
    @Path("/storage")
    @Produces("application/json")
    public Response getImage(
            @QueryParam("fileName") String fileName) {

        java.nio.file.Path imagePath = Paths.get("storage/" + fileName);

        if (!Files.exists(imagePath) || !Files.isRegularFile(imagePath)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(buildErrorMessage("Изображение не найдено", false).build())
                    .build();
        }

        try {
            String mimeType = Files.probeContentType(imagePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            return Response.ok(Files.readAllBytes(imagePath))
                    .type(mimeType)
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(buildErrorMessage("Ошибка чтения файла", false).build())
                    .build();
        }
    }
}
