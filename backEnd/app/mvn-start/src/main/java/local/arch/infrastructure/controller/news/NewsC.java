package local.arch.infrastructure.controller.news;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.news.INewsService;
import local.arch.domain.entities.News;
import local.arch.infrastructure.builder.news_annotation.BuiltNews;

@Path("/news")
public class NewsC {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    @Inject
    @BuiltNews
    INewsService newsService;

    // +
    @GET
    @Produces("application/json")
    public Response getListAllNews(@QueryParam("newsID") Integer newsID) {
        if (newsID != null) {
            News news = newsService.receiveInfoNews(newsID);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                    .add("headline", news.getHeadline())
                    .add("description", news.getDescription())
                    .add("dateCreation", news.getDateCreation().toString())
                    .add("image", "[]");

            return Response.ok(objectBuilder.build()).build();
        } else {

            // список новостей организации

            List<News> news = newsService.receiveAllNews();

            for (News news2 : news) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                        .add("id", news2.getNewsID())
                        .add("headline", news2.getHeadline())
                        .add("dateCreation", news2.getDateCreation().toString())
                        .add("image", "[]");
                arrayBuilder.add(objectBuilder);
            }

            return Response.ok(arrayBuilder.build()).build();
        }
    }
}
