package local.arch.infrastructure.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import local.arch.application.interfaces.config.IFileConfig;
import local.arch.application.interfaces.page.news.IStorageNews;
import local.arch.domain.entities.page.News;
import local.arch.infrastructure.storage.model.EImageNews;
import local.arch.infrastructure.storage.model.ENews;

public class NewsPsqlJPA implements IStorageNews {

    Calendar calendar = Calendar.getInstance();

    @Inject
    IFileConfig fileConfig;

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addNews(News news) {
        ENews n = new ENews();
        n.setDateCreation(calendar);
        n.setDescriptionNews(news.getDescription());
        n.setHeadlineNews(news.getHeadline());
        entityManager.persist(n);

        if (news.getImageUrl() != null && !news.getImageUrl().isBlank()) {
            EImageNews newsImage = new EImageNews();
            newsImage.setImage(news.getImageUrl());
            newsImage.setNews(n);
            entityManager.persist(newsImage);
        }
    }

    @Override
    @Transactional
    public void deleteNews(Integer newsID) {
        ENews news = entityManager.find(ENews.class, newsID);
        if (news == null)
            return;

        List<EImageNews> imageNews = null;

        try {
            imageNews = entityManager.createQuery("Select p from EImageNews p where p.news = :news", EImageNews.class)
                    .setParameter("news", news).getResultList();
        } catch (NoResultException e) {
        }

        if (imageNews.isEmpty()) {
            for (EImageNews image : imageNews) {
                try {
                    fileConfig.deleteImage(image.getImage());
                } catch (IOException e) {
                    Logger.getLogger(getClass().getName())
                            .log(Level.SEVERE, "Ошибка удаления файла: " + image.getImage(), e);
                    throw new RuntimeException("Ошибка удаления файла", e);
                }

                entityManager.remove(image);
            }
        }

        entityManager.remove(news);
    }

    @Override
    @Transactional
    public void changeNewsInfo(Integer newsID, News news) {
        ENews n = entityManager.find(ENews.class, newsID);

        List<EImageNews> imageNews = entityManager
                .createQuery("Select p from EImageNews p where p.fkEventID = :news", EImageNews.class)
                .setParameter("news", n).getResultList();

        if (news.getImage() != null && !news.getImage().isBlank() && !news.getImage().contains("news")) {
            for (EImageNews image : imageNews) {
                try {
                    String imageUrl = fileConfig.saveImageFromBase64(news.getImage(), "news/1");
                    fileConfig.deleteImage(image.getImage());

                    image.setImage(imageUrl);

                    entityManager.persist(image);
                } catch (IOException e) {
                    Logger.getLogger(getClass().getName())
                            .log(Level.WARNING, "Ошибка удаления файла: " + image.getImage(), e);
                }
            }
        }

        n.setHeadlineNews(news.getHeadline());
        n.setDescriptionNews(news.getDescription());
        entityManager.merge(n);
    }

    @Override
    public List<News> receiveAllNews() {
        List<ENews> results = entityManager
                .createQuery("Select p from ENews p order by p.dateCreation DESC", ENews.class).getResultList();

        List<News> news = new ArrayList<>();
        for (ENews res : results) {
            News n = new News();

            List<EImageNews> imageNews = entityManager
                    .createQuery("SELECT p FROM EImageNews p WHERE p.news = :news", EImageNews.class)
                    .setParameter("news", res)
                    .getResultList();

            n.setNewsID(res.getNewsID());
            n.setHeadline(res.getHeadlineNews());
            n.setDateCreation(res.getDateCreation());

            if (imageNews.isEmpty()) {
                n.setImageUrl(null);
            } else {
                n.setImageUrl(imageNews.get(0).getImage());
            }

            news.add(n);
        }

        return news;
    }

    @Override
    public News receiveInfoNews(Integer newsID) {
        ENews news = entityManager.find(ENews.class, newsID);
        List<EImageNews> imageNews = entityManager
                .createQuery("SELECT p FROM EImageNews p WHERE p.news = :news", EImageNews.class)
                .setParameter("news", news)
                .getResultList();

        News n = new News();
        n.setHeadline(news.getHeadlineNews());
        n.setDescription(news.getDescriptionNews());
        n.setDateCreation(news.getDateCreation());

        if (imageNews.isEmpty()) {
            n.setImageUrl(null);
        } else {
            n.setImageUrl(imageNews.get(0).getImage());

        }
        return n;
    }
}
