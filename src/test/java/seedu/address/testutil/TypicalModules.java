package seedu.address.testutil;


import seedu.address.model.AddressBook;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    public static final Module CS2040S = new ModuleBuilder()
            .withCode("CS2040S")
            .withYear("1")
            .withSem("SEMESTER_2")
            .withGrade("A-").build();
    public static final Module CS2030S = new ModuleBuilder()
            .withCode("CS2030S")
            .withYear("1")
            .withSem("SEMESTER_2")
            .withGrade("A").build();
    public static final Module MA2001 = new ModuleBuilder()
            .withCode("MA2001")
            .withYear("1")
            .withSem("SEMESTER_2")
            .withGrade("B+").build();

    // Manually added
    public static final Module CS2101 = new ModuleBuilder()
            .withCode("MA2001")
            .withYear("2")
            .withSem("SEMESTER_1")
            .withGrade("IP").build();

    public static final Module GEA1000 = new ModuleBuilder()
            .withCode("GEA1000")
            .withYear("1")
            .withSem("SEMESTER_1")
            .withGrade("A+").build();
    //Add more

    private TypicalModules() {}

    /**
     * Returns an {@code AddressBook} with all the typical modules.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Module module : getTypicalModules()) {
            ab.addModule(module);
        }
        return ab;
    }

    public static ModulePlan getTypicalModulePlan() {
        ModulePlan mp = new ModulePlan();

        mp.addSemester(new ModulePlanSemester(new Year("1"), new Semester("SEMESTER_1")));
        mp.addSemester(new ModulePlanSemester(new Year("1"), new Semester("SEMESTER_2")));
        mp.addSemester(new ModulePlanSemester(new Year("2"), new Semester("SEMESTER_1")));
        mp.addSemester(new ModulePlanSemester(new Year("2"), new Semester("SEMESTER_2")));

        for (Module module : getTypicalModules()) {
            mp.addModule(module);
        }
        return mp;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2030S, CS2040S, MA2001, CS2101, GEA1000));
    }

}
