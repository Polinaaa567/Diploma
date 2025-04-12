package local.arch.application.interfaces.organization;

import java.util.List;

import local.arch.domain.entities.FAQData;
import local.arch.domain.entities.InfoCenter;

public interface IStorageOrganization {
    public List<FAQData> receiveFAQ();

    public InfoCenter receiveInfoAboutOrganization();
}
