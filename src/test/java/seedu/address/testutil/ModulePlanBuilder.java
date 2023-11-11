package seedu.address.testutil;

import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;

/**
 * A utility class to help with building ModulePlan objects.
 * Example usage: <br>
 * {@code ModulePlan ab = new ModulePlanBuilder().withModule(module).build();}
 */
public class ModulePlanBuilder {

    private final ModulePlan modulePlan;

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
