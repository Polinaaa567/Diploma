package local.arch.infrastructure.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import local.arch.infrastructure.storage.model.ELessons;
import local.arch.infrastructure.storage.model.EPoints;
import local.arch.infrastructure.storage.model.ERole;
import local.arch.infrastructure.storage.model.EUser;
import local.arch.infrastructure.storage.model.Efaq;
import local.arch.domain.entities.page.FAQData;
import local.arch.domain.entities.page.Lesson;

class EducationPsqlIntegrationTest {
    private final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    private static EntityManagerFactory emf;
    private EntityManager em;
    private OrganizationPsqlJPA educationStorage;

    @BeforeAll
    static void setup() {
        emf = Persistence.createEntityManagerFactory("TestVolunteering");
    }

    @BeforeEach
    void init() {
        em = emf.createEntityManager();
        educationStorage = new OrganizationPsqlJPA();
        try {
            var field = OrganizationPsqlJPA.class.getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(educationStorage, em);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        persistTestData();
    }

    private void persistTestData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Efaq fEfaq = new Efaq();
        fEfaq.setAnswer("null");
        fEfaq.setQuestion("null");
        em.persist(fEfaq);
        em.flush();

        Efaq fEfaq2 = new Efaq();
        fEfaq2.setAnswer("null2");
        fEfaq2.setQuestion("null2");
        em.persist(fEfaq2);
        em.flush();

        // ERole role = new ERole();
        // role.setIdRole(1);
        // role.setNameRoles("Администратор");
        // em.persist(role);

        // EUser user = new EUser();
        // user.setIdUser(1);
        // user.setLogin("null");
        // user.setPassword("null");
        // user.setFkRoleID(role);
        // em.persist(user);

        // // Создание уроков
        // ELessons lesson1 = new ELessons();
        // lesson1.setLessonID(1);
        // lesson1.setHeadline("desc1");
        // lesson1.setLink("link1");
        // lesson1.setNumberPoints(10);
        // lesson1.setDateCreation(timestamp);
        // em.persist(lesson1);

        // ELessons lesson2 = new ELessons();
        // lesson2.setLessonID(2);
        // lesson2.setHeadline("desc2");
        // lesson2.setLink("link2");
        // lesson2.setNumberPoints(20);
        // lesson2.setDateCreation(timestamp);
        // em.persist(lesson2);

        // EPoints points = new EPoints();
        // points.setFkUserID(user);
        // points.setFkLessonID(lesson1);
        // points.setPoints(lesson1.getNumberPoints());
        // points.setDateChange(Timestamp.valueOf(LocalDateTime.now()));
        // em.persist(points);

        tx.commit();
    }

    @Test
    void receiveLessons_WithValidUserId_ReturnsLessonsWithStatus() {
        em.getTransaction().begin();
        List<FAQData> faqList = educationStorage.receiveFAQ();
        em.getTransaction().rollback();

        assertEquals(2, faqList.size());
        FAQData faq1 = faqList.get(0);
        assertEquals(1, faq1.getFaqID());
        assertEquals("null", faq1.getAnswer());

        // // Урок 2 (последний созданный)
        // Lesson lesson2 = lessons.get(0);
        // assertEquals(2, lesson2.getLessonID());
        // assertEquals("Headline2", lesson2.getHeadline());
        // assertFalse(lesson2.getStatus()); // Баллы не начислены

        // // Урок 1
        // Lesson lesson1 = lessons.get(1);
        // assertEquals(1, lesson1.getLessonID());
        // assertTrue(lesson1.getStatus()); // Баллы начислены
    }
}
