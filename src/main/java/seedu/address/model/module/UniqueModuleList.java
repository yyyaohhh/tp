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
 *
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
     * Replaces the module {@code target} in the list with {@code editedmodule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedmodule} must not be the same as another existing module in the list.
     */
    public void setModules(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.isSameModule(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
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
     * Calculates and returns the total modular credits of all modules in the internal list.
     *
     * @return The total modular credits of all modules in the internal list.
     */
    public int modularCredits() {
        Module[] mods = new Module[internalList.size()];
        mods = internalList.toArray(mods);
        int modularCredits = 0;
        for (int i = 0; i < internalList.size(); i++) {
            modularCredits += mods[i].getModularCredit().hashCode();
        }
        return modularCredits;
    }

    /**
     * Calculates and returns the total grade points weighted by the modular credits of all modules in the internal list.
     *
     * @return The total grade points weighted by modular credits as a floating-point number.
     */
    public Float gradePointsWithUnits() {
        Module[] mods = new Module[internalList.size()];
        mods = internalList.toArray(mods);
        float gradePoints = 0;
        for (int i = 0; i < internalList.size(); i++) {
            gradePoints += mods[i].getGrade().gradePoint() * mods[i].getModularCredit().hashCode();
        }
        return gradePoints;
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
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
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
