package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import local.arch.application.interfaces.organization.IStorageOrganization;
import local.arch.domain.entities.FAQData;
import local.arch.domain.entities.InfoCenter;

public class OrganizationPsqlJPA implements IStorageOrganization {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<FAQData> receiveFAQ() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveFAQ'");
    }

    @Override
    public InfoCenter receiveInfoAboutOrganization() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveInfoAboutOrganization'");
    }
    
}
