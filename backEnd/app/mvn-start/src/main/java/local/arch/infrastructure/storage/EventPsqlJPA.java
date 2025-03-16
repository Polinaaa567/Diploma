package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import local.arch.apllication.interfaces.event.IStorageEvent;
import local.arch.domain.entities.Event;
import local.arch.domain.utils.EventStructure;
import local.arch.infrastructure.storage.model.EEvent;

@Named
public class EventPsqlJPA implements IStorageEvent {

    // @Resource
    // private UserTransaction userTransaction;

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<Event> receiveEvents() {

        List<Object[]> results = entityManager.createQuery(
                "SELECT p.eventID, p.dateEvent, p.nameEvent, p.descriptionEvent, p.image FROM EEvent p order by p.dateEvent",
                Object[].class).getResultList();

        List<Event> events = new ArrayList<>();
        for (Object[] result : results) {
            Event event = new Event();
            event.setEventID((Integer) result[0]);
            event.setDateEvent((Timestamp) result[1]);
            event.setNameEvent((String) result[2]);
            event.setDescriptionEvent((String) result[3]);
            event.setImage((byte[]) result[4]);
            events.add(event);
        }

        return events;
    }
}
