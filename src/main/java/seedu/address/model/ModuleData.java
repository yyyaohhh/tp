package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.module.DbModule;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.UniqueDbModuleList;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Represents the database as a list of {@code DbModule}s.
 */
public class ModuleData implements ReadOnlyModuleData {

    private final UniqueDbModuleList modules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueDbModuleList();
    }

    /**
     * Constucts an empty {@code ModuleData}.
     */
    public ModuleData() {}

    /**
     * Creates a ModuleData using the DbModules in the {@code toBeCopied}.
     *
     * @param toBeCopied The list of {@code DbModule}s to be added.
     */
    public ModuleData(ReadOnlyModuleData toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /// list overwrite operations ---------------------------------------------------------------------------

    /**
     * Resets the existing data of this {@code ModuleData} with {@code newData}.
     *
     * @param newData The {@code ModuleData} to be copied.
     */
    public void resetData(ReadOnlyModuleData newData) {
        requireNonNull(newData);
        setDbModules(newData.getDbModuleList());
    }

    /**
     * Replaces the contents of the module list with {@code dbModules}.
     * {@code dbModules} must not contain duplicates.
     *
     * @param dbModules The list of {@code DbModule}s to be added.
     */
    public void setDbModules(List<DbModule> dbModules) {
        this.modules.setDbModules(dbModules);
    }

    /// dbModule-level operations ---------------------------------------------------------------------------

    /**
     * Returns true if a module with the same identity as {@code dbModule} exists in the module data.
     *
     * @param dbModule The {@code DbModule} to be checked.
     * @return True if the {@code DbModule} exists in the module data, false otherwise.
     */
    public boolean hasDbModule(DbModule dbModule) {
        requireNonNull(dbModule);
        return modules.contains(dbModule);
    }

    /**
     * Adds the given {@DbModule} to the list.
     *
     * @param module The {@code DbModule} to be added.
     */
    public void addDbModule(DbModule module) {
        modules.add(module);
    }

    /**
     * Finds the {@code DbModule} which matches the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code DbModule}.
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    public DbModule getDbModule(ModuleCode moduleCode) throws ModuleNotFoundException {
        requireNonNull(moduleCode);
        return modules.findDbModule(moduleCode);
    }

    /**
     * Returns the {@code ModuleName} of the module with the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code ModuleName}.
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    public ModuleName getDbModuleName(ModuleCode moduleCode) throws ModuleNotFoundException {
        return getDbModule(moduleCode).getName();
    }

    /**
     * Returns the {@code Description} of the module with the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code Description}.
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    public Description getDbModuleDescription(ModuleCode moduleCode) throws ModuleNotFoundException {
        return getDbModule(moduleCode).getDescription();
    }

    /**
     * Returns the {@code ModularCredit} of the module with the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code ModularCredit}.
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    public ModularCredit getDbModularCredit(ModuleCode moduleCode) throws ModuleNotFoundException {
        return getDbModule(moduleCode).getModularCredit();
    }

    /**
     * Checks if the specified {@code ModuleCode} exists in the database.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return True if the {@code ModuleCode} exists in the database, false otherwise.
     */
    public boolean checkDbValidModuleCode(ModuleCode moduleCode) {
        for (DbModule module : modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    /// util methods ----------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("modules", modules)
                .toString();
    }

    @Override
    public ObservableList<DbModule> getDbModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleData)) {
            return false;
        }

        ModuleData otherModuleData = (ModuleData) other;
        return modules.equals(otherModuleData.modules);
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
