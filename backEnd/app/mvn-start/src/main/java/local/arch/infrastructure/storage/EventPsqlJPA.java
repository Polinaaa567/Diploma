package local.arch.infrastructure.storage;

import java.util.List;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import local.arch.apllication.interfaces.IStorageEvent;
import local.arch.domain.entities.Event;
import local.arch.domain.utils.EventStructure;
import local.arch.infrastructure.storage.model.EEvent;

@Named
public class EventPsqlJPA implements IStorageEvent{
    
    // @Resource
    // private UserTransaction userTransaction;

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<Event> receiveEventss() {

        List<EEvent> eEvents = entityManager.createQuery("SELECT p FROM EEvent p order by p.dateEvent", EEvent.class).getResultList();
        
        List<Event> events = EventStructure.toEventList(eEvents);

        return events;
    }
}
