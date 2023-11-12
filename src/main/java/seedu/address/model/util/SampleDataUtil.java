package seedu.address.model.util;

import seedu.address.model.module.Description;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * Contains utility methods for populating {@code ModulePlan} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS1231S"), new Year("1"), new Semester("1"), new Grade("A+"),
                    new ModuleName("Discrete Structures"),
                    new Description("Introduces mathematical tools required the study of Computer Science"),
                    new ModularCredit("4")),

            new Module(new ModuleCode("CS2040S"), new Year("1"), new Semester("2"), new Grade("A"),
                    new ModuleName("Data Structures and Algorithms"),
                    new Description(
                            "Covers the design and implementation of efficient data structures and algorithms"),
                    new ModularCredit("4")),

            new Module(new ModuleCode("CS2101"), new Year("2"), new Semester("1"), new Grade("A-"),
                    new ModuleName("Effective Communication for Computing Professionals"),
                    new Description("Equips students with the skills needed to communicate technical information"),
                    new ModularCredit("4")),
        };
    }

    public static ReadOnlyModulePlan getSampleModulePlan() {
        ModulePlan sampleMp = new ModulePlan();

        for (Module sampleModule : getSampleModules()) {
            sampleMp.addModule(sampleModule);
        }

        return sampleMp;
    }
}
