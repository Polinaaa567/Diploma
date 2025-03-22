package local.arch.domain.utils;

import java.util.ArrayList;
import java.util.List;

import local.arch.domain.entities.Event;
import local.arch.infrastructure.storage.model.EEvent;

public class EventStructure {
    public static List<Event> toEventList(List<EEvent> eEvents) {
        List<Event> events = new ArrayList<>();

        for (EEvent eEvent : eEvents) {
            Event event = toEvents(eEvent);
            events.add(event);
        }
        return events;
    }

    public static Event toEvents(EEvent eEvent) {

        Event event = new Event();

        event.setAddressEvent(eEvent.getAddressEvent());


        Integer ageRestrictions = eEvent.getAgeRestrictions();
        if (ageRestrictions != null) {
            event.setAgeRestrictions(ageRestrictions.intValue());
        } else {
            event.setAgeRestrictions(0);
        }

        event.setDateCreation(eEvent.getDateCreation());

        event.setDescriptionEvent(eEvent.getDescriptionEvent());

        event.setEventFormat(eEvent.getEventFormat());

        event.setEventID(eEvent.getEventID());

        event.setEventType(eEvent.getEventType());

        event.setImage(eEvent.getImage());

        event.setLinkDobroRF(eEvent.getLinkDobroRF());

        Integer maxParticipants = eEvent.getMaxNumberParticipants();
        if (maxParticipants != null) {
            event.setMaxNumberParticipants(maxParticipants.intValue());
        } else {
            event.setMaxNumberParticipants(0);
        }
        
        event.setNameEvent(eEvent.getNameEvent());

        event.setNumberPointsEvent(eEvent.getNumberPointsEvent());

       return event;

    }

    public static EEvent toEEvent(Event e) {
        try {
            EEvent eE = new EEvent();
            eE.setAddressEvent(e.getAddressEvent());

            eE.setAgeRestrictions(e.getAgeRestrictions());

            eE.setDateCreation(e.getDateCreation());

            eE.setDescriptionEvent(e.getDescriptionEvent());

            eE.setEventFormat(e.getEventFormat());

            eE.setEventID(e.getEventID());

            eE.setEventType(e.getEventType());

            eE.setImage(e.getImage());

            eE.setLinkDobroRF(e.getLinkDobroRF());

            eE.setMaxNumberParticipants(e.getMaxNumberParticipants());

            eE.setNameEvent(e.getNameEvent());

            eE.setNumberPointsEvent(e.getNumberPointsEvent());

            return eE;
        } catch (Exception error) {
            return null;
        }
    }
}
