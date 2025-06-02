package local.arch.application.interfaces.page.education;

import java.util.List;

import local.arch.domain.entities.page.Lesson;

public interface IEducationService {
    public List<Lesson> receiveLessons(Integer userID);

    public Lesson sendPointsToUser(Integer userID, Integer lessonID);

    public void addLesson(Lesson lesson);

    public void deleteLesson(Integer lessonID);

    public void changeLessonInfo(Integer lessonID, Lesson lesson);

    public Lesson receiveLessonInfo(Integer lessonID);

    public List<Lesson> getUsersLessons(Integer userID);
}
