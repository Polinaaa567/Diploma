package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import local.arch.application.interfaces.news.IStorageNews;
import local.arch.domain.entities.News;
import local.arch.infrastructure.storage.model.EImageNews;
import local.arch.infrastructure.storage.model.ENews;

public class NewsPsqlJPA implements IStorageNews {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addNews(News news) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        ENews n = new ENews();
        n.setDateCreation(timestamp);
        n.setDescriptionNews(news.getDescription());
        n.setHeadlineNews(news.getHeadline());

        entityManager.persist(n);
    }

    @Override
    @Transactional
    public void deleteNews(Integer newsID) {
        
        ENews news = entityManager.find(ENews.class, newsID);

        entityManager.remove(news);
      
    }

    @Override
    @Transactional
    public void changeNewsInfo(Integer newsID, News news) {
        ENews n = entityManager.find(ENews.class, newsID);

        n.setHeadlineNews(news.getHeadline());
        n.setDescriptionNews(news.getDescription());
        entityManager.merge(n);
    }

    @Override
    public List<News> receiveAllNews() {
        List<ENews> results = entityManager.createQuery("Select p from ENews p order by p.dateCreation DESC", ENews.class).getResultList(); 

        List<News> news = new ArrayList<>();
        for(ENews res : results) {
            News n = new News();

            // List<EImageNews> imageNews = entityManager.createQuery("select p from EImageNews p where p.news = :news", EImageNews.class).setParameter("news", res).getResultList();
            
            n.setNewsID(res.getNewsID());
            n.setHeadline(res.getHeadlineNews());
            n.setDateCreation(res.getDateCreation());
            // if (imageNews.isEmpty() != true) {
            //     List<Byte[]> images = new ArrayList<>();

            //     for (EImageNews image : imageNews) {
            //         images.add(image.getImage()); 
            //     }

            //     n.setImageData(images);
            // } else {
            //     n.setImageData(null);
            // }
            
            news.add(n);
        }

        return news;
    }

    @Override
    public News receiveInfoNews(Integer newsID) {
        ENews news = entityManager.find(ENews.class, newsID);

        News n = new News();
        n.setHeadline(news.getHeadlineNews());
        n.setDescription(news.getDescriptionNews());
        n.setDateCreation(news.getDateCreation());

        return n;
    }
}
