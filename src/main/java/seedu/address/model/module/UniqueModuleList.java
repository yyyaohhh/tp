package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;


/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#isSameModule(Module)}. As such, adding and updating of
 * module uses Module#isSameModule(Module) for equality so as to ensure that the module being added or updated is
 * unique in terms of identity in the UniqueModuleList. However, the removal of a module uses Module#equals(Object) so
 * as to ensure that the module with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Module#isSameModule(Module)
 */
public class UniqueModuleList implements Iterable<Module> {
    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }


    /**
     * Replaces the modules in the internal list with the modules from the provided `UniqueModuleList`.
     *
     * @param replacement The `UniqueModuleList` containing the modules to replace the internal list.
     * @throws NullPointerException If the provided replacement is null.
     */
    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Removes the equivalent module from the list.
     * The module must exist in the list.
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Finds and returns the module with the specified module code.
     *
     * @param code The module code to search for.
     * @return The module with the specified code.
     */
    public Module find(ModuleCode code) {
        Module[] mods = new Module[internalList.size()];
        mods = internalList.toArray(mods);
        for (int i = 0; i < internalList.size(); i++) {
            if (mods[i].getModuleCode().equals(code)) {
                return mods[i];
            }
        }
        return null;
    }

    /**
     * Calculates the total modular credits of all modules in the internal list.
     *
     * @return The total modular credits of all modules in the internal list.
     */
    public float modularCredits() {
        Module[] mods = new Module[internalList.size()];
        mods = internalList.toArray(mods);
        float modularCredits = 0;
        for (int i = 0; i < internalList.size(); i++) {
            modularCredits += mods[i].getMcValue();
        }
        return modularCredits;
    }

    /**
     * Calculates and returns the Cumulative Average Point (CAP) based on the grades and modular credits of all
     * modules in the internal list.
     *
     * @return The CAP (Cumulative Average Point) as a floating-point number, or 0.0 if there are no valid grades.
     */
    public Float findGradePointsWithUnits() {
        Module[] mods = new Module[internalList.size()];
        mods = internalList.toArray(mods);
        float gradePoints = 0;
        for (int i = 0; i < internalList.size(); i++) {
            if (mods[i].getGrade().gradePoint() != null) {
                gradePoints += mods[i].getGrade().gradePoint() * mods[i].getMcValue();
            }
        }
        return gradePoints;
    }

    /**
     * Calculates and returns the total modular credits of modules with valid grades in the internal list.
     *
     * @return The total modular credits of modules with valid grades as a floating-point number.
     */
    public float findMcsForCap() {
        Module[] mods = new Module[internalList.size()];
        mods = internalList.toArray(mods);
        float modularCredits = 0;
        for (int i = 0; i < internalList.size(); i++) {
            if (mods[i].getGrade().gradePoint() != null) {
                modularCredits += mods[i].getMcValue();
            }
        }
        return modularCredits;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    /**
     * Whether the list is empty or not
     *
     * @return True if it is empty and false otherwise
     */
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueModuleList)) {
            return false;
        }

        UniqueModuleList otherUniqueModuleList = (UniqueModuleList) other;
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

    /**
     * Returns true if {@code modules} contains only unique modules.
     */
    private boolean modulesAreUnique(List<Module> modules) {
        for (int i = 0; i < modules.size() - 1; i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                if (modules.get(i).isSameModule(modules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
