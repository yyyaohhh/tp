package seedu.address.model.moduleplan;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ModulePlan implements ReadOnlyModulePlan {

    private final ModulePlanSemesterList semesters;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        semesters = new ModulePlanSemesterList();
    }

    public ModulePlan() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public ModulePlan(ReadOnlyModulePlan toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations


    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyModulePlan newData) {
        requireNonNull(newData);
        setSemesters(newData.getModulePlanSemesterList());
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate persons.
     */
    public void setSemesters(List<ModulePlanSemester> modules) {
        this.semesters.setSemesters(modules);
    }

    /**
     * Adds a new semester to the module plan.
     *
     * @param semester The semester to be added.
     */
    public void addSemester(ModulePlanSemester semester) {
        this.semesters.addSemester(semester);
    }

    /**
     * Removes a semester from the module plan.
     *
     * @param semester The semester to be removed.
     */
    public void removeSemester(ModulePlanSemester semester) {
        this.semesters.removeSemester(semester);
    }


    //// module-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasModule(Module m) {
        requireNonNull(m);
        return semesters.containsModule(m);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addModule(Module m) {
        semesters.addModule(m);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(Module key) {
        semesters.removeModule(key);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        semesters.setModules(target, editedModule);
    }

    /**
     * Finds and returns a module using its module code from the internal list of modules.
     *
     * @param code The module code to search for.
     * @return The module with the specified module code, or null if not found.
     * @throws NullPointerException If the provided module code is null.
     */
    public Module findUsingCode(ModuleCode code) {
        requireNonNull(code);
        return semesters.findModule(code);
    }

    /**
     * Calculates and returns the total modular credits of all modules in the collection.
     *
     * @return The total modular credits of all modules in the collection.
     */
    public int totalModularCredits() {
        return semesters.modularCredits();
    }

    /**
     * Calculates and returns the total grade points weighted by modular credits of all modules in the collection.
     *
     * @return The total grade points weighted by modular credits of all modules in the collection as a float.
     */
    public Float totalGradePointsByUnits() {
        return semesters.gradePointsWithUnits();
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("semesters", semesters)
                .toString();
    }

    @Override
    public ObservableList<ModulePlanSemester> getModulePlanSemesterList() {
        return semesters.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModulePlan)) {
            return false;
        }

        ModulePlan otherModulePlan = (ModulePlan) other;
        return semesters.equals(otherModulePlan.semesters);
    }

    @Override
    public int hashCode() {
        return semesters.hashCode();
    }
}
