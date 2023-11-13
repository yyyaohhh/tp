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
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * Represents the in-memory model of the module plan data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModulePlan modulePlan;
    private final UserPrefs userPrefs;
    private final ModuleData moduleData;

    /**
     * Initializes a ModelManager with the given modulePlan, userPrefs and moduleData.
     */
    public ModelManager(ReadOnlyModulePlan modulePlan, ReadOnlyUserPrefs userPrefs, ReadOnlyModuleData moduleData) {
        requireAllNonNull(modulePlan, userPrefs, moduleData);

        logger.fine("Initializing with module plan: " + modulePlan + " user prefs " + userPrefs
                + " and module data " + moduleData);

        this.modulePlan = new ModulePlan(modulePlan);
        this.userPrefs = new UserPrefs(userPrefs);
        this.moduleData = new ModuleData(moduleData);
    }

    public ModelManager() {
        this(new ModulePlan(), new UserPrefs(), new ModuleData());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public Path getModulePlanFilePath() {
        return userPrefs.getModulePlanFilePath();
    }

    @Override
    public void setModulePlanFilePath(Path modulePlanFilePath) {
        requireNonNull(modulePlanFilePath);
        userPrefs.setModulePlanFilePath(modulePlanFilePath);
    }

    //=========== ModulePlan ================================================================================

    @Override
    public ReadOnlyModulePlan getModulePlan() {
        return modulePlan;
    }

    @Override
    public void setModulePlan(ReadOnlyModulePlan modulePlan) {
        this.modulePlan.resetData(modulePlan);
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
        if (modulePlan.hasModule(module)) {
            throw new DuplicateModuleException();
        }
        modulePlan.addModule(module);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);
        modulePlan.setModule(target, editedModule);
    }

    @Override
    public Module getModule(ModuleCode code) {
        requireNonNull(code);
        return modulePlan.getModule(code);
    }

    @Override
    public float totalModularCredits() {
        return modulePlan.totalModularCredits();
    }

    @Override
    public Float getCap() {
        return modulePlan.getCap();
    }

    //=========== ModuleData ===============================================================================

    @Override
    public Module getModuleFromDb(ModuleCode moduleCode) {
        return moduleData.getModule(moduleCode);
    }

    @Override
    public ReadOnlyModuleData getModuleData() {
        return moduleData;
    }

    @Override
    public void setModuleData(ReadOnlyModuleData moduleData) {
        requireNonNull(moduleData);
        this.moduleData.resetData(moduleData);
    }

    @Override
    public boolean checkDbValidModule(Module module) {
        requireNonNull(module);
        return checkDbValidModuleCode(module.getModuleCode());
    }

    @Override
    public boolean checkDbValidModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return moduleData.checkDbValidModuleCode(moduleCode);
    }

    //=========== ModulePlanSemesterList Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
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
                && userPrefs.equals(otherModelManager.userPrefs)
                && moduleData.equals(otherModelManager.moduleData);
    }

}
