package local.arch.application.interfaces.page.organization;

import java.util.List;

import local.arch.domain.entities.YearData;
import local.arch.domain.entities.page.FAQData;
import local.arch.domain.entities.page.InfoCenter;

public interface IOrganizationService {
    public List<FAQData> receiveFAQ();

    public InfoCenter receiveInfoAboutOrganization();
        
    public List<YearData> receiveReports();

}
