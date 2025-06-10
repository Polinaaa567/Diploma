package local.arch.application.service.event_service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import local.arch.application.interfaces.config.IFileConfig;
import local.arch.application.interfaces.config.IUseFileConfig;
import local.arch.application.interfaces.page.event.IEventsService;
import local.arch.application.interfaces.page.event.IStorageEvent;
import local.arch.application.interfaces.page.event.IStorageEventUsing;
import local.arch.domain.Factory;
import local.arch.domain.ITextToImage;
import local.arch.domain.entities.Pagination;
import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.Rating;
import local.arch.domain.entities.page.User;
import local.arch.domain.entities.page.UserEvent;

public class EventsService implements IEventsService, IStorageEventUsing, IUseFileConfig {

    IStorageEvent storageEvent;
    IFileConfig fileConfig;

    @Override
    public List<Event> receiveEvents() {
        return storageEvent.receiveEvents();
    }

    @Override
    public Pagination receivePastEventsUser(Integer userID, Integer page, Integer limit) {
        return storageEvent.receivePastEventsUser(userID, page, limit);
    }

    @Override
    public Pagination receiveFutureEventsUser(Integer userID, Integer page, Integer limit) {
        return storageEvent.receiveFutureEventsUser(userID, page, limit);
    }

    @Override
    public Event receiveEventInfo(UserEvent userEvent) {
        return storageEvent.receiveEventInfo(userEvent);
    }

    @Override
    public UserEvent signUpForEvent(UserEvent userEvent) {
        return storageEvent.signUpForEvent(userEvent);
    }

    @Override
    public UserEvent deleteUsersEvent(UserEvent userEvent) {
        return storageEvent.deleteUsersEvent(userEvent);
    }

    @Override
    public void addEvent(Event data) {
        storageEvent.addEvent(data);
    }

    @Override
    public void deleteEvent(Integer eventID) {
        storageEvent.deleteEvent(eventID);
    }

    @Override
    public void changeEventInfo(Integer eventID, Event data) {
        storageEvent.changeEventInfo(eventID, data);
    }

    @Override
    public List<UserEvent> receiveUsersByEvent(Integer eventID) {
        return storageEvent.receiveUsersByEvent(eventID);
    }

    @Override
    public String saveInfoParticipance(Integer eventID, UserEvent ue) {
        Event event = storageEvent.findEvent(eventID);
        User user = storageEvent.findUser(ue.getUserID());
        List<Rating> res = storageEvent.ratingsGet(eventID, ue.getUserID());
        try {
            BufferedImage template = fileConfig.loadTemplateSertificate();

            String imagePath;
            
            if(ue.getStampParticipate() && res.isEmpty()) {

                ITextToImage modifiedImage = Factory.createTextToImage();
                
                BufferedImage image = modifiedImage.addTextToImage(template, event.getName(), user);
                imagePath = fileConfig.saveModifiedCertificate(image, ue.getUserID());

                user.setCertificate(imagePath);

                ue.setUser(user);
            } else {
                imagePath = null;
                
                user.setCertificate(imagePath);

                ue.setUser(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return storageEvent.saveInfoParticipance(eventID, ue);
    }

    @Override
    public void useStorage(IStorageEvent storageEvent) {
        this.storageEvent = storageEvent;
    }

    @Override
    public Pagination eventsBetwenDate(String dateStart, String dateEnd, Integer page, Integer limit) {
        return storageEvent.eventsBetwenDate(dateStart, dateEnd, page, limit);
    }

    @Override
    public List<String> getTypesEvents() {
        return storageEvent.getTypesEvents();
    }

    @Override
    public Pagination receiveAllEvents(Integer page, Integer limit) {
        return storageEvent.receiveAllEvents(page, limit);
    }

    @Override
    public void useFileConfig(IFileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }
}
