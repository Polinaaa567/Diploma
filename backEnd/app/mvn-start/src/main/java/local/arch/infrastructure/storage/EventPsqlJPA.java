package local.arch.infrastructure.storage;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import local.arch.application.interfaces.event.IStorageEvent;
import local.arch.domain.entities.Event;
import local.arch.domain.entities.User;
import local.arch.infrastructure.storage.model.EUser;
import local.arch.infrastructure.storage.model.EEvent;
import local.arch.infrastructure.storage.model.EPoints;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.storage.model.EUserEvent;

@Named
public class EventPsqlJPA implements IStorageEvent {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    Calendar calendar = Calendar.getInstance();
    

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<Event> receiveEvents() {

        List<Object[]> results = entityManager.createQuery(
                "SELECT p.eventID, p.dateEvent, p.nameEvent, p.descriptionEvent, p.image, p.isParticipation "
                        + "FROM EEvent p "
                        + "order by p.dateEvent DESC",
                Object[].class).getResultList();

        List<Event> events = new ArrayList<>();
        for (Object[] result : results) {
            Event event = new Event();
            event.setEventID((Integer) result[0]);
            event.setDateC((Calendar) result[1]);
            event.setName((String) result[2]);
            event.setDescription((String) result[3]);
            event.setImage((Byte[]) result[4]);
            event.setIsRelevance(((Calendar) result[1]).compareTo(calendar) > 0
                    ? true
                    : false);
            event.setIsParticipation((Boolean) result[5]);

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
            event.setDateC((Calendar) result[1]);
            event.setName((String) result[2]);
            event.setDescription((String) result[3]);
            event.setImage((Byte[]) result[4]);
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
            event.setDateC(((Calendar) result[1]));
            event.setName((String) result[2]);
            event.setDescription((String) result[3]);
            event.setImage((Byte[]) result[4]);

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
        event.setDateC((Calendar) events.getDateEvent());
        event.setName(events.getNameEvent());
        event.setDescription(events.getDescriptionEvent());
        event.setImage(events.getImage());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(timestamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));

        if (userEvent.getUserID() != null && events.getDateEvent().compareTo(calendar) > 0) {
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

        event.setAddress(events.getAddressEvent());
        event.setFormat(events.getEventFormat());
        event.setMaxCountParticipants(events.getMaxNumberParticipants());
        event.setType(events.getEventType());
        event.setAge(events.getAgeRestrictions());
        event.setPoints(events.getNumberPointsEvent());
        event.setNumberParticipants(count);
        event.setLinkDobroRF(events.getLinkDobroRF());

        return event;
    }

    @Override
    @Transactional
    public String signUpForEvent(UserEvent userEvent) {
        try {
            EEvent events = entityManager.find(EEvent.class, userEvent.getEventID());
            EUser user = entityManager.find(EUser.class, userEvent.getUserID());

            if (user.getClothingSize() != null
            && user.getFirstName() != null
            && user.getLastName() != null
            && user.getAgeStamp() != null) {

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
                                + " \n\"countParticipants\": " + number
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
                            + " \n\"countParticipants\": " + number
                            + "\n}";
                }
            } else {
                return "{\n \"status\": true, \n\"message\": \"Вы уже записаны на мероприятие\"\n}";
            }

            } else {
            return "{\n\"status\": false, \n\"message\": \"Не все данные о пользователе заполнены\"" + "\n}";
            }
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
                    entityManager.remove(entityManager.contains(userEventToRemove) ? userEventToRemove
                            : entityManager.merge(userEventToRemove));
                }

