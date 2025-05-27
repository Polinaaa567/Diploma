package local.arch.infrastructure.builder.education_annotation;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import local.arch.application.interfaces.page.education.IEducationService;
import local.arch.application.interfaces.page.education.IStorageEducation;
import local.arch.application.interfaces.page.education.IStorageEducationUsing;

public class BuilderEducation {
    @Inject
    private IEducationService educationService;

    @Inject 
    private IStorageEducation storageEducation;

    @BuiltEducation
    @Produces
    public IEducationService builEducationService() {
        ((IStorageEducationUsing) educationService).useStorage(storageEducation);
        return educationService;
    }
}
