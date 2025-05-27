package local.arch.infrastructure.builder.organization_annotation;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.application.interfaces.page.organization.IOrganizationService;
import local.arch.application.interfaces.page.organization.IStorageOrganization;
import local.arch.application.interfaces.page.organization.IStorageOrganizationUsing;

public class BuilderOrganization {
    @Inject
    private IOrganizationService organizationService;

    @Inject
    private IStorageOrganization storageOrganization;

    @BuiltOrganization
    @Produces
    public IOrganizationService buildIOrganizationService() {
        ((IStorageOrganizationUsing) organizationService).useStorage(storageOrganization);
        return organizationService;
    }
}
