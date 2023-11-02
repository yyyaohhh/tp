package seedu.address.model.moduleplan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.exceptions.DuplicateSemesterException;
import seedu.address.model.moduleplan.exceptions.SemesterNotFoundException;


/**
 * A list of semesters that enforces uniqueness between its elements and does not allow nulls.
 * A semester is considered unique by comparing using {@code ModulePlanSemester#equals(ModulePlanSemester)}.
 * As such, adding and updating of module uses ModulePlanSemester#equals(ModulePlanSemester)
 * for equality so as to ensure that the semester being added or updated is
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

        if (!semestersAreUnique(semesters)) {
            throw new DuplicateSemesterException();
        }
        internalList.setAll(semesters);
        Collections.sort(internalList);
    }

    /**
     * Replaces the semesters in the internal list with the semesters from
     * the provided `UniModulePlanSemesterListqueModuleList`.
     *
     * @param replacement The `ModulePlanSemesterList` containing the semesters to replace the internal list.
     * @throws NullPointerException If the provided replacement is null.
     */
    public void setSemesters(ModulePlanSemesterList replacement) {
        requireNonNull(replacement);

        if (!semestersAreUnique(replacement.internalList)) {
            throw new DuplicateSemesterException();
        }
        internalList.setAll(replacement.internalList);
        Collections.sort(internalList);
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


        if (containsSemester(semester)) {
            throw new DuplicateSemesterException();
        }
        internalList.add(semester);
        Collections.sort(internalList);
    }

    /**
     * Removes a semester in the list.
     * The semester must exist in the list.
     *
     * @param semester The semester to be removed.
     */
    public void removeSemester(ModulePlanSemester semester) {
        requireNonNull(semester);

        if (!containsSemester(semester)) {
            throw new SemesterNotFoundException();
        }
        internalList.remove(semester);
        Collections.sort(internalList);
    }

    /**
     * Check if the semester has any modules.
     *
     * @param semester The semester to be checked.
     * @return Whether the semester is empty or not.
     */
    public boolean checkIfSemesterEmpty(ModulePlanSemester semester) {
        requireNonNull(semester);

        if (!containsSemester(semester)) {
            throw new SemesterNotFoundException();
        }

        for (int i = 0; i < internalList.size(); i++) {
            if (semester.equals(internalList.get(i))) {
                return internalList.get(i).isEmpty();
            }
        }
        return false;
    }

    //// modules functions

    /**
     * Returns true if the any of the semesters contains an equivalent module as the given argument.
     */
    public boolean containsModule(Module toCheck) {
        requireNonNull(toCheck);
        for (ModulePlanSemester m : internalList) {
            if (m.hasModule(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a module to the correct semester.
     * The module must not already exist in that semester.
     */
    public void addModule(Module toAdd) {
        requireNonNull(toAdd);

        if (containsModule(toAdd)) {
            throw new DuplicateModuleException();
        }

        int index = findSemester(toAdd);

        if (index == -1) {
            ModulePlanSemester sem = new ModulePlanSemester(toAdd.getYearTaken(), toAdd.getSemesterTaken());
            addSemester(sem);

            index = findSemester(toAdd);
        }

        assert index != -1;

        internalList.get(index).addModule(toAdd);
        refreshList(index);
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

        if (indexTarget == -1) {
            throw new ModuleNotFoundException();
        }

        if (indexEdit == -1) {
            ModulePlanSemester sem = new ModulePlanSemester(editedModule.getYearTaken(), editedModule.getSemesterTaken());
            addSemester(sem);

            indexEdit = findSemester(editedModule);
        }

        assert indexTarget != -1 && indexEdit != -1;

        if (indexTarget == indexEdit) {
            internalList.get(indexTarget).setModule(target, editedModule);
            refreshList(indexTarget);
        } else {
            internalList.get(indexTarget).removeModule(target);
            internalList.get(indexEdit).addModule(editedModule);
            refreshList(indexTarget);
            refreshList(indexEdit);
        }

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
        refreshList(index);

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
            System.out.println("internalList count: " + i);
            Module m = internalList.get(i).getModule(code);
            if (m != null) {
                return m;
            }
        }
        throw new ModuleNotFoundException();
    }

    /**
     * Calculates the total modular credits of all modules in the internal list.
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
     * Calculates and returns the overall Cumulative Average Point (CAP) for a collection of modules.
     *
     * @return The overall CAP (Cumulative Average Point) as a floating-point number.
     */
    public Float getCap() {
        float gradePoints = 0;
        float moduleCredits = 0;
        for (int i = 0; i < internalList.size(); i++) {
            gradePoints += internalList.get(i).totalGradePointsWithUnits();
            moduleCredits += internalList.get(i).totalValidMcs();
        }

        if (moduleCredits == 0) {
            return 0f;
        }
        return gradePoints / moduleCredits;
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

        ModulePlanSemesterList otherModulePlanSemesterList = (ModulePlanSemesterList) other;
        return internalList.equals(otherModulePlanSemesterList.internalList);
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
            if (internalList.get(i).checkModuleBelongToSemester(m)) {
                return i;
            }
        }
        return -1;
    }

    private void refreshList(int index) {
        ModulePlanSemester temp = internalList.get(index);
        internalList.set(index, temp);
    }

    /**
     * Returns true if {@code msodules} contains only unique modules.
     */
    private boolean semestersAreUnique(List<ModulePlanSemester> semesters) {
        for (int i = 0; i < semesters.size() - 1; i++) {
            for (int j = i + 1; j < semesters.size(); j++) {
                if (semesters.get(i).equals(semesters.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
