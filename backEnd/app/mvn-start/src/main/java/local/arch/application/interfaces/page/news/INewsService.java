package local.arch.application.interfaces.page.news;

import java.util.List;

import local.arch.domain.entities.page.News;

public interface INewsService {
    public void addNews(News news);

    public void deleteNews(Integer newsID);

    public void changeNewsInfo(Integer newsID, News news);

    public List<News> receiveAllNews();

    public News receiveInfoNews(Integer newsID);
}
