package seedu.address.testutil;


import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Person objects.
 */
public class ModuleBuilder {
    public static final String DEFAULT_CODE = "CS2100";
    public static final String DEFAULT_YEAR = "2";
    public static final String DEFAULT_SEM = "SEMESTER_1";
    public static final String DEFAULT_GRADE = "A";

    private ModuleCode code;
    private Year year;
    private Semester semester;
    private Grade grade;

    /**
     * Creates a {@code ModuleBuilder} with the default details
     */
    public ModuleBuilder() {
        code = new ModuleCode(DEFAULT_CODE);
        year = new Year(DEFAULT_YEAR);
        semester = new Semester(DEFAULT_SEM);
        grade = new Grade(DEFAULT_GRADE);
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        code = moduleToCopy.getModuleCode();
        year = moduleToCopy.getYearTaken();
        semester = moduleToCopy.getSemesterTaken();
        grade = moduleToCopy.getGrade();
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

    public Module build() {
        return new Module(code, year, semester, grade);
    }


}
