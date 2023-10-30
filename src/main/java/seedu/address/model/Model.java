package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getModulePlanFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setModulePlanFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setModulePlan(ReadOnlyModulePlan modulePlan);

    /** Returns the AddressBook */
    ReadOnlyModulePlan getModulePlan();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     *
     * @throws ModuleNotFoundException if the module does not exist in the address book.
     */
    void deleteModule(Module module) throws ModuleNotFoundException;

    /**
     * Adds the given module.
     * {@code module} must not already exist in the address book.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the address book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     */
    void setModule(Module target, Module editedPerson);

    /**
     * Returns the module with the specified {@code ModuleCode}.
     *
     * @throws ModuleNotFoundException if no such module exists in the module plan.
     */
    Module getModule(ModuleCode moduleCode) throws ModuleNotFoundException;

    /**
     * Calculates and returns the total modular credits of all modules in the address book.
     *
     * @return The total modular credits of all modules in the address book.
     */
    int totalModularCredits();

    /**
     * Calculates and returns the Cumulative Average Point (CAP) for a collection of semesters.
     *
     * @return The CAP as a floating-point number based on the cumulative performance of multiple semesters.
     */
    Float getCap();

    /**
     * Replaces the module data with the data in {@code moduleData}.
     */
    public void setModuleData(ReadOnlyModuleData moduleData);

    /**
     * Returns the module data.
     */
    public ReadOnlyModuleData getModuleData();

    /**
     * Checks if the specified {@code Module} is present in the database by verifying its {@code ModuleCode}.
     */
    boolean checkDbValidModule(Module module);

    /**
     * Returns the {@code ModuleName} of the module with the specified {@code ModuleCode}.
     *
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    ModuleName getDbModuleName(ModuleCode moduleCode) throws ModuleNotFoundException;

    /**
     * Returns the {@code Description} of the module with the specified {@code ModuleCode}.
     *
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    Description getDbModuleDescription(ModuleCode moduleCode) throws ModuleNotFoundException;

    /**
     * Returns the {@code ModularCedit} of the module with the specified {@code ModuleCode}.
     *
     * @throws ModuleNotFoundException if no such module exists in the database.
     */
    ModularCredit getDbModularCredit(ModuleCode moduleCode) throws ModuleNotFoundException;

    /**
     * Checks if the specified {@code ModuleCode} is present in the database.
     */
    boolean checkDbValidModuleCode(ModuleCode moduleCode);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ModulePlanSemester> getFilteredModuleList();

}
