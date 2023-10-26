package seedu.address.database;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;

/**
 * Represents the database as a list of {@code DbModule}.
 */
public class DbModuleList {

    private List<DbModule> modules = new ArrayList<>();

    /**
     * Constucts an empty {@code DbModuleList}.
     */
    public DbModuleList() {}

    /**
     * Constructs a {@code DbModuleList} with the given modules.
     *
     * @param dbModuleList The list of modules to be added.
     */
    public DbModuleList(DbModuleList dbModuleList) {
        modules.addAll(dbModuleList.modules);
    }

    /**
     * Adds the given {@DbModule} to the list.
     *
     * @param module
     */
    public void addDbModule(DbModule module) {
        modules.add(module);
    }

    /**
     * Finds the {@code DbModule} which matches the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code DbModule}.
     * @throws NoSuchElementException if no such module exists in the database.
     */
    public DbModule getDbModule(ModuleCode moduleCode) throws NoSuchElementException {
        for (DbModule module : modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                return module;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns the {@code ModuleName} of the module with the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code ModuleName}.
     * @throws NoSuchElementException if no such module exists in the database.
     */
    public ModuleName getModuleName(ModuleCode moduleCode) throws NoSuchElementException {
        return getDbModule(moduleCode).getName();
    }

    /**
     * Returns the {@code Description} of the module with the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code Description}.
     * @throws NoSuchElementException if no such module exists in the database.
     */
    public Description getModuleDescription(ModuleCode moduleCode) throws NoSuchElementException {
        return getDbModule(moduleCode).getDescription();
    }

    /**
     * Returns the {@code ModularCredit} of the module with the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code ModularCredit}.
     * @throws NoSuchElementException if no such module exists in the database.
     */
    public ModularCredit getModularCredit(ModuleCode moduleCode) throws NoSuchElementException {
        return getDbModule(moduleCode).getModularCredit();
    }

    /**
     * Checks if the specified {@code ModuleCode} exists in the database.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return True if the {@code ModuleCode} exists in the database, false otherwise.
     */
    public boolean isValidModuleCode(ModuleCode moduleCode) {
        for (DbModule module : modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }
}
