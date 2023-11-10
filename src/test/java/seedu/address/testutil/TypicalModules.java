package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ModuleData;
import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ModulePlan;

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
            .withName("Effective Communication for Computing Professionals")
            .withModularCredit("4")
            .withDescription("This course aims to equip students with the skills needed to communicate.")
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

    public static final Module CS2106 = new ModuleBuilder()
            .withCode("CS2106")
            .withYear("2")
            .withSem("2")
            .withGrade("B")
            .withName("Introduction to Operating Systems")
            .withModularCredit("4")
            .withDescription("Learn about operating systems")
            .build();

    public static final Module IS6000 = new ModuleBuilder()
            .withCode("IS6000")
            .withYear("4")
            .withSem("2")
            .withGrade("IP")
            .withName("Topics in Information Systems and Analytics Research")
            .withModularCredit("0")
            .withDescription("A 0 MC module")
            .build();

    public static final Module CS9999 = new ModuleBuilder()
            .withCode("CS9999")
            .withYear("3")
            .withSem("1")
            .withGrade("C")
            .withName("Invalid Module")
            .withModularCredit("0")
            .withDescription("Should not be in modulePlan or moduleDatabase.")
            .build();


    private TypicalModules() {}

    private static ModulePlan modulePlan;
    public static ModulePlan getTypicalModulePlan() {
       modulePlan = new ModulePlan();
       modulePlan.resetData(modulePlan);

        List<Module> list = getTypicalModules();
        for (Module module : list) {
            modulePlan.addModule(module);
        }
        return modulePlan;
    }


    public static ModulePlan getTypicalModulePlanWithout(Module ... exclusions) {
        List<Module> exclusionList = new ArrayList(Arrays.asList(exclusions));
        ModulePlan mp = getTypicalModulePlan();
        for (Module excludedModule : exclusionList) {
            if(mp.hasModule(excludedModule)) {
                mp.removeModule(excludedModule);
            }
        }
        return new ModulePlan(mp);

    }

    public static ModuleData getTypicalModuleData() {
        ModuleData moduleData = new ModuleData();

        for (Module m : getTypicalModules()) {
            Module module = clearUserInputFields(m);
            moduleData.addModule(module);
        }
        return moduleData;
    }

    public static Module clearUserInputFields(Module module) {
        return new Module(module.getModuleCode(), module.getName(), module.getDescription(), module.getModularCredit());
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2030S, CS2040S, CS2100));
    }

}
