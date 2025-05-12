package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import local.arch.application.interfaces.organization.IStorageOrganization;
import local.arch.domain.entities.FAQData;
import local.arch.domain.entities.InfoCenter;
import local.arch.infrastructure.storage.model.ECenter;
import local.arch.infrastructure.storage.model.Efaq;

public class OrganizationPsqlJPA implements IStorageOrganization {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<FAQData> receiveFAQ() {
        List<Efaq> results = entityManager.createQuery("select p from Efaq p", Efaq.class).getResultList();
        
        List<FAQData> faq = new ArrayList<>();
        for (Efaq res: results) {
            FAQData f = new FAQData();
            f.setFaqID(res.getFaqID());
            f.setAnswer(res.getAnswer());
            f.setQuestion(res.getQuestion());

            faq.add(f);
        }

        return faq;
    }

    @Override
    public InfoCenter receiveInfoAboutOrganization() {

        ECenter result = entityManager.createQuery("Select p from ECenter p", ECenter.class).getSingleResult();
        
        InfoCenter center = new InfoCenter();
        center.setNameCenter(result.getName());
        center.setDescription(result.getDescription());
        center.setAddress(result.getAddress());
        center.setContacts(result.getContacts());
        center.setImageData(result.getImageURL());
        
        return center;
        // @Produces("image/*")

                // File file = new File("C:\\Users\\2003k\\Desktop\\diploma\\backEnd\\app\\mvn-start\\src\\main\\webapp\\uploads\\image.jpg");
    }
}
