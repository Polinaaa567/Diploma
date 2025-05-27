package local.arch.application.service.organization_service;

import java.util.List;

import local.arch.application.interfaces.page.organization.IOrganizationService;
import local.arch.application.interfaces.page.organization.IStorageOrganization;
import local.arch.application.interfaces.page.organization.IStorageOrganizationUsing;
import local.arch.domain.entities.YearData;
import local.arch.domain.entities.page.FAQData;
import local.arch.domain.entities.page.InfoCenter;

public class OrganizationService implements IOrganizationService, IStorageOrganizationUsing {

    IStorageOrganization storageOrganization;

    @Override
    public void useStorage(IStorageOrganization storageOrganization) {
        this.storageOrganization = storageOrganization;
    }

    @Override
    public List<FAQData> receiveFAQ() {
        return storageOrganization.receiveFAQ();
    }

    @Override
    public InfoCenter receiveInfoAboutOrganization() {
        return storageOrganization.receiveInfoAboutOrganization();
    }

    @Override
    public List<YearData> receiveReports() {
        return storageOrganization.receiveReports();
    }
}
