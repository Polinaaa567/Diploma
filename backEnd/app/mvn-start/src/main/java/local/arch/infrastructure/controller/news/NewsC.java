package local.arch.infrastructure.controller.news;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import local.arch.application.interfaces.page.news.INewsService;
import local.arch.domain.entities.page.News;
import local.arch.infrastructure.builder.news_annotation.BuiltNews;

@Path("/news")
public class NewsC {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    private String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(date.getTime());
    }

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
                    .add("dateCreation", formatDate(news.getDateCreation()))
                    .add("image", Optional.ofNullable(news.getImageUrl()).orElse(""));

            return Response.ok(objectBuilder.build()).build();
        } else {

            // список новостей организации

            List<News> news = newsService.receiveAllNews();

            news.forEach(news2 -> {
                arrayBuilder.add(
                        Json.createObjectBuilder()
                                .add("id", news2.getNewsID())
                                .add("headline", news2.getHeadline())
                                .add("dateCreation", formatDate(news2.getDateCreation()))
                                .add("image", Optional.ofNullable(news2.getImageUrl()).orElse("")));
            });

            return Response.ok(arrayBuilder.build()).build();
        }
    }
}
