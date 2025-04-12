package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import local.arch.application.interfaces.education.IStorageEducation;
import local.arch.domain.entities.Lesson;

public class EducationPsqlJPA implements IStorageEducation {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<Lesson> receiveLessons(Integer userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveLessons'");
    }

    @Override
    public Boolean sendPointsToUser(Integer userID, Integer lessonID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendPointsToUser'");
    }

    @Override
    @Transactional
    public void addLesson(Lesson lesson) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLesson'");
    }

    @Override
    @Transactional
    public void deleteLesson(Integer lessonID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteLesson'");
    }

    @Override
    @Transactional
    public void changeLessonInfo(Integer lessonID, Lesson lesson) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeLessonInfo'");
    }
}
