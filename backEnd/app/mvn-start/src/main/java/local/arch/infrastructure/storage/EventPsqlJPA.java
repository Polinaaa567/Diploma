package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import local.arch.apllication.interfaces.event.IStorageEvent;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.User;
import local.arch.infrastructure.storage.model.EUser;
import local.arch.infrastructure.storage.model.EEvent;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.storage.model.EUserEvent;

@Named
public class EventPsqlJPA implements IStorageEvent {

    LocalDateTime localDateTime = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(localDateTime);

    public String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(date.getTime());
    }

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<Event> receiveEvents() {

        List<Object[]> results = entityManager.createQuery(
                "SELECT p.eventID, p.dateEvent, p.nameEvent, p.descriptionEvent, p.image "
                        + "FROM EEvent p "
                        + "order by p.dateEvent",
                Object[].class).getResultList();

        List<Event> events = new ArrayList<>();
        for (Object[] result : results) {
            Event event = new Event();
            event.setEventID((Integer) result[0]);
            event.setDateEvent(formatDate((Calendar) result[1]));
            event.setNameEvent((String) result[2]);
            event.setDescriptionEvent((String) result[3]);
            event.setImage((byte[]) result[4]);
            events.add(event);
        }

        return events;
    }

    @Override
    public List<Event> receivePastEventsUser(Integer userID) {

        List<Object[]> results = entityManager.createQuery(
                "SELECT e.eventID, e.dateEvent, e.nameEvent, e.descriptionEvent, e.image, eu.stampParticipate, eu.timeParticipate "
                        + "FROM EUserEvent eu JOIN eu.fkUserID u JOIN eu.fkEventID e "
                        + "WHERE u.idUser = :id "
                        + "and e.dateEvent < :timestamp",
                Object[].class)
                .setParameter("id", userID)
                .setParameter("timestamp", timestamp)
                .getResultList();

        List<Event> events = new ArrayList<>();
        for (Object[] result : results) {
            Event event = new Event();
            event.setEventID((Integer) result[0]);
            event.setDateEvent(formatDate((Calendar) result[1]));
            event.setNameEvent((String) result[2]);
            event.setDescriptionEvent((String) result[3]);
            event.setImage((byte[]) result[4]);
            event.setStampParticipate((Boolean) result[5]);
            event.setTimeParticipate((Double) result[6]);

            events.add(event);
        }
        return events;
    }

    @Override
    public List<Event> receiveFutureEventsUser(Integer userID) {
        List<Object[]> results = entityManager.createQuery(
                "SELECT e.eventID, e.dateEvent, e.nameEvent, e.descriptionEvent, e.image "
                        + "FROM EUserEvent eu JOIN eu.fkUserID u JOIN eu.fkEventID e "
                        + "WHERE u.idUser = :id "
                        + "and e.dateEvent > :timestamp",
                Object[].class)
                .setParameter("id", userID)
                .setParameter("timestamp", timestamp)
                .getResultList();

        List<Event> events = new ArrayList<>();
        for (Object[] result : results) {
            Event event = new Event();
            event.setEventID((Integer) result[0]);
            event.setDateEvent(formatDate((Calendar) result[1]));
            event.setNameEvent((String) result[2]);
            event.setDescriptionEvent((String) result[3]);
            event.setImage((byte[]) result[4]);

            events.add(event);
        }
        return events;
    }

    @Override
    public Event receiveEventInfo(UserEvent userEvent) {

        EEvent events = entityManager.find(EEvent.class, userEvent.getEventID());

        List<EUserEvent> ue = entityManager.createQuery(
                "SELECT p "
                        + "FROM EUserEvent p "
                        + "where p.fkEventID = :id",
                EUserEvent.class)
                .setParameter("id", events)
                .getResultList();

        Integer count = ue != null
                ? ue.size()
                : 0;

        Event event = new Event();
        event.setDateEvent(formatDate((Calendar) events.getDateEvent()));
        event.setNameEvent(events.getNameEvent());
        event.setDescriptionEvent(events.getDescriptionEvent());
        event.setImage(event.getImage());

        if (userEvent.getUserID() != null) {
            EUser user = entityManager.find(EUser.class, userEvent.getUserID());

            Boolean existEventUser = entityManager.createQuery(
                    "SELECT p "
                            + "FROM EUserEvent p "
                            + "where p.fkEventID = :idE and p.fkUserID = :idU ",
                    EUserEvent.class)
                    .setParameter("idE", events).setParameter("idU", user).getResultList().isEmpty()
                            ? false
                            : true;

            if (events.getMaxNumberParticipants() != null && events.getMaxNumberParticipants() != 0) {
                if (events.getMaxNumberParticipants() - count <= 0) {
                    event.setStatusParticipate(true);
                } else {
                    event.setStatusParticipate(existEventUser);
                }
            } else {
                event.setStatusParticipate(existEventUser);
            }
        } else {
            event.setStatusParticipate(true);
        }

        event.setAddressEvent(events.getAddressEvent());
        event.setEventFormat(event.getEventFormat());
        event.setMaxNumberParticipants(event.getMaxNumberParticipants());
        event.setEventType(event.getEventType());
        event.setAgeRestrictions(event.getAgeRestrictions());
        event.setNumberPointsEvent(event.getNumberPointsEvent());
        event.setNumberParticipants(count);

        return event;
    }

    @Override
    @Transactional
    public String signUpForEvent(UserEvent userEvent) {
        try {
            EEvent events = entityManager.find(EEvent.class, userEvent.getEventID());
            EUser user = entityManager.find(EUser.class, userEvent.getUserID());

            // if (user.getClothingSize() != null
            // && user.getFirstName() != null
            // && user.getLastName() != null
            // && user.getAgeStamp() != null) {

            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);

            EUserEvent eu = new EUserEvent();
            eu.setFkEventID(events);
            eu.setFkUserID(user);
            eu.setDateCreation(timestamp);

            Integer count = entityManager.createQuery(
                    "SELECT p "
                            + "FROM EUserEvent p "
                            + "where p.fkEventID = :id",
                    EUserEvent.class)
                    .setParameter("id", events)
                    .getResultList().size();

            List<EUserEvent> existingEUserEvent = entityManager.createQuery(
                    "SELECT p "
                            + "FROM EUserEvent p "
                            + "where p.fkEventID = :idE and p.fkUserID = :idU",
                    EUserEvent.class)
                    .setParameter("idE", events)
                    .setParameter("idU", user)
                    .getResultList();

            if (existingEUserEvent.isEmpty()) {
                if (events.getMaxNumberParticipants() != null && events.getMaxNumberParticipants() != 0) {
                    if (events.getMaxNumberParticipants() - count <= 0) {
                        return "{\n\"status\": true, \n\"message\": \"Кол-во мест на мероприятие закончилось\","
                                + " \n\"countParticipants\": " + count
                                + "\n}";
                    } else {

                        entityManager.persist(eu);

                        Integer number = entityManager.createQuery(
                                "SELECT p "
                                        + "FROM EUserEvent p "
                                        + "where p.fkEventID = :id",
                                EUserEvent.class)
                                .setParameter("id", events)
                                .getResultList().size();
                        return "{\n\"status\": true, \n\"message\": \"Успешная регистрация на мероприятие\","
                                + " \n\"countParticipants\": " + number + ",\n\"link\": "
                                + events.getLinkDobroRF()
                                + "\n}";
                    }

                } else {

                    entityManager.persist(eu);

                    Integer number = entityManager.createQuery(
                            "SELECT p "
                                    + "FROM EUserEvent p "
                                    + "where p.fkEventID = :id",
                            EUserEvent.class)
                            .setParameter("id", events)
                            .getResultList().size();
                    return "{\n\"status\": true, \n\"message\": \"Успешная решистрация на мероприятие\","
                            + " \n\"countParticipants\": " + number + ",\n\"link\": "
                            + events.getLinkDobroRF()
                            + "\n}";
                }
            } else {
                return "{\n \"status\": true, \n\"message\": \"Вы уже записаны на мероприятие\"\n}";
            }

            // } else {
            // return "{\n\"status\": false, \n\"message\": \"Не все данные о пользователе
            // заполнены\"" + "\n}";
            // }
        } catch (Exception e) {
            return "{\n \"status\": false, \n\"message\": \"" + e + "\"\n}";
        }
    }

    @Override
    @Transactional
    public String deleteUsersEvent(UserEvent userEvent) {
        try {
            EEvent events = entityManager.find(EEvent.class, userEvent.getEventID());
            EUser user = entityManager.find(EUser.class, userEvent.getUserID());

            List<EUserEvent> query = entityManager.createQuery(
                    "SELECT eu FROM EUserEvent eu " +
                            "WHERE eu.fkEventID = :event AND eu.fkUserID = :user",
                    EUserEvent.class)
                    .setParameter("event", events)
                    .setParameter("user", user)
                    .getResultList();
            if (query.isEmpty()) {
                return "{\n\"status\": false, \n\"message\": \"Произошла ошибка при удалении\"" + "\n}";
                
            } else {
                for (EUserEvent userEventToRemove : query) {
                    entityManager.remove(entityManager.contains(userEventToRemove) ? userEventToRemove : entityManager.merge(userEventToRemove));
                }

                return "{\n\"status\": false,\n\"message\":\"Успешно удалено мероприятие из списка\"\n}";
            }
        } catch (Exception e) {
            return "{\n\"status\": true, \n\"message\": \"Ошибка: " + e + "\"\n}";
        }
    }

    @Override
    public Boolean addEvent(Event data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addEvent'");
    }

    @Override
    public Boolean deleteEvent(Integer eventID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteEvent'");
    }

    @Override
    public Boolean changeEventInfo(Integer eventID, Event data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeEventInfo'");
    }

    @Override
    public List<User> receiveUsersByEvent(Integer eventID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveUsersByEvent'");
    }
}
