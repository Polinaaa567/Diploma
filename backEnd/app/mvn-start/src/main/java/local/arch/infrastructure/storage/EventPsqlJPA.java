package local.arch.infrastructure.storage;

import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.wslf.levenshteindistance.LevenshteinCalculator;

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
import local.arch.infrastructure.storage.model.EType;
import local.arch.domain.entities.UserEvent;
import local.arch.infrastructure.storage.model.EUserEvent;

@Named
public class EventPsqlJPA implements IStorageEvent {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    Calendar calendar = Calendar.getInstance();

    LevenshteinCalculator calculator = new LevenshteinCalculator();

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
            // event.setImage((Byte[]) result[4]);
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
                        + "and e.dateEvent < :timestamp "
                        + "order by e.dateEvent DESC",
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
            // event.setImage((Byte[]) result[4]);
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
                        + "and e.dateEvent > :timestamp " 
                        + "order by e.dateEvent",
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
            // event.setImage((Byte[]) result[4]);

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
        // event.setImage(events.getImage());

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
        event.setType(events.getEventType() != null ? events.getEventType().getName() : "Другое");
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

        List<EType> types = entityManager.createQuery("Select p from EType p", EType.class).getResultList();

        EType type = null;

        for (EType t : types) {
            Integer distance = calculator.getLevenshteinDistance(t.getName().toLowerCase(),
                    data.getType().toLowerCase());

            if (distance <= 2) {
                type = t;
                break;
            }
        }

        if (type == null && data.getType() != null) {
            EType newType = new EType();
            newType.setName(data.getType().substring(0, 1).toUpperCase() + data.getType().substring(1).toLowerCase());
            entityManager.persist(newType);

            type = newType;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(data.getDate(), formatter);
        calendar.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        ev.setAddressEvent(data.getAddress());
        ev.setAgeRestrictions(data.getAge());
        ev.setDateCreation(timestamp);
        ev.setDateEvent(calendar);
        ev.setDescriptionEvent(data.getDescription());
        ev.setEventFormat(data.getFormat());
        ev.setEventType(type);
        // ev.setImage(data.getImage());
        // ev.setImage(Byte[]);
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

        List<EType> types = entityManager.createQuery("Select p from EType p", EType.class).getResultList();

        EType type = null;

        for (EType t : types) {
            Integer distance = calculator.getLevenshteinDistance(t.getName().toLowerCase(),
                    data.getType().toLowerCase());

            if (distance <= 2) {
                type = t;
                break;
            }
        }

        if (type == null && data.getType() != null) {
            EType newType = new EType();
            newType.setName(data.getType().substring(0, 1).toUpperCase() + data.getType().substring(1).toLowerCase());
            entityManager.persist(newType);

            type = newType;
        }

        ev.setAddressEvent(data.getAddress());
        ev.setAgeRestrictions(data.getAge());
        ev.setDateEvent(calendar);
        ev.setDescriptionEvent(data.getDescription());
        ev.setEventFormat(data.getFormat());
        ev.setEventType(type);
        // ev.setImage(data.getImage());
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
                        : false);
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
            List<EPoints> isPoints = entityManager
                    .createQuery("Select p from EPoints p where p.fkEventID = :idE and p.fkUserID = :idU",
                            EPoints.class)
                    .setParameter("idE", event)
                    .setParameter("idU", user)
                    .getResultList();

            EPoints points = new EPoints();

            if (ue.getStampParticipate() && isPoints.isEmpty()) {
                points.setPoints(event.getNumberPointsEvent());
                points.setFkEventID(event);
                points.setDateChange(timestamp);
                points.setFkUserID(user);

                entityManager.persist(points);
            }

            return "all completed";
        } catch (Exception e) {
            return "" + e;
        }
    }

    @Override
    public List<Event> eventsBetwenDate(String dateStart, String dateEnd) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime localDateTime = LocalDateTime.parse(dateStart, formatter);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        LocalDateTime localDateTime2 = LocalDateTime.parse(dateEnd, formatter);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant()));

        List<EEvent> events = entityManager.createQuery(
                "SELECT p "
                        + "FROM EEvent p "
                        + "WHERE p.dateEvent < :end "
                        + "and p.dateEvent > :start",
                EEvent.class)
                .setParameter("start", calendarStart)
                .setParameter("end", calendarEnd)
                .getResultList();

        List<Event> eventArray = new ArrayList<>();
        for (EEvent event : events) {
            Event e = new Event();
            e.setEventID(event.getEventID());
            e.setDateC(event.getDateEvent());
            e.setName(event.getNameEvent());
            e.setDescription(event.getDescriptionEvent());
            // e.setImage(event.getImage());
            e.setIsRelevance((event.getDateEvent()).compareTo(calendar) > 0
                    ? true
                    : false);
            e.setIsParticipation(event.getIsParticipation());

            eventArray.add(e);
        }
        return eventArray;
    }

    @Override
    public List<String> getTypesEvents() {
        List<EType> types = entityManager.createQuery("Select p from EType p", EType.class).getResultList();

        List<String> typesArray = new ArrayList<>();

        for (EType t : types) {
            typesArray.add(t.getName());
        }

        return typesArray;
    }
}
