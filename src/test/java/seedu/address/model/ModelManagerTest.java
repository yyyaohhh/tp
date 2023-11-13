package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ModuleUtil.clearUserInputFields;
import static seedu.address.testutil.TypicalModules.MODULE_IN_BOTH;
import static seedu.address.testutil.TypicalModules.MODULE_IN_NEITHER;
import static seedu.address.testutil.TypicalModules.MODULE_ONLY_DATA;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.ModulePlan;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ModulePlan(), new ModulePlan(modelManager.getModulePlan()));
    }

    //=========== UserPrefs ==================================================================================

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setModulePlanFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setModulePlanFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    //=========== ModulePlan ================================================================================

    @Test
    public void setModulePlanFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModulePlanFilePath(null));
    }

    @Test
    public void setModulePlanFilePath_validPath_setsModulePlanFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setModulePlanFilePath(path);
        assertEquals(path, modelManager.getModulePlanFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInModulePlan_returnsFalse() {
        modelManager.setModulePlan(getTypicalModulePlan());
        modelManager.setModuleData(getTypicalModuleData());

        // Module is present in moduleData but not in modulePlan
        Module module = MODULE_ONLY_DATA;
        assertFalse(modelManager.hasModule(module));

        // Module is not present in moduleData or modulePlan
        module = MODULE_IN_NEITHER;
        assertFalse(modelManager.hasModule(module));
    }

    @Test
    public void hasModule_moduleInModulePlan_returnsTrue() {
        modelManager.setModulePlan(getTypicalModulePlan());

        Module module = MODULE_IN_BOTH;
        assertTrue(modelManager.hasModule(module));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

    //=========== ModuleData ===============================================================================

    @Test
    public void setModuleData_nullModuleData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModuleData(null));
    }

    @Test
    public void getModuleFromDb_validModule_returnsModule() {
        modelManager.setModuleData(getTypicalModuleData());
        Module actualModule = modelManager.getModuleFromDb(MODULE_ONLY_DATA.getModuleCode());
        Module expectedModule = clearUserInputFields(MODULE_ONLY_DATA);
        assertEquals(expectedModule, actualModule);
    }

    @Test
    public void getModuleFromDb_invalidModule_throwsIllegalArgumentException() {
        modelManager.setModuleData(getTypicalModuleData());
        ModuleCode invalidModuleCode = MODULE_IN_NEITHER.getModuleCode();
        assertThrows(ModuleNotFoundException.class, () -> modelManager.getModuleFromDb(invalidModuleCode));
    }

    @Test
    public void checkDbValidModule_validModule_returnsTrue() {
        modelManager.setModuleData(getTypicalModuleData());
        assertTrue(modelManager.checkDbValidModule(MODULE_ONLY_DATA));
    }

    @Test
    public void checkDbValidModule_invalidModule_returnsFalse() {
        modelManager.setModuleData(getTypicalModuleData());
        assertFalse(modelManager.checkDbValidModule(MODULE_IN_NEITHER));
    }

    @Test
    public void checkDbValidModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.checkDbValidModule(null));
    }

    @Test
    public void checkDbValidModuleCode_validModule_returnsTrue() {
        modelManager.setModuleData(getTypicalModuleData());
        ModuleCode validModuleCode = MODULE_IN_BOTH.getModuleCode();
        assertTrue(modelManager.checkDbValidModuleCode(validModuleCode));
    }

    @Test
    public void checkDbValidModuleCode_invalidModule_returnsFalse() {
        modelManager.setModuleData(getTypicalModuleData());
        ModuleCode invalidModuleCode = MODULE_IN_NEITHER.getModuleCode();
        assertFalse(modelManager.checkDbValidModuleCode(invalidModuleCode));
    }

    @Test
    public void checkDbValidModuleCode_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.checkDbValidModuleCode(null));
    }

    @Test
    public void equals() {
        ModulePlan modulePlan = getTypicalModulePlan();
        UserPrefs userPrefs = new UserPrefs();
        ModuleData moduleData = getTypicalModuleData();
        modelManager = new ModelManager(modulePlan, userPrefs, moduleData);

        // same values -> returns true
        ModelManager modelManagerCopy = new ModelManager(modulePlan, userPrefs, moduleData);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(modelManager, null);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different modulePlan -> returns false
        ModulePlan differentModulePlan = new ModulePlan();
        assertNotEquals(modelManager, new ModelManager(differentModulePlan, userPrefs, moduleData));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModulePlanFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(modulePlan, differentUserPrefs, moduleData));

        // different moduleData -> returns false
        ModuleData differentModuleData = new ModuleData();
        assertNotEquals(modelManager, new ModelManager(modulePlan, userPrefs, differentModuleData));
    }

}
