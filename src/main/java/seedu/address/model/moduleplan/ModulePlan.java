package seedu.address.model.moduleplan;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameModule comparison)
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

    public ModulePlan() {
    }

    /**
     * Creates an ModulePlan using the Modules in the {@code toBeCopied}
     */
    public ModulePlan(ReadOnlyModulePlan toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code ModulePlan} with {@code newData}.
     */
    public void resetData(ReadOnlyModulePlan newData) {
        requireNonNull(newData);
        setSemesters(newData.getModulePlanSemesterList());
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
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
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    public boolean hasModule(Module m) {
        requireNonNull(m);
        return semesters.containsModule(m);
    }

    /**
     * Adds a module to the module plan.
     * The module must not already exist in the same semester.
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
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the address book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        semesters.setModules(target, editedModule);
    }

    /**
     * Returns a module with the specified {@code moduleCode} from the internal list of semesters.
     *
     * @param moduleCode The module code to search for.
     * @return The module with the specified module code.
     * @throws ModuleNotFoundException if no such module exists.
     */
    public Module getModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return semesters.findModule(moduleCode);
    }

    /**
     * Calculates and returns the total modular credits of all modules in the collection.
     *
     * @return The total modular credits of all modules in the collection.
     */
    public float totalModularCredits() {
        return semesters.modularCredits();
    }

    /**
     * Calculates and returns the Cumulative Average Point (CAP) for a collection of semesters.
     *
     * @return The CAP as a floating-point number based on the cumulative performance of multiple semesters.
     */
    public Float getCap() {
        return semesters.getCap();
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
