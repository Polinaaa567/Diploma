package local.arch.infrastructure.storage;

import java.util.Date;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.wslf.levenshteindistance.LevenshteinCalculator;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import local.arch.application.interfaces.config.IFileConfig;
import local.arch.application.interfaces.page.event.IStorageEvent;
import local.arch.infrastructure.storage.model.EUser;
import local.arch.infrastructure.storage.model.ECertificate;
import local.arch.infrastructure.storage.model.EEvent;
import local.arch.infrastructure.storage.model.EPoints;
import local.arch.infrastructure.storage.model.EType;
import local.arch.domain.entities.Pagination;
import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.Rating;
import local.arch.domain.entities.page.User;
import local.arch.domain.entities.page.UserEvent;
import local.arch.infrastructure.storage.model.EUserEvent;

@Named
public class EventPsqlJPA implements IStorageEvent {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    Calendar calendar = Calendar.getInstance();

    LevenshteinCalculator calculator = new LevenshteinCalculator();

    @Inject
    IFileConfig fileConfig;

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
            event.setImageUrl((String) result[4]);
            event.setIsRelevance(((Calendar) result[1]).compareTo(calendar) > 0
                    ? true
                    : false);
            event.setIsParticipation((Boolean) result[5]);

            events.add(event);
        }

        return events;
    }

    @Override
    public Pagination receiveAllEvents(Integer page, Integer limit) {
        Integer sizeAllEvents = entityManager.createQuery("Select p from EEvent p", EEvent.class)
                .getResultList()
                .size();

        List<Object[]> results = entityManager.createQuery(
                "SELECT p.eventID, p.dateEvent, p.nameEvent, p.descriptionEvent, p.image, p.isParticipation "
                        + " FROM EEvent p "
                        + " order by p.dateEvent DESC ",
                Object[].class)
                .setMaxResults(limit * page)
                .getResultList();

        List<Event> events = new ArrayList<>();
        for (Object[] result : results) {
            Event event = new Event();
            event.setEventID((Integer) result[0]);
            event.setDateC((Calendar) result[1]);
            event.setName((String) result[2]);
            event.setDescription((String) result[3]);
            event.setImageUrl((String) result[4]);
            event.setIsRelevance(((Calendar) result[1]).compareTo(calendar) > 0
                    ? true
                    : false);
            event.setIsParticipation((Boolean) result[5]);

            events.add(event);
        }

        Pagination pagination = new Pagination();
        pagination.setTotalCount(sizeAllEvents);
        pagination.setEvents(events);

        return pagination;
    }

    @Override
    public Pagination receivePastEventsUser(Integer userID, Integer page, Integer limit) {
        List<Object[]> results;
        String query = "SELECT e.eventID, e.dateEvent, e.nameEvent, e.descriptionEvent, e.image, eu.stampParticipate, eu.timeParticipate "
                + "FROM EUserEvent eu JOIN eu.fkUserID u JOIN eu.fkEventID e "
                + "WHERE u.idUser = :id "
                + "and e.dateEvent < :timestamp "
                + "order by e.dateEvent DESC";

        Integer total = entityManager.createQuery(
                query, Object[].class)
                .setParameter("id", userID)
                .setParameter("timestamp", timestamp)
                .getResultList().size();

        if (page != null && page != 0) {
            results = entityManager.createQuery(
                    query, Object[].class)
                    .setParameter("id", userID)
                    .setParameter("timestamp", timestamp)
                    .setMaxResults(page * limit)
                    .getResultList();
        } else {
            results = entityManager.createQuery(
                    query, Object[].class)
                    .setParameter("id", userID)
                    .setParameter("timestamp", timestamp)
                    .getResultList();
        }

        List<Event> events = new ArrayList<>();
        for (Object[] result : results) {
            Event event = new Event();

            event.setEventID((Integer) result[0]);
            event.setDateC((Calendar) result[1]);
            event.setName((String) result[2]);
            event.setDescription((String) result[3]);
            event.setImageUrl((String) result[4]);
            event.setStampParticipate((Boolean) result[5]);
            event.setTimeParticipate((Double) result[6]);

            events.add(event);
        }

        Pagination pagination = new Pagination();
        pagination.setTotalCount(total);
        pagination.setEvents(events);

        return pagination;
    }

    @Override
    public Pagination receiveFutureEventsUser(Integer userID, Integer page, Integer limit) {
        List<Object[]> results;
        String query = "SELECT e.eventID, e.dateEvent, e.nameEvent, e.descriptionEvent, e.image "
                + "FROM EUserEvent eu JOIN eu.fkUserID u JOIN eu.fkEventID e "
                + "WHERE u.idUser = :id "
                + "and e.dateEvent > :timestamp "
                + "order by e.dateEvent";
        Integer total = entityManager.createQuery(
                query, Object[].class)
                .setParameter("id", userID)
                .setParameter("timestamp", timestamp)
                .getResultList().size();

        if (page != null && page != 0) {
            results = entityManager.createQuery(
                    query, Object[].class)
                    .setParameter("id", userID)
                    .setParameter("timestamp", timestamp)
                    .setMaxResults(page * limit)
                    .getResultList();
        } else {
            results = entityManager.createQuery(
                    query, Object[].class)
                    .setParameter("id", userID)
                    .setParameter("timestamp", timestamp)
                    .getResultList();
        }
        List<Event> events = new ArrayList<>();

        for (Object[] result : results) {
            Event event = new Event();
            event.setEventID((Integer) result[0]);
            event.setDateC(((Calendar) result[1]));
            event.setName((String) result[2]);
            event.setDescription((String) result[3]);
            event.setImageUrl((String) result[4]);

            events.add(event);
        }

        Pagination pagination = new Pagination();
        pagination.setTotalCount(total);
        pagination.setEvents(events);

        return pagination;
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
        event.setImageUrl(events.getImage());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(timestamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));

        if (userEvent.getUserID() != null && events.getDateEvent().compareTo(calendar) > 0) {
            EUser user = entityManager.find(EUser.class, userEvent.getUserID());

            if (user.getAgeStamp().equals("16-17") && events.getAgeRestrictions() == 18) {
                event.setStatusParticipate(true);
            } else {
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
    public UserEvent signUpForEvent(UserEvent userEvent) {
        UserEvent ue = new UserEvent();

        try {

            EEvent events = entityManager.find(EEvent.class, userEvent.getEventID());

            EUser user = entityManager.find(EUser.class, userEvent.getUserID());

            if (events.getDateEvent().before(calendar)) {
                ue.setStatus(false);
                ue.setMsg("Мероприятие прошло, записаться нельзя");

                return ue;
            } else if (user.getClothingSize() != null
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
                            ue.setCountParticipants(count);
                            ue.setStatus(false);
                            ue.setMsg("Кол-во мест на мероприятие закончилось");
                            return ue;
                        } else {

                            entityManager.persist(eu);

                            ue.setCountParticipants(count + 1);
                            ue.setStatus(true);
                            ue.setMsg("Успешная регистрация на мероприятие");

                            return ue;
                        }
                    } else {

                        entityManager.persist(eu);

                        ue.setCountParticipants(count + 1);
                        ue.setStatus(true);
                        ue.setMsg("Успешная регистрация на мероприятие");

                        return ue;
                    }
                } else {
                    ue.setCountParticipants(count);
                    ue.setStatus(true);
                    ue.setMsg("Вы уже записаны на мероприятие");

                    return ue;
                }

            } else {
                ue.setStatus(false);
                ue.setMsg("Не все данные о пользователе заполнены");

                return ue;
            }
        } catch (Exception e) {
            ue.setStatus(false);
            ue.setMsg("Ошибка: " + e);

            return ue;
        }
    }

    @Override
    @Transactional
    public UserEvent deleteUsersEvent(UserEvent userEvent) {
        UserEvent ue = new UserEvent();

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
                ue.setStatus(false);
                ue.setMsg("Произошла ошибка при удалении");

                return ue;
            } else {
                for (EUserEvent userEventToRemove : query) {
                    entityManager.remove(entityManager.contains(userEventToRemove) ? userEventToRemove
                            : entityManager.merge(userEventToRemove));
                }
                ue.setStatus(true);
                ue.setMsg("Успешно удалено мероприятие из списка");

                return ue;
            }
        } catch (Exception e) {
            ue.setStatus(false);
            ue.setMsg("Ошибка: " + e);

            return ue;
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
        ev.setImage(data.getImageUrl());
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
        EEvent event = entityManager.find(EEvent.class, eventID);

        if (event != null) {
            entityManager.remove(event);

            if (event.getImage() != null) {
                try {
                    fileConfig.deleteImage(event.getImage());
                } catch (IOException e) {
                    Logger.getLogger(getClass().getName())
                            .log(Level.WARNING, "Ошибка удаления файла: " + event.getImage(), e);
                }
            }
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

        if (data.getImage() != null && !data.getImage().isBlank() && !data.getImage().contains("event")) {
            try {
                String imageUrl = fileConfig.saveImageFromBase64(data.getImage(), "events");
                fileConfig.deleteImage(ev.getImage());

                ev.setImage(imageUrl);

            } catch (IOException e) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Ошибка удаления файла: " + data.getImage(), e);
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
    @Transactional(rollbackOn = { RuntimeException.class })
    public String saveInfoParticipance(Integer eventID, UserEvent ue) {

        try {
            EEvent event = entityManager.find(EEvent.class, eventID);
            if (event == null) {
                throw new EntityNotFoundException("Event not found");
            }

            EUser user = entityManager.find(EUser.class, ue.getUserID());
            if (user == null) {
                throw new EntityNotFoundException("User not found");
            }

            EUserEvent userEvent;
            try {
                userEvent = entityManager
                        .createQuery("SELECT p FROM EUserEvent p WHERE p.fkUserID = :idU AND p.fkEventID = :idE",
                                EUserEvent.class)
                        .setParameter("idU", user)
                        .setParameter("idE", event)
                        .getSingleResult();
            } catch (NoResultException e) {
                userEvent = new EUserEvent();
                userEvent.setFkUserID(user);
                userEvent.setFkEventID(event);
                entityManager.persist(userEvent);
            }

            userEvent.setStampParticipate(ue.getStampParticipate());
            userEvent.setTimeParticipate(ue.getTimeParticipate());
            entityManager.merge(userEvent);

            event.setIsParticipation(true);
            entityManager.merge(event);

            List<EPoints> isPoints = entityManager
                    .createQuery("SELECT p FROM EPoints p WHERE p.fkEventID = :idE AND p.fkUserID = :idU",
                            EPoints.class)
                    .setParameter("idE", event)
                    .setParameter("idU", user)
                    .getResultList();

            if (ue.getStampParticipate() && isPoints.isEmpty()) {
                EPoints points = new EPoints();
                points.setPoints(event.getNumberPointsEvent());
                points.setFkEventID(event);
                points.setDateChange(new Timestamp(System.currentTimeMillis()));
                points.setFkUserID(user);
                entityManager.persist(points);
            }

            List<ECertificate> certificate = entityManager
                    .createQuery("SELECT p FROM ECertificate p WHERE p.fkUserID = :user AND p.fkEventID = :event",
                            ECertificate.class)
                    .setParameter("user", user)
                    .setParameter("event", event)
                    .getResultList();

            if (certificate.isEmpty() && ue.getUser().getCertificate() != null) {
                ECertificate newCertificate = new ECertificate();
                newCertificate.setFkUserID(user);
                newCertificate.setFkEventID(event);
                newCertificate.setImageURL(ue.getUser().getCertificate());

                entityManager.persist(newCertificate);
            }

            return "all completed";
        } catch (Exception e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.WARNING, "Error saving participation info", e);
            throw new RuntimeException("Transaction failed", e);
        }
    }

    @Override
    public List<Rating> ratingsGet(Integer eventID, Integer userID) {
        EEvent event = entityManager.find(EEvent.class, eventID);
        if (event == null) {
            throw new EntityNotFoundException("Event not found");
        }

        EUser user = entityManager.find(EUser.class, userID);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        List<ECertificate> certificate = entityManager
                    .createQuery("SELECT p FROM ECertificate p WHERE p.fkUserID = :user AND p.fkEventID = :event",
                            ECertificate.class)
                    .setParameter("user", user)
                    .setParameter("event", event)
                    .getResultList();
        
        List<Rating> achievementsList = new ArrayList<>();
        for(ECertificate c : certificate) {
            Rating achievements = new Rating();
            achievements.setCertificate(c.getImageURL());
            achievementsList.add(achievements);
        }

        return achievementsList;
    }

    @Override
    public Pagination eventsBetwenDate(String dateStart, String dateEnd, Integer page, Integer limit) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime localDateTime = LocalDateTime.parse(dateStart, formatter);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        LocalDateTime localDateTime2 = LocalDateTime.parse(dateEnd, formatter);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant()));

        List<EEvent> events;
        String query = "SELECT p "
                + " FROM EEvent p "
                + " WHERE p.dateEvent < :end "
                + " and p.dateEvent > :start "
                + " order by p.dateEvent desc ";

        Integer total = entityManager.createQuery(
                query, EEvent.class)
                .setParameter("start", calendarStart)
                .setParameter("end", calendarEnd)
                .getResultList().size();

        if (page != null && page != 0) {
            events = entityManager.createQuery(
                    query, EEvent.class)
                    .setParameter("start", calendarStart)
                    .setParameter("end", calendarEnd)
                    .setMaxResults(page * limit)
                    .getResultList();
        } else {
            events = entityManager.createQuery(
                    query, EEvent.class)
                    .setParameter("start", calendarStart)
                    .setParameter("end", calendarEnd)
                    .getResultList();
        }

        List<Event> eventArray = new ArrayList<>();
        for (EEvent event : events) {
            Event e = new Event();
            e.setEventID(event.getEventID());
            e.setDateC(event.getDateEvent());
            e.setName(event.getNameEvent());
            e.setDescription(event.getDescriptionEvent());
            e.setImageUrl(event.getImage());
            e.setIsRelevance((event.getDateEvent()).compareTo(calendar) > 0
                    ? true
                    : false);
            e.setIsParticipation(event.getIsParticipation());

            eventArray.add(e);
        }
        Pagination pagination = new Pagination();
        pagination.setTotalCount(total);
        pagination.setEvents(eventArray);

        return pagination;
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

    @Override
    public Event findEvent(Integer eventID) {
        EEvent resEEvent = entityManager.find(EEvent.class, eventID);
        Event event = new Event();
        event.setName(resEEvent.getNameEvent());

        return event;
    }

    @Override
    public User findUser(Integer userID) {
        EUser euser = entityManager.find(EUser.class, userID);

        User user = new User();
        user.setLastName(euser.getLastName());
        user.setName(euser.getFirstName());
        user.setPatronymic(euser.getPatronymic());

        return user;
    }
}
