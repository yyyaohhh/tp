package seedu.address.testutil;


import seedu.address.model.module.Description;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * A utility class to help with building Person objects.
 */
public class ModuleBuilder {
    public static final String DEFAULT_CODE = "CS2100";
    public static final String DEFAULT_YEAR = "2";
    public static final String DEFAULT_SEM = "1";
    public static final String DEFAULT_GRADE = "A";
    public static final String DEFAULT_NAME = "Computer Organisation";
    public static final String DEFAULT_MC = "4";
    public static final String DEFAULT_DESC = "Learn about computer organisation";

    private ModuleCode code;
    private Year year;
    private Semester semester;
    private Grade grade;
    private ModuleName moduleName;
    private Description description;
    private ModularCredit modularCredit;

    /**
     * Creates a {@code ModuleBuilder} with the default details
     */
    public ModuleBuilder() {
        code = new ModuleCode(DEFAULT_CODE);
        year = new Year(DEFAULT_YEAR);
        semester = new Semester(DEFAULT_SEM);
        grade = new Grade(DEFAULT_GRADE);
        moduleName = new ModuleName(DEFAULT_NAME);
        modularCredit = new ModularCredit(DEFAULT_MC);
        description = new Description(DEFAULT_DESC);
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        code = moduleToCopy.getModuleCode();
        year = moduleToCopy.getYearTaken();
        semester = moduleToCopy.getSemesterTaken();
        grade = moduleToCopy.getGrade();
        moduleName = moduleToCopy.getName();
        modularCredit = moduleToCopy.getModularCredit();
        description = moduleToCopy.getDescription();
    }

    /**
     * Sets the {@code moduleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String code) {
        this.code = new ModuleCode(code);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Module} that we are building.
     */
    public ModuleBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code sem} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSem(String sem) {
        this.semester = new Semester(sem);
        return this;
    }

    /**
     * Sets the {@code grade} of the {@code Module} that we are building.
     */
    public ModuleBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    /**
     * Sets the {@code moduleName} of the {@code Module} that we are building.
     */
    public ModuleBuilder withName(String name) {
        this.moduleName = new ModuleName(name);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Module} that we are building.
     */
    public ModuleBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code modularCredit} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModularCredit(String modularCredit) {
        this.modularCredit = new ModularCredit(modularCredit);
        return this;
    }


    public Module build() {
        return new Module(code, year, semester, grade, moduleName, description, modularCredit);
    }
}
