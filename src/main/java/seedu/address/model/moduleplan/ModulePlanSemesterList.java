package seedu.address.model.moduleplan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
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
 * <p>
 * Supports a minimal set of list operations.
 */
public class ModulePlanSemesterList implements Iterable<ModulePlanSemester> {

    protected static final List<ModulePlanSemester> DEFAULT_SEMESTERS = new ArrayList<>() {
        {
            for (int y = 1; y <= 4; y++) {
                for (int s = 1; s <= 2; s++) {
                    Year year = new Year(Integer.toString(y));
                    Semester sem = new Semester(Integer.toString(s));
                    ModulePlanSemester m = new ModulePlanSemester(year, sem);
                    add(m);
                }
            }
        }
    };

    private final ObservableList<ModulePlanSemester> internalList = FXCollections.observableArrayList();
    private final ObservableList<ModulePlanSemester> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Loads the default semesters in when ever a new instance is created.
     */
    public ModulePlanSemesterList() {
        loadDefaultSemester();
    }


    /// semester functions

    /**
     * Adds all default semester to the internal list.
     */
    private void loadDefaultSemester() {
        for (ModulePlanSemester m : DEFAULT_SEMESTERS) {
            addSemester(m.copy());
        }
    }


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
     * the provided `ModulePlanSemesterList`.
     *
     * @param replacement The `ModulePlanSemesterList` containing the semesters to replace the internal list.
     * @throws DuplicateSemesterException If the semesters are not unique.
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
     * Returns true if the list contains an equivalent semester as the given argument.
     */
    public boolean containsSemester(ModulePlanSemester toCheck) {
        requireNonNull(toCheck);

        return internalList.stream().anyMatch(toCheck::checkIfSameSemester);
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
     * Checks if the semester has any modules.
     *
     * @param toCheck The semester to be checked.
     * @return Whether the semester is empty or not.
     */
    private boolean checkIfSemesterEmpty(ModulePlanSemester toCheck) {
        if (!containsSemester(toCheck)) {
            throw new SemesterNotFoundException();
        }

        for (int i = 0; i < internalList.size(); i++) {
            if (toCheck.checkIfSameSemester(internalList.get(i))) {
                return internalList.get(i).isEmpty();
            }
        }
        return false;
    }

    /**
     * Checks if the semester is one of the default semesters.
     *
     * @param toCheck The semester to be checked.
     * @return Whether the semester is in @code DEFAULT_SEMESTERS} or not.
     */
    private boolean inDefaultSemesters(ModulePlanSemester toCheck) {
        for (ModulePlanSemester m : DEFAULT_SEMESTERS) {
            if (m.checkIfSameSemester(toCheck)) {
                return true;
            }
        }
        return false;
    }

    //// modules functions

    /**
     * Returns true if any of the semesters contains an equivalent module as the given argument.
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
     * The module must not already exist in any semesters.
     * If the semester of the module is not in the module plan, it is added.
     */
    public void addModule(Module toAdd) {
        requireNonNull(toAdd);
        if (containsModule(toAdd)) {
            throw new DuplicateModuleException();
        }

        //Verifies the semester
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
     * If the semester of the {@code editedmodule} is not in the module plan, it is added.
     */
    public void setModules(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        removeModule(target);
        addModule(editedModule);
    }

    /**
     * Removes the equivalent module from the appropriate semester.
     * The module must exist in the list.
     * Removes ModulePlanSemester if it is empty and not in default semesters.
     */
    public void removeModule(Module toRemove) {
        requireNonNull(toRemove);

        int index = findSemester(toRemove);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        internalList.get(index).removeModule(toRemove);
        refreshList(index);

        ModulePlanSemester sem = new ModulePlanSemester(toRemove.getYearTaken(), toRemove.getSemesterTaken());
        if (checkIfSemesterEmpty(sem) && !inDefaultSemesters(sem)) {
            removeSemester(sem);
        }
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
    public float modularCredits() {
        float modularCredits = 0;
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

    /**
     * Finds the index of the semester the {@code m} belongs to.
     *
     * @return The index if it is found and -1 otherwise.
     */
    private int findSemester(Module m) {
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).checkModuleBelongToSemester(m)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Refreshes the list so the UI can pick up on internal changes.
     */
    private void refreshList(int index) {
        ModulePlanSemester temp = internalList.get(index);
        internalList.set(index, temp);
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     */
    private boolean semestersAreUnique(List<ModulePlanSemester> semesters) {
        for (int i = 0; i < semesters.size() - 1; i++) {
            for (int j = i + 1; j < semesters.size(); j++) {
                if (semesters.get(i).checkIfSameSemester(semesters.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
