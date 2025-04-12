// package local.arch.domain.utils;

// import java.util.ArrayList;
// import java.util.List;

// import local.arch.domain.entities.Event;
// import local.arch.infrastructure.storage.model.EEvent;

// public class EventStructure {
//     public static List<Event> toEventList(List<EEvent> eEvents) {
//         List<Event> events = new ArrayList<>();

//         for (EEvent eEvent : eEvents) {
//             Event event = toEvents(eEvent);
//             events.add(event);
//         }
//         return events;
//     }

//     public static Event toEvents(EEvent eEvent) {

//         Event event = new Event();

//         event.setAddress(eEvent.getAddressEvent());


//         Integer ageRestrictions = eEvent.getAgeRestrictions();
//         if (ageRestrictions != null) {
//             event.setAge(ageRestrictions);
//         } else {
//             event.setAge(0);
//         }

//         event.setDateCreation(eEvent.getDateCreation());

//         event.setDescription(eEvent.getDescriptionEvent());

//         event.setFormat(eEvent.getEventFormat());

//         event.setEventID(eEvent.getEventID());

//         event.setType(eEvent.getEventType());

//         event.setImage(eEvent.getImage());

//         event.setLinkDobroRF(eEvent.getLinkDobroRF());

//         Integer maxParticipants = eEvent.getMaxNumberParticipants();
//         if (maxParticipants != null) {
//             event.setMaxCountParticipants(maxParticipants.intValue());
//         } else {
//             event.setMaxCountParticipants(0);
//         }
        
//         event.setName(eEvent.getNameEvent());

//         event.setPoints(eEvent.getNumberPointsEvent());

//        return event;

//     }

//     public static EEvent toEEvent(Event e) {
//         try {
//             EEvent eE = new EEvent();
            
//             eE.setAddressEvent(e.getAddress());

//             eE.setAgeRestrictions(e.getAge());

//             eE.setDateCreation(e.getDateCreation());

//             eE.setDescriptionEvent(e.getDescription());

//             eE.setEventFormat(e.getDate());

//             eE.setEventID(e.getEventID());

//             eE.setEventType(e.getType());

//             eE.setImage(e.getImage());

//             eE.setLinkDobroRF(e.getLinkDobroRF());

//             eE.setMaxNumberParticipants(e.getMaxCountParticipants());

//             eE.setNameEvent(e.getName());

//             eE.setNumberPointsEvent(e.getPoints());

//             return eE;
//         } catch (Exception error) {
//             return null;
//         }
//     }
// }
