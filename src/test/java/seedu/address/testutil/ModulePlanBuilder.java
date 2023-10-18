package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class ModulePlanBuilder {

    private ModulePlan modulePlan;

    public ModulePlanBuilder() {
        modulePlan = new ModulePlan();
    }

    public ModulePlanBuilder(ModulePlan modulePlan) {
        this.modulePlan = modulePlan;
    }

    /**
     * Adds a new {@code Module} to the {@code ModulePlan} that we are building.
     */
    public ModulePlanBuilder withModule(Module module) {
        modulePlan.addModule(module);
        return this;
    }

    /**
     * Adds a new {@code ModulePlanSemester} to the {@code ModulePlanSemester} that we are building.
     */
    public ModulePlanBuilder withSemester(ModulePlanSemester semester) {
        modulePlan.addSemester(semester);
        return this;
    }

    public ModulePlan build() {
        return modulePlan;
    }
}
