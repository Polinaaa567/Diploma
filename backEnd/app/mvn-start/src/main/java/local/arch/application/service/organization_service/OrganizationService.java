package local.arch.application.service.organization_service;

import java.util.List;

import local.arch.application.interfaces.organization.IOrganizationService;
import local.arch.application.interfaces.organization.IStorageOrganization;
import local.arch.application.interfaces.organization.IStorageOrganizationUsing;
import local.arch.domain.entities.FAQData;
import local.arch.domain.entities.InfoCenter;

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
}
