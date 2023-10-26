package seedu.address.model;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
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
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

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
     * The module must exist in the address book.
     */
    void deleteModule(Module module);

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
     * Finds and returns a module using its module code.
     *
     * @param code The module code used to search for the module.
     * @return The module with the specified module code, or null if not found.
     */
    Module findModuleUsingCode(ModuleCode code);

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
     * Returns the {@code ModuleName} of the module with the specified {@code ModuleCode}.
     *
     * @throws NoSuchElementException if no such module exists in the database.
     */
    ModuleName getModuleName(ModuleCode moduleCode) throws NoSuchElementException;

    /**
     * Returns the {@code Description} of the module with the specified {@code ModuleCode}.
     *
     * @throws NoSuchElementException if no such module exists in the database.
     */
    Description getModuleDescription(ModuleCode moduleCode) throws NoSuchElementException;

    /**
     * Returns the {@code ModularCedit} of the module with the specified {@code ModuleCode}.
     *
     * @throws NoSuchElementException if no such module exists in the database.
     */
    ModularCredit getModularCredit(ModuleCode moduleCode) throws NoSuchElementException;

    /**
     * Checks if the specified {@code ModuleCode} is present in the database.
     */
    boolean isValidModuleCode(ModuleCode moduleCode);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ModulePlanSemester> getFilteredModuleList();

}
