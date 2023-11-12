package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Represents the database as a list of {@code Module}s.
 */
public class ModuleData implements ReadOnlyModuleData {

    private final UniqueModuleList modules;

    {
        modules = new UniqueModuleList();
    }

    /**
     * Constucts an empty {@code ModuleData}.
     */
    public ModuleData() {
    }

    /**
     * Creates a ModuleData using the Modules in the {@code toBeCopied}.
     *
     * @param toBeCopied The list of {@code Module}s to be added.
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
        setModules(newData.getModuleList());
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicates.
     *
     * @param modules The list of modules to be added.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /// module-level operations ---------------------------------------------------------------------------

    /**
     * Returns true if a module with the same identity as {@code module} exists in the module data.
     *
     * @param module The module to be checked.
     * @return True if the module exists in the module data, false otherwise.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds the given {@code module} to the list.
     *
     * @param module The module to be added.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Finds the {@code Module} which matches the specified {@code ModuleCode}.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return The matching {@code Module}.
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    public Module getModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        Module module = modules.find(moduleCode);
        if (module == null) {
            throw new ModuleNotFoundException();
        }
        return module;
    }

    /**
     * Checks if the specified {@code ModuleCode} exists in the database.
     *
     * @param moduleCode The {@code ModuleCode} to be matched with.
     * @return True if the {@code ModuleCode} exists in the database, false otherwise.
     */
    public boolean checkDbValidModuleCode(ModuleCode moduleCode) {
        for (Module module : modules) {
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
    public ObservableList<Module> getModuleList() {
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
