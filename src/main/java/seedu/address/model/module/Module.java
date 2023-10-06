package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Module {
    // Identity fields
    private final Name name;
    private final ModuleCode moduleCode;

    // Data fields
    private final Description description;
    private final Lecturer lecturer;
    private final Year yearTaken;
    private final Semester semesterTaken;
    private final Grade grade;

    public Module(
            Name name, ModuleCode moduleCode, Description description, Lecturer lecturer,
            Year yearTaken, Semester semesterTaken, Grade grade) {
        requireAllNonNull(name, moduleCode, description, lecturer, yearTaken, semesterTaken, grade);
        this.name = name;
        this.moduleCode = moduleCode;
        this.description = description;
        this.lecturer = lecturer;
        this.yearTaken = yearTaken;
        this.semesterTaken = semesterTaken;
        this.grade = grade;
    }

    public Name getName() {
        return name;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Description getDescription() {
        return description;
    }

    public Lecturer getLecturer() {
        return lecturer;
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
