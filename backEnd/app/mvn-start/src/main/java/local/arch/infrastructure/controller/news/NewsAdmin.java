package local.arch.infrastructure.controller.news;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.config.IFileConfig;
import local.arch.application.interfaces.page.news.INewsService;
import local.arch.domain.entities.page.News;
import local.arch.infrastructure.builder.news_annotation.BuiltNews;
import local.arch.infrastructure.token.ITokenKey;

@Path("/admin/news")
public class NewsAdmin {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    private JsonObjectBuilder buildErrorMessage(String msg, Boolean status) {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("status", status);
    }

    @Inject
    IFileConfig fileConfig;

    @Inject
    ITokenKey tokenKey;

    @Inject
    @BuiltNews
    INewsService newsService;

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addNewsInfo(
            @Valid News newsInfo,
            @HeaderParam("token") String token) {

        try {
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

            if (newsInfo.getImage() != null && !newsInfo.getImage().isBlank()) {
                String imageUrl = fileConfig.saveImageFromBase64(newsInfo.getImage(), "news/1" );
                newsInfo.setImageUrl(imageUrl);
            }

            newsService.addNews(newsInfo);

            return Response.ok(buildErrorMessage("Данные сохранены", true).build())
                    .build();
        } catch (NotFoundException e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // +
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeNewsInfo(
            @Valid News newsInfo,
            @HeaderParam("token") String token) {

        // изменить инфу о новости

        try {
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

            newsService.changeNewsInfo(newsInfo.getNewsID(), newsInfo);

            return Response.ok(buildErrorMessage("Данные сохранены", true).build())
                    .build();
        } catch (NotFoundException e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // ?
    @DELETE
    @Path("/{newsID}")
    public Response deleteNewsAdmin(
            @PathParam("newsID") Integer newsID,
            @HeaderParam("token") String token) {

        // удалить новость 

        try {
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
            
            if (newsID != null) {
                newsService.deleteNews(newsID);

                return Response.ok(buildErrorMessage("Урок удалён", true).build()).build();
            } else {
                return Response.ok(buildErrorMessage("Неверный ID", false).build()).build();
            }
        } catch (NotFoundException e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return Response
                    .ok(buildErrorMessage("Произошла ошибка: " + e, false).build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
