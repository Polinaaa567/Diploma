package local.arch.infrastructure.controller.news;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import local.arch.application.interfaces.news.INewsService;
import local.arch.domain.entities.News;
import local.arch.infrastructure.builder.news_annotation.BuiltNews;

@Path("/admin/news")
public class NewsAdmin {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltNews
    INewsService newsService;

    // -
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public void addNewsInfo(News newsInfo) {

        // добавить новость

        newsService.addNews(newsInfo);
    }

    // -
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public void changeNewsInfo( News newsInfo) {

        // изменить инфу о новости

        newsService.changeNewsInfo(newsInfo.getNewsID(), newsInfo);
    }

    // -
    @POST
    @Path("/delete")
    public void deleteNews(@QueryParam("newsID") Integer newsID) {

        // удалить новость

        newsService.deleteNews(newsID);
    }
}
