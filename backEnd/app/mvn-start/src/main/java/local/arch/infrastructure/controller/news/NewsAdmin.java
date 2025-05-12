package local.arch.infrastructure.controller.news;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.news.INewsService;
import local.arch.domain.entities.News;
import local.arch.infrastructure.builder.news_annotation.BuiltNews;

@Path("/admin/news")
public class NewsAdmin {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltNews
    INewsService newsService;

    // +
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addNewsInfo(@Valid News newsInfo) {
        try {
            newsService.addNews(newsInfo);
            return Response.ok().build();
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }

    // +
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeNewsInfo(@Valid News newsInfo) {

        // изменить инфу о новости

        try {
            newsService.changeNewsInfo(newsInfo.getNewsID(), newsInfo);
            return Response.ok().build();

        } catch (ConstraintViolationException e) {
            throw e;
        }
    }

    // ?
    @DELETE
    @Path("/{newsID}")
    public Response deleteNewsAdmin(@PathParam("newsID") Integer newsID) {
        if (newsID != null) {
            try {
                newsService.deleteNews(newsID);
                return Response
                        .ok("{\n\"status\": true \n}")
                        .build();

            } catch (NotFoundException e) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"status\": false, \n\"message\": \"Новость не найдена\" \n}").build();
            } catch (Exception e) {
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\n\"status\": false, \n\"message\": \"Внутренняя ошибка сервера\" \n}").build();
            }
        } else {
            return Response.ok("{\n\"status\": false \n\"message\": \"Неверный ID\" \n}").build();
        }

        // удалить мероприятие
    }
}
