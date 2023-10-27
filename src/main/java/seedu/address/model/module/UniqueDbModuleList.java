package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.exceptions.DuplicateModuleException;


/**
 * A list of DbModules that enforces uniqueness between its elements and does not allow nulls.
 * A DbModule is considered unique by comparing using {@code DbModule#isSameDbModule(DbModule)}. As such, adding and
 * updating of DbModule uses DbModule#isSameDbModule(DbModule) for equality so as to ensure that the DbModule being
 * added or updated is unique in terms of identity in the UniqueDbModuleList. However, the removal of a DbModule uses
 * DbModule#equals(Object) so as to ensure that the DbModule with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see DbModule#isSameDbModule(DbModule)
 */
public class UniqueDbModuleList implements Iterable<DbModule> {
    private final ObservableList<DbModule> internalList = FXCollections.observableArrayList();
    private final ObservableList<DbModule> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent DbModule as the given argument.
     */
    public boolean contains(DbModule toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDbModule);
    }

    /**
     * Adds a DbModule to the list.
     * The DbModule must not already exist in the list.
     */
    public void add(DbModule toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the dbModules in the internal list with the dbModules from the provided `UniqueDbModuleList`.
     *
     * @param replacement The `UniqueDbModuleList` containing the dbModules to replace the internal list.
     * @throws NullPointerException If the provided replacement is null.
     */
    public void setDbModules(UniqueDbModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code dbModules}.
     * {@code dbModules} must not contain duplicate dbModules.
     */
    public void setDbModules(List<DbModule> dbModules) {
        requireAllNonNull(dbModules);
        if (!dbModulesAreUnique(dbModules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(dbModules);
    }

    /**
     * Finds and returns the DbModule with the specified DbModule code.
     *
     * @param code The DbModule code to search for.
     * @return The DbModule with the specified code.
     */
    public DbModule findDbModule(ModuleCode code) {
        DbModule[] mods = new DbModule[internalList.size()];

        mods = internalList.toArray(mods);
        for (int i = 0; i < internalList.size(); i++) {
            if (mods[i].getModuleCode().equals(code)) {
                return mods[i];
            }
        }
        return null;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<DbModule> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<DbModule> iterator() {
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
        if (!(other instanceof UniqueDbModuleList)) {
            return false;
        }

        UniqueDbModuleList otherUniqueDbModuleList = (UniqueDbModuleList) other;
        return internalList.equals(otherUniqueDbModuleList.internalList);
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
     * Returns true if {@code dbModules} contains only unique dbModules.
     */
    private boolean dbModulesAreUnique(List<DbModule> dbModules) {
        for (int i = 0; i < dbModules.size() - 1; i++) {
            for (int j = i + 1; j < dbModules.size(); j++) {
                if (dbModules.get(i).isSameDbModule(dbModules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
