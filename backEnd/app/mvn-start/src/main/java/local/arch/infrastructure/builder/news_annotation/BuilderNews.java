package local.arch.infrastructure.builder.news_annotation;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import local.arch.application.interfaces.news.INewsService;
import local.arch.application.interfaces.news.IStorageNews;
import local.arch.application.interfaces.news.IStorageNewsUsing;

public class BuilderNews {
    @Inject
    private INewsService newsService;

    @Inject 
    private IStorageNews storageNews;

    @BuiltNews
    @Produces
    public INewsService buildNewsService() {
        ((IStorageNewsUsing) newsService).useStorage(storageNews);
        return newsService;
    }
}
