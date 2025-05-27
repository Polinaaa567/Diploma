package local.arch.application.service.news_service;

import java.util.List;

import local.arch.application.interfaces.page.news.INewsService;
import local.arch.application.interfaces.page.news.IStorageNews;
import local.arch.application.interfaces.page.news.IStorageNewsUsing;
import local.arch.domain.entities.page.News;

public class NewsService implements INewsService, IStorageNewsUsing {

    IStorageNews storageNews;

    @Override
    public void useStorage(IStorageNews storageNews) {
        this.storageNews = storageNews;
    }

    @Override
    public void addNews(News news) {
        storageNews.addNews(news);
    }

    @Override
    public void deleteNews(Integer newsID) {
       storageNews.deleteNews(newsID);
    }

    @Override
    public void changeNewsInfo(Integer newsID, News news) {
        storageNews.changeNewsInfo(newsID, news);
    }

    @Override
    public List<News> receiveAllNews() {
        return storageNews.receiveAllNews();
    }

    @Override
    public News receiveInfoNews(Integer newsID) {
        return storageNews.receiveInfoNews(newsID);
    }
}
