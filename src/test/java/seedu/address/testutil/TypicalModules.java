package seedu.address.testutil;


import seedu.address.model.AddressBook;
import seedu.address.model.ModuleData;
import seedu.address.model.module.DbModule;
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
            .withSem("2")
            .withGrade("A-")
            .withName("Data Structures and Algorithms")
            .withModularCredit("4")
            .withDescription("This course introduces the fundamentals of data structures and algorithms.")
            .build();
    public static final Module CS2030S = new ModuleBuilder()
            .withCode("CS2030S")
            .withYear("1")
            .withSem("2")
            .withGrade("A")
            .withName("Programming Methodology II")
            .withModularCredit("4")
            .withDescription("This course is a follow up to CS1010.")
            .build();
    public static final Module MA2001 = new ModuleBuilder()
            .withCode("MA2001")
            .withYear("1")
            .withSem("2")
            .withGrade("B+")
            .withName("Linear Algebra I")
            .withModularCredit("4")
            .withDescription("This course is a first course in linear algebra.")
            .build();

    // Manually added
    public static final Module CS2101 = new ModuleBuilder()
            .withCode("CS2101")
            .withYear("2")
            .withSem("1")
            .withGrade("IP")
            .withName("Linear Algebra I")
            .withModularCredit("4")
            .withDescription("This course is a first course in linear algebra.")
            .build();
    public static final Module GEA1000 = new ModuleBuilder()
            .withCode("GEA1000")
            .withYear("1")
            .withSem("1")
            .withGrade("A+")
            .withName("Quantitative Reasoning with Data")
            .withModularCredit("4")
            .withDescription("This course aims to equip students with essential data literacy skills.")
            .build();
    public static final Module CS2100 = new ModuleBuilder()
            .withCode("CS2100")
            .withYear("2")
            .withSem("1")
            .withGrade("A")
            .withName("Computer Organisation")
            .withModularCredit("4")
            .withDescription("Learn about computer organisation")
            .build();
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

        mp.addSemester(new ModulePlanSemester(new Year("1"), new Semester("1")));
        mp.addSemester(new ModulePlanSemester(new Year("1"), new Semester("2")));
        mp.addSemester(new ModulePlanSemester(new Year("2"), new Semester("1")));
        mp.addSemester(new ModulePlanSemester(new Year("2"), new Semester("2")));

        for (Module module : getTypicalModules()) {
            mp.addModule(module);
        }
        return mp;
    }

    public static ModuleData getTypicalModuleData() {
        ModuleData moduleData = new ModuleData();

        for (Module m : getTypicalModules()) {
            DbModule dbModule = new DbModule(
                    m.getModuleCode(), m.getName(), m.getDescription(), m.getModularCredit());
            moduleData.addDbModule(dbModule);
        }
        return moduleData;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2030S, CS2040S, MA2001, CS2101, GEA1000));
    }

}
