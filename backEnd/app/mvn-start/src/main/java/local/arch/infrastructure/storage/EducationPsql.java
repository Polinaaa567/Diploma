package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import local.arch.application.interfaces.page.education.IStorageEducation;
import local.arch.domain.entities.page.Lesson;
import local.arch.infrastructure.storage.model.ELessons;
import local.arch.infrastructure.storage.model.EPoints;
import local.arch.infrastructure.storage.model.EUser;

public class EducationPsql implements IStorageEducation {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<Lesson> receiveLessons(Integer userID) {
        if (userID != null) {
            EUser u = entityManager.find(EUser.class, userID);
            List<Object[]> results = entityManager.createQuery(
                    "Select l.lessonID, l.headline, l.link, l.description, l.numberPoints, " +
                            "case when p.idUP is not null then true else false end as status " +
                            "From ELessons l " +
                            "left OUTER join EPoints p on l = p.fkLessonID and p.fkUserID = :user " +
                            "order by l.dateCreation DESC",
                    Object[].class)
                    .setParameter("user", u)
                    .getResultList();

            List<Lesson> lessonsList = new ArrayList<>();
            for (Object[] result : results) {
                Lesson lesson = new Lesson();

                lesson.setLessonID((Integer) result[0]);
                lesson.setHeadline((String) result[1]);
                lesson.setLink((String) result[2]);
                lesson.setDescription((String) result[3]);
                lesson.setNumberPoints((Integer) result[4]);
                lesson.setStatus((Boolean) result[5]);

                lessonsList.add(lesson);
            }

            return lessonsList;
        } else {
            List<Object[]> results = entityManager.createQuery(
                    "Select l.lessonID, l.headline, l.link, l.description " +
                            "From ELessons l " +
                            "order by l.dateCreation DESC",
                    Object[].class)
                    .getResultList();

            List<Lesson> lessonsList = new ArrayList<>();
            for (Object[] result : results) {
                Lesson lesson = new Lesson();

                lesson.setLessonID((Integer) result[0]);
                lesson.setHeadline((String) result[1]);
                lesson.setLink((String) result[2]);
                lesson.setDescription((String) result[3]);

                lessonsList.add(lesson);
            }

            return lessonsList;
        }
    }

    @Override
    @Transactional
    public Lesson sendPointsToUser(Integer userID, Integer lessonID) {
        Lesson l = new Lesson();

        try {
            ELessons lesson = entityManager.find(ELessons.class, lessonID);

            if (lesson == null) {
                l.setMessage("Урок не найден");
                l.setStatus(false);

                return l;
            }

            EUser user = entityManager.find(EUser.class, userID);

            if (user == null) {
                l.setMessage("Пользователь не найден");
                l.setStatus(false);

                return l;
            }

            EPoints points = new EPoints();

            points.setDateChange(timestamp);
            points.setFkEventID(null);
            points.setFkLessonID(lesson);
            points.setPoints(lesson.getNumberPoints());
            points.setFkUserID(user);

            entityManager.persist(points);

            l.setMessage("Данные сохранены");
            l.setStatus(true);

            return l;

        } catch (Exception e) {
            l.setMessage("Ошибка при начислении баллов: " + e);
            l.setStatus(false);

            return l;
        }
    }

    @Override
    @Transactional
    public void addLesson(Lesson lesson) {
        ELessons l = new ELessons();

        l.setDateCreation(timestamp);
        l.setHeadline(lesson.getHeadline());
        l.setLink(lesson.getLink());
        l.setDescription(lesson.getDescription());
        l.setNumberPoints(lesson.getNumberPoints());

        entityManager.persist(l);
    }

    @Override
    @Transactional
    public void deleteLesson(Integer lessonID) {

        ELessons lessons = entityManager.find(ELessons.class, lessonID);

        entityManager.remove(lessons);

    }

    @Override
    @Transactional
    public void changeLessonInfo(Integer lessonID, Lesson lesson) {

        ELessons l = entityManager.find(ELessons.class, lessonID);

        l.setHeadline(lesson.getHeadline());
        l.setLink(lesson.getLink());
        l.setNumberPoints(lesson.getNumberPoints());
        l.setDescription(lesson.getDescription());

        entityManager.merge(l);
    }

    @Override
    public Lesson receiveLessonInfo(Integer lessonID) {
        ELessons l = entityManager.find(ELessons.class, lessonID);

        Lesson lesson = new Lesson();
        lesson.setHeadline(l.getHeadline());
        lesson.setDescription(l.getDescription());
        lesson.setLink(l.getLink());
        lesson.setNumberPoints(l.getNumberPoints());

        return lesson;
    }

    @Override
    public List<Lesson> getUsersLessons(Integer userID) {
        // EUser user = entityManager.find(EUser.class, userID);

        // List<Object[]> listLessonsUsers = entityManager.createQuery(
        //         "Select l.lessonID, l.headline, l.link, l.description, l.numberPoints from EPoints p left join ELessons l on p.fkLessonID = l where p.fkLessonID is not null and p.fkUserID = :user",
        //         Object[].class).setParameter("user", user).getResultList();

        // List<Lesson> lessonsList = new ArrayList<>();
        // for(Object[] lesson : listLessonsUsers) {
        //     Lesson l = new Lesson();
        //     l.setLessonID((Integer) lesson[0]);
        //     l.setHeadline((String) lesson[1]);
        //     l.set((String) lesson[1]);
        //     l.setDescription((String) lesson[3]);
        // }
        return null;
    }
}
