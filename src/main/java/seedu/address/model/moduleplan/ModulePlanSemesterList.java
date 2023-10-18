package seedu.address.model.moduleplan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.exceptions.DuplicateSemesterException;
import seedu.address.model.moduleplan.exceptions.SemesterNotFoundException;


/**
 * A list of semesters that enforces uniqueness between its elements and does not allow nulls.
 * A semester is considered unique by comparing using {@code ModulePlanSemester#equals(ModulePlanSemester)}. As such, adding and updating of
 * module uses ModulePlanSemester#equals(ModulePlanSemester) for equality so as to ensure that the semester being added or updated is
 * unique in terms of identity in the ModulePlanSemesterList.
 *
 * Supports a minimal set of list operations.
 */
public class ModulePlanSemesterList implements Iterable<ModulePlanSemester> {
    private final ObservableList<ModulePlanSemester> internalList = FXCollections.observableArrayList();
    private final ObservableList<ModulePlanSemester> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /// semester functions


    /**
     * Replaces the contents of this list with {@code semesters}.
     * {@code semesters} must not contain duplicate semesters.
     */
    public void setSemesters(List<ModulePlanSemester> semesters) {
        requireAllNonNull(semesters);

        internalList.setAll(semesters);
    }

    /**
     * Replaces the semesters in the internal list with the semesters from the provided `UniModulePlanSemesterListqueModuleList`.
     *
     * @param replacement The `ModulePlanSemesterList` containing the semesters to replace the internal list.
     * @throws NullPointerException If the provided replacement is null.
     */
    public void setSemesters(ModulePlanSemesterList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean containsSemester(ModulePlanSemester toCheck) {
        requireNonNull(toCheck);

        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a semester to the list.
     * The semester must not already exist in the list.
     *
     * @param semester The semester to be added.
     */
    public void addSemester(ModulePlanSemester semester) {
        requireNonNull(semester);

        if(containsSemester(semester)) {
            throw new DuplicateSemesterException();
        }
        internalList.add(semester);
    }

    /**
     * Removes a semester in the list.
     * The semester must exist in the list.
     *
     * @param semester The semester to be removed.
     */
    public void removeSemester(ModulePlanSemester semester) {
        requireNonNull(semester);

        if(!containsSemester(semester)) {
            throw new SemesterNotFoundException();
        }
        internalList.remove(semester);

    }


    //// modules functions

    /**
     * Returns true if the any of the semesters contains an equivalent module as the given argument.
     */
    public boolean containsModule(Module toCheck) {
        requireNonNull(toCheck);
        for (ModulePlanSemester m : internalList) {
            if (!m.hasModule(toCheck)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a module to the correct semester.
     * The module must not already exist in that semester.
     */
    public void addModule(Module toAdd) {
        requireNonNull(toAdd);

        int index = findSemester(toAdd);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        internalList.get(index).addModule(toAdd);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedmodule}.
     * {@code target} must exist in the that semester.
     * The module identity of {@code editedmodule} must not be the same as another existing module in the list.
     */
    public void setModules(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int indexTarget = findSemester(target);
        int indexEdit = findSemester(editedModule);

        if (indexTarget == -1 || indexEdit == -1) {
            throw new ModuleNotFoundException();
        }

        if (indexTarget != indexEdit) {
            throw new ModuleNotFoundException();
        }

        internalList.get(indexTarget).setModule(target, editedModule);
    }

    /**
     * Removes the equivalent module from the appropriate semester.
     * The module must exist in the list.
     */
    public void removeModule(Module toRemove) {
        requireNonNull(toRemove);

        int index = findSemester(toRemove);

        if (index == -1) {
            throw new ModuleNotFoundException();
        }
        internalList.get(index).removeModule(toRemove);
    }



    /**
     * Finds and returns the module with the specified module code.
     *
     * @param code The module code to search for.
     * @return The module with the specified code.
     * @throws ModuleNotFoundException If no module with the given code is found.
     */
    public Module findModule(ModuleCode code) {
        for (int i = 0; i < internalList.size(); i++) {
            Module m = internalList.get(i).findUsingCode(code);
            if (m != null) {
                return m;
            }
        }
        throw new ModuleNotFoundException();
    }

    /**
     * Calculates and returns the total modular credits of all modules in the internal list.
     *
     * @return The total modular credits of all modules in the internal list.
     */
    public int modularCredits() {
        int modularCredits = 0;
        for (int i = 0; i < internalList.size(); i++) {
            modularCredits += internalList.get(i).totalModularCredits();
        }
        return modularCredits;
    }

    /**
     * Calculates and returns the total grade points weighted by the modular credits of all modules in the internal list.
     *
     * @return The total grade points weighted by modular credits as a floating-point number.
     */
    public Float gradePointsWithUnits() {
        float gradePoints = 0;
        for (int i = 0; i < internalList.size(); i++) {
            gradePoints += internalList.get(i).totalGradePointsByUnits();
        }
        return gradePoints;
    }



    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ModulePlanSemester> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ModulePlanSemester> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModulePlanSemesterList)) {
            return false;
        }

        ModulePlanSemesterList otherUniqueModuleList = (ModulePlanSemesterList) other;
        return internalList.equals(otherUniqueModuleList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }


    private int findSemester(Module m) {
        for (int i = 0; i < internalList.size(); i++) {
            if(internalList.get(i).checkModuleInSemester(m)) {
                return i;
            }
        }
        return -1;
    }

}
