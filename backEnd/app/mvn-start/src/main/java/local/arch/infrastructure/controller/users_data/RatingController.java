package local.arch.infrastructure.controller.users_data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.user.IUserService;
import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.Rating;
import local.arch.infrastructure.builder.user_annotation.BuiltUser;
import local.arch.infrastructure.token.ITokenKey;

@Path("/rating")
public class RatingController {

    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);

    }

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    ITokenKey tokenKey;

    @Inject
    @BuiltUser
    IUserService userService;

    // +
    @GET
    @Produces("application/json")
    public Response getAllUsersInRating(
            @QueryParam("userID") Integer userID) {

        // Список пользователей в рейтинге по баллам

        // имя, фамилия, кол-во баллов и уровень

        List<Rating> results = userService.receiveUserRating(userID);

        results.forEach(r -> {
            Double percent = ((double) r.getPoint() / (double) r.getMaxPoint()) * 100;
            Double roundedNumber = Math.round(percent * 100.0) / 100.0;

            arrayBuilder.add(
                    Json.createObjectBuilder()
                            .add("id", r.getInfo().getUserID())
                            .add("lastName", r.getInfo().getLastName())
                            .add("name", r.getInfo().getName())
                            .add("points", r.getPoint())
                            .add("maxPoint", r.getMaxPoint() != null ? r.getMaxPoint() : 0)
                            .add("level", r.getLevel())
                            .add("percent", roundedNumber));
        });

        return Response.ok(arrayBuilder.build()).build();
    }

    // -
    @GET
    @Produces("application/json")
    @Path("/{userID}/achievements")
    public Response getAchievements(
            @PathParam("userID") Integer userID,
            @HeaderParam("token") String token) {

        if (!tokenKey.isTokenValid(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(buildErrorMessage("Недействительный токен", false).build())
                    .build();
        }

        Rating achievements = userService.receiveCertificate(userID);

        Double percent = ((double) achievements.getPoint() / (double) achievements.getMaxPoint()) * 100;
        Double roundedNumber = Math.round(percent * 100.0) / 100.0;
                
        achievements.getCertificates().forEach(arrayBuilder::add);
        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("points", achievements.getPoint())
                .add("level", achievements.getLevel())
                .add("maxPoint", achievements.getMaxPoint() != null ? achievements.getMaxPoint() : 0)
                .add("percent", roundedNumber)
                .add("certificates", arrayBuilder);

        // достижения пользователя

        // грамоты , как то их собрать в пдф

        return Response.ok(objBuilder.build()).build();
    }

    @GET
    @Path("/{userID}/certificates/pdf")
    @Produces("application/pdf")
    public Response generateCertificatesPdf(
            @PathParam("userID") Integer userID // ,
    // @HeaderParam("token") String token
    ) {
        // if (!tokenKey.isTokenValid(token)) {
        // return Response.status(Response.Status.UNAUTHORIZED)
        // .entity(buildErrorMessage("Недействительный токен", false).build())
        // .build();
        // }

        Rating achievements = userService.receiveCertificate(userID);
        try (PDDocument doc = new PDDocument()) {
            for (String imageC : achievements.getCertificates()) {
                java.nio.file.Path imagePath = Paths.get("storage", imageC).normalize();
                if (!Files.exists(imagePath)) {
                    throw new FileNotFoundException("Файл изображения не найден: " + imagePath);
                }

                String mimeType = Files.probeContentType(imagePath);
                if (mimeType == null || !mimeType.startsWith("image/")) {
                    throw new IOException("Неподдерживаемый формат файла: " + imagePath);
                }

                BufferedImage image = ImageIO.read(imagePath.toFile());
                if (image == null) {
                    throw new IOException("Не удалось декодировать изображение: " + imagePath);
                }
                PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
                doc.addPage(page);

                try (PDPageContentStream cont = new PDPageContentStream(doc, page)) {
                    PDImageXObject pdImage = LosslessFactory.createFromImage(doc, image);
                    cont.drawImage(pdImage, 0, 0);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);

            return Response.ok(baos.toByteArray())
                    .header("Content-Disposition", "attachment; filename=certificates.pdf")
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ошибка генерации PDF: " + e.getMessage())
                    .build();
        }
    }
}
