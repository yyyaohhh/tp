package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModulePlan modulePlan;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyModulePlan modulePlan, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(modulePlan, userPrefs);

        logger.fine("Initializing with module plan: " + modulePlan + " and user prefs " + userPrefs);

        this.modulePlan = new ModulePlan(modulePlan);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new ModulePlan(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== ModulePlan ================================================================================

    @Override
    public void setModulePlan(ReadOnlyModulePlan modulePlan) {
        this.modulePlan.resetData(modulePlan);
    }

    @Override
    public ReadOnlyModulePlan getModulePlan() {
        return modulePlan;
    }


    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modulePlan.hasModule(module);
    }

    @Override
    public void deleteModule(Module module) {
        requireNonNull(module);
        modulePlan.removeModule(module);
    }
    @Override
    public void addModule(Module module) {
        requireNonNull(module);
        modulePlan.addModule(module);
    }


    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);
        modulePlan.setModule(target, editedModule);
    }

    @Override
    public Module findModuleUsingCode(ModuleCode code) {
        requireAllNonNull(code);
        return modulePlan.findUsingCode(code);
    }

    @Override
    public int totalModularCredits() {
        return modulePlan.totalModularCredits();
    }

    @Override
    public Float CAP() {
        return modulePlan.CAP();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<ModulePlanSemester> getFilteredModuleList() {
        return modulePlan.getModulePlanSemesterList();
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return modulePlan.equals(otherModelManager.modulePlan)
                && userPrefs.equals(otherModelManager.userPrefs);
    }

}
