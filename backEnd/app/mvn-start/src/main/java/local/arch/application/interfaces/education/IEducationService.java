package local.arch.application.interfaces.education;

import java.util.List;

import local.arch.domain.entities.Lesson;

public interface IEducationService {
    public List<Lesson> receiveLessons(Integer userID);

    public Boolean sendPointsToUser(Integer userID, Integer lessonID);

    public void addLesson(Lesson lesson);

    public void deleteLesson(Integer lessonID);

    public void changeLessonInfo(Integer lessonID, Lesson lesson);

    public Lesson receiveLessonInfo(Integer lessonID);
}