                return "{\n\"status\": false,\n\"message\":\"Успешно удалено мероприятие из списка\"\n}";
            }
        } catch (Exception e) {
            return "{\n\"status\": true, \n\"message\": \"Ошибка: " + e + "\"\n}";
        }
    }

    @Override
    @Transactional
    public void addEvent(Event data) {
        EEvent ev = new EEvent();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(data.getDate(), formatter);
        calendar.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        
        ev.setAddressEvent(data.getAddress());
        ev.setAgeRestrictions(data.getAge());
        ev.setDateCreation(timestamp);
        ev.setDateEvent(calendar);
        ev.setDescriptionEvent(data.getDescription());
        ev.setEventFormat(data.getFormat());
        ev.setEventType(data.getType());
        ev.setImage(data.getImage());
        ev.setLinkDobroRF(data.getLinkDobroRF());
        ev.setMaxNumberParticipants(data.getMaxCountParticipants());
        ev.setNameEvent(data.getName());
        ev.setNumberPointsEvent(data.getPoints());
        ev.setIsParticipation(false);

        entityManager.persist(ev);
    }

    @Override
    @Transactional
    public void deleteEvent(Integer eventID) {
        EEvent events = entityManager.find(EEvent.class, eventID);

        if (events != null) {
            entityManager.remove(events);
        } else {
            throw new EntityNotFoundException("Event with ID " + eventID + " not found.");
        }
    }

    @Override
    @Transactional
    public void changeEventInfo(Integer eventID, Event data) {
        EEvent ev = entityManager.find(EEvent.class, eventID);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(data.getDate(), formatter);
        calendar.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        ev.setAddressEvent(data.getAddress());
        ev.setAgeRestrictions(data.getAge());
        ev.setDateEvent(calendar);
        ev.setDescriptionEvent(data.getDescription());
        ev.setEventFormat(data.getFormat());
        ev.setEventType(data.getType());
        ev.setImage(data.getImage());
        ev.setLinkDobroRF(data.getLinkDobroRF());
        ev.setMaxNumberParticipants(data.getMaxCountParticipants());
        ev.setNameEvent(data.getName());
        ev.setNumberPointsEvent(data.getPoints());

        entityManager.merge(ev);
    }

    @Override
    public List<UserEvent> receiveUsersByEvent(Integer eventID) {
        EEvent event = entityManager.find(EEvent.class, eventID);

        List<EUserEvent> results = entityManager.createQuery(
                "SELECT p from EUserEvent p where p.fkEventID = :id",
                EUserEvent.class)
                .setParameter("id", event)
                .getResultList();
        if (results.isEmpty()) {
            return null;
        } else {
            calendar.setTime(Date.from(timestamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));

            List<UserEvent> usersEvent = new ArrayList<>();
            for (EUserEvent ue : results) {
                UserEvent userEvent = new UserEvent();
                User user = new User();
                user.setUserID(ue.getFkUserID().getIdUser());
                user.setLastName(ue.getFkUserID().getLastName());
                user.setName(ue.getFkUserID().getFirstName());
                user.setPatronymic(ue.getFkUserID().getPatronymic());
                user.setStatus(ue.getFkEventID().getDateEvent().compareTo(calendar) > 0
                        ? event.getIsParticipation()
                        : false
                );
                userEvent.setUser(user);
                userEvent.setStampParticipate(ue.getStampParticipate());
                userEvent.setTimeParticipate(ue.getTimeParticipate());

                usersEvent.add(userEvent);
            }
            return usersEvent;
        }
    }

    @Override
    @Transactional
    public String saveInfoParticipance(Integer eventID, UserEvent ue) {

        EEvent event = entityManager.find(EEvent.class, eventID);
        EUser user = entityManager.find(EUser.class, ue.getUserID());
        EUserEvent userEvent = entityManager
                .createQuery("Select p from EUserEvent p where p.fkUserID = :idU and p.fkEventID = :idE",
                        EUserEvent.class)
                .setParameter("idU", user).setParameter("idE", event).getSingleResult();

        try {
            userEvent.setStampParticipate(ue.getStampParticipate());
            userEvent.setTimeParticipate(ue.getTimeParticipate());
            entityManager.merge(userEvent);

            event.setIsParticipation(true);
            entityManager.merge(event);

            EPoints points = entityManager.createQuery("select p from EPoints p where p.fkUserID = :idU", EPoints.class)
                    .setParameter("idU", user).getSingleResult();

            if (ue.getStampParticipate()) {
                points.setPoints(points.getPoints() + event.getNumberPointsEvent());
                entityManager.merge(points);
            }

            return "all completed";
        } catch (Exception e) {
            return "" + e;
        }

    }
}
