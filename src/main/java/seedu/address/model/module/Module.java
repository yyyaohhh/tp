package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Module in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    public static final String MESSAGE_INFO = "%1$s: %2$s \n"
            + "%3$s MC \n"
            + "%4$s \n";

    // Identity fields
    private final ModuleName moduleName;
    private final ModuleCode moduleCode;
    private final Description description;
    private final ModularCredit modularCredit;

    // Data fields
    private final Year yearTaken;
    private final Semester semesterTaken;
    private final Grade grade;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Year yearTaken, Semester semesterTaken, Grade grade, ModuleName name,
                  Description description, ModularCredit modularCredit) {
        requireAllNonNull(name, moduleCode, description, yearTaken, semesterTaken, grade);
        this.moduleName = name;
        this.moduleCode = moduleCode;
        this.description = description;
        this.yearTaken = yearTaken;
        this.semesterTaken = semesterTaken;
        this.grade = grade;
        this.modularCredit = modularCredit;
    }

    /**
     * Constructs a {@code Module} with only identity fields.
     */
    public Module(ModuleCode moduleCode, ModuleName moduleName, Description description, ModularCredit modularCredit) {
        requireAllNonNull(moduleCode, moduleName, description, modularCredit);
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.description = description;
        this.modularCredit = modularCredit;

        // User input fields
        this.yearTaken = null;
        this.semesterTaken = null;
        this.grade = null;
    }

    /**
     * Makes a new Module with the fields Year, Semester and Grade set.
     *
     * @return A new Module with the input fields.
     */
    public Module fillUserInputs(Year yearTaken, Semester semesterTaken, Grade grade) {
        requireAllNonNull(yearTaken, semesterTaken, grade);

        return new Module(moduleCode, yearTaken, semesterTaken, grade, moduleName, description, modularCredit);
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

    public String toInfoString() {
        return String.format(MESSAGE_INFO, moduleCode, moduleName, modularCredit, description);
    }

    public float getMcValue() {
        return modularCredit.getValue();
    }

    /**
     * Checks if two modules are the same module.
     *
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
                && this.moduleName.equals(otherModule.moduleName)
                && this.description.equals(otherModule.description)
                && this.modularCredit.equals(otherModule.modularCredit)
                && isEditableFieldEquals(this.yearTaken, otherModule.yearTaken)
                && isEditableFieldEquals(this.semesterTaken, otherModule.semesterTaken)
                && isEditableFieldEquals(this.grade, otherModule.grade);
    }

    private boolean isEditableFieldEquals(Object thisObj, Object otherObj) {
        if (thisObj == null && otherObj == null) {
            return true;
        } else if (thisObj == null || otherObj == null) {
            return false;
        }
        return thisObj.equals(otherObj);
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
