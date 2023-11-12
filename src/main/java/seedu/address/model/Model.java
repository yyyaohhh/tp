package seedu.address.model;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' module plan file path.
     */
    Path getModulePlanFilePath();

    /**
     * Sets the user prefs' module plan file path.
     */
    void setModulePlanFilePath(Path modulePlanFilePath);

    /**
     * Returns the ModulePlan
     */
    ReadOnlyModulePlan getModulePlan();

    /**
     * Replaces module plan data with the data in {@code modulePlan}.
     */
    void setModulePlan(ReadOnlyModulePlan modulePlan);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the module plan.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * {@code module} must already exist in the module plan.
     */
    void deleteModule(Module module);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the module plan.
     *
     * @throws DuplicateModuleException if the module already exists in the module plan.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the module plan.
     * The module identity of {@code editedModule} must not be the same as another existing module in the module plan.
     */
    void setModule(Module target, Module editedPerson);

    /**
     * Returns the module with the specified {@code ModuleCode}.
     *
     * @throws ModuleNotFoundException if no such module exists in the module plan.
     */
    Module getModule(ModuleCode moduleCode);

    /**
     * Calculates and returns the total modular credits of all modules in the module plan.
     *
     * @return The total modular credits of all modules in the module plan.
     */
    float totalModularCredits();

    /**
     * Calculates and returns the Cumulative Average Point (CAP) for a collection of semesters.
     *
     * @return The CAP as a floating-point number based on the cumulative performance of multiple semesters.
     */
    Float getCap();

    /**
     * Returns the module data.
     */
    ReadOnlyModuleData getModuleData();

    /**
     * Replaces the module data with the data in {@code moduleData}.
     */
    void setModuleData(ReadOnlyModuleData moduleData);

    /**
     * Checks if the specified {@code module} is present in the database.
     * This method checks for equality based on module code.
     *
     * @param module The module to search for.
     * @return True if the module is present in the database, false otherwise.
     */
    boolean checkDbValidModule(Module module);

    /**
     * Checks if the specified {@code moduleCode} is present in the database.
     *
     * @param moduleCode The module code to search for.
     * @return True if the module code is present in the database, false otherwise.
     */
    boolean checkDbValidModuleCode(ModuleCode moduleCode);

    /**
     * Returns the module with the specified {@code moduleCode} from the database.
     *
     * @param moduleCode The module code to search for.
     * @return The module with the specified module code.
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    Module getModuleFromDb(ModuleCode moduleCode);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<ModulePlanSemester> getFilteredModuleList();

}
