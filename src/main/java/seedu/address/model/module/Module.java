package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

/**
 * Represents a Module in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {
    // Identity fields
    private final moduleName moduleName;
    private final ModuleCode moduleCode;

    // Data fields
    private final Description description;
    private final List<Lecturer> lecturers;
    private final Year yearTaken;
    private final Semester semesterTaken;
    private final Grade grade;

    /**
     * Every field must be present and not null.
     */
    public Module(
            moduleName moduleName, ModuleCode moduleCode, Description description, List<Lecturer> lecturers,
            Year yearTaken, Semester semesterTaken, Grade grade) {
        requireAllNonNull(moduleName, moduleCode, description, lecturers, yearTaken, semesterTaken, grade);
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.description = description;
        this.lecturers = lecturers;
        this.yearTaken = yearTaken;
        this.semesterTaken = semesterTaken;
        this.grade = grade;
    }

    public moduleName getName() {
        return moduleName;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Description getDescription() {
        return description;
    }

    public List<Lecturer> getLecturers() {
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
        return isSameModule(otherModule);
    }
}
