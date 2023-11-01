package seedu.address.testutil;

import static seedu.address.testutil.TypicalModules.getTypicalModuleData;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyModuleData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getModulePlanFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModulePlanFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModulePlan(ReadOnlyModulePlan modulePlan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyModulePlan getModulePlan() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Module getModule(ModuleCode code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int totalModularCredits() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Float getCap() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModuleData(ReadOnlyModuleData moduleData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyModuleData getModuleData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkDbValidModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkDbValidModuleCode(ModuleCode moduleCode) {
        return getTypicalModuleData().checkDbValidModuleCode(moduleCode);
    }
    @Override
    public Module getModuleFromDb(ModuleCode moduleCode) {
        return getTypicalModuleData().getModule(moduleCode);
    }

    @Override
    public ObservableList<ModulePlanSemester> getFilteredModuleList() {
        throw new AssertionError("This method should not be called.");
    }
}
