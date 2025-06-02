package local.arch.application.service.education_service;

import java.util.List;

import local.arch.application.interfaces.page.education.IEducationService;
import local.arch.application.interfaces.page.education.IStorageEducation;
import local.arch.application.interfaces.page.education.IStorageEducationUsing;
import local.arch.domain.entities.page.Lesson;

public class EducationService implements IEducationService, IStorageEducationUsing {

    IStorageEducation storageEducation;

    @Override
    public void useStorage(IStorageEducation storageEducation) {
        this.storageEducation = storageEducation;
    }

    @Override
    public List<Lesson> receiveLessons(Integer userID) {
        return storageEducation.receiveLessons(userID);
    }

    @Override
    public Lesson sendPointsToUser(Integer userID, Integer lessonID) {
        return storageEducation.sendPointsToUser(userID, lessonID);
    }

    @Override
    public void addLesson(Lesson lesson) {
        storageEducation.addLesson(lesson);
    }

    @Override
    public void deleteLesson(Integer lessonID) {
        storageEducation.deleteLesson(lessonID);
    }

    @Override
    public void changeLessonInfo(Integer lessonID, Lesson lesson) {
        storageEducation.changeLessonInfo(lessonID, lesson);
    }

    @Override
    public Lesson receiveLessonInfo(Integer lessonID) {
       return storageEducation.receiveLessonInfo(lessonID);
    }

    @Override
    public List<Lesson> getUsersLessons(Integer userID) {
        return storageEducation.getUsersLessons(userID);
    }
}
