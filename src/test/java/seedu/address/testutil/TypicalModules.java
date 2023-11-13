package seedu.address.testutil;

import static seedu.address.testutil.ModuleUtil.clearUserInputFields;

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
    public static final Module CS2101 = new ModuleBuilder()
            .withCode("CS2101")
            .withYear("2")
            .withSem("1")
            .withGrade("B-")
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
            .withYear("5")
            .withSem("2")
            .withGrade("B")
            .withName("Introduction to Operating Systems")
            .withModularCredit("4")
            .withDescription("Learn about operating systems")
            .build();
    public static final Module CS3230 = new ModuleBuilder()
            .withCode("CS3230")
            .withYear("3")
            .withSem("2")
            .withGrade("IP")
            .withName("Design and Analysis of Algorithms")
            .withModularCredit("4")
            .withDescription("This course introduces different techniques of designing and analysing algorithms.")
            .build();
    public static final Module IS6000 = new ModuleBuilder()
            .withCode("IS6000")
            .withYear("4")
            .withSem("2")
            .withGrade("A")
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
    public static final Module CFG1002 = new ModuleBuilder()
            .withCode("CFG1002")
            .withYear("1")
            .withSem("1")
            .withGrade("CS")
            .withName("Career Catalyst")
            .withModularCredit("2")
            .withDescription("A CS/CU module")
            .build();

    // Modules defined based on their presence in modulePlan and moduleData
    public static final Module MODULE_IN_BOTH = CS2101;
    public static final Module MODULE_ONLY_DATA = CS2040S;
    public static final Module MODULE_IN_NEITHER = CS9999;

    // Module with 0 MC
    public static final Module MODULE_ZERO_MC = IS6000;

    // Module with CS grade
    public static final Module MODULE_CS_GRADE = CFG1002;

    private TypicalModules() {
    }

    public static ModulePlan getTypicalModulePlan() {
        ModulePlan modulePlan = new ModulePlan();
        modulePlan.addModule(MODULE_IN_BOTH);
        return modulePlan;
    }

    public static ModulePlan getTypicalModulePlanWith(Module module) {
        ModulePlan modulePlan = getTypicalModulePlan();
        modulePlan.addModule(module);
        return modulePlan;
    }

    public static ModulePlan getTypicalModulePlanWithout(Module module) {
        ModulePlan modulePlan = getTypicalModulePlan();
        modulePlan.removeModule(module);
        return modulePlan;
    }

    public static ModuleData getTypicalModuleData() {
        ModuleData moduleData = new ModuleData();
        moduleData.addModule(clearUserInputFields(MODULE_IN_BOTH));
        moduleData.addModule(clearUserInputFields(MODULE_ONLY_DATA));
        return moduleData;
    }
}
