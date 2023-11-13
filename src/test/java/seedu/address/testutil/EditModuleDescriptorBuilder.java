package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * A utility class to help with building EditModuleDescriptor objects.
 */
public class EditModuleDescriptorBuilder {

    private final EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(EditModuleDescriptor descriptor) {
        this.descriptor = new EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code module}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditModuleDescriptor();
        descriptor.setYear(module.getYearTaken());
        descriptor.setSemester(module.getSemesterTaken());
        descriptor.setGrade(module.getGrade());
    }

    /**
     * Sets the {@code Year} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withYear(String year) {
        descriptor.setYear(new Year(year));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(new Semester(semester));
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withGrade(String grade) {
        descriptor.setGrade(new Grade(grade));
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}
