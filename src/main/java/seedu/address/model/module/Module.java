package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Module in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {
    // Identity fields
    private final ModuleName moduleName;
    private final ModuleCode moduleCode;

    // Data fields
    private final Description description;
    private final Set<Lecturer> lecturers;
    private final Year yearTaken;
    private final Semester semesterTaken;
    private final Grade grade;
    private final ModularCredit modularCredit;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Year yearTaken, Semester semesterTaken, Grade grade, ModuleName name,
                   Description description, Set<Lecturer> lecturers, ModularCredit modularCredit) {
        requireAllNonNull(name, moduleCode, description, lecturers, yearTaken, semesterTaken, grade);
        this.moduleName = name;
        this.moduleCode = moduleCode;
        this.description = description;
        this.lecturers = lecturers;
        this.yearTaken = yearTaken;
        this.semesterTaken = semesterTaken;
        this.grade = grade;
        this.modularCredit = modularCredit;
    }

    /**
     * Constructs a {@code Module} with only user input fields.
     */
    public Module(ModuleCode moduleCode, Year year, Semester semester, Grade grade) {
        requireAllNonNull(moduleCode, year, semester, grade);
        this.moduleCode = moduleCode;
        this.yearTaken = year;
        this.semesterTaken = semester;
        this.grade = grade;
        // Temporary until we get back-end setup
        this.moduleName = null;
        this.description = null;
        this.lecturers = null;
        this.modularCredit = null;
    }

    public ModuleName getName() {
        return moduleName;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Description getDescription() {
        return description;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public Year getYearTaken() {
        return yearTaken;
    }

    public Semester getSemesterTaken() {
        return semesterTaken;
    }

    public Grade getGrade() {
        return grade;
    }

    public ModularCredit getModularCredit() {
        return modularCredit;
    }

    /**
     * Checks if two modules are the same module.
     * @param otherModule the other module to check.
     * @return true if the modules are the same, false otherwise.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null && otherModule.getModuleCode().equals(getModuleCode());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return this.moduleCode.equals(otherModule.moduleCode)
                && this.yearTaken.equals(otherModule.yearTaken)
                && this.semesterTaken.equals(otherModule.semesterTaken)
                && this.grade.equals(otherModule.grade);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("moduleCode", moduleCode)
                .add("year", yearTaken)
                .add("semester", semesterTaken)
                .add("grade", grade)
                .toString();
    }
}
