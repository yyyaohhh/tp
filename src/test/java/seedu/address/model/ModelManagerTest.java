package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.CS2101;
import static seedu.address.testutil.TypicalModules.CS9999;
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
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.testutil.TypicalModules;

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
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModulePlanFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
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
        assertFalse(modelManager.hasModule(CS2101));
    }

    @Test
    public void hasModule_moduleInModulePlan_returnsTrue() {
        ModulePlanSemester m = new ModulePlanSemester(CS2101.getYearTaken(), CS2101.getSemesterTaken());
        modelManager.addModule(CS2101);
        assertTrue(modelManager.hasModule(CS2101));
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
        Module actualModule = modelManager.getModuleFromDb(CS2100.getModuleCode());
        Module expectedModule = TypicalModules.clearUserInputFields(CS2100);
        assertEquals(expectedModule, actualModule);
    }

    @Test 
    public void getModuleFromDb_invalidModule_throwsIllegalArgumentException() {
        ModuleCode invalidModuleCode = CS9999.getModuleCode();
        assertThrows(ModuleNotFoundException.class, () -> modelManager.getModuleFromDb(invalidModuleCode));
    }

    @Test
    public void checkDbValidModule_validModule_returnsTrue() {
        modelManager.setModuleData(getTypicalModuleData());
        Module validModule = CS2100;
        assertTrue(modelManager.checkDbValidModule(validModule));
    }

    @Test
    public void checkDbValidModule_invalidModule_returnsFalse() {
        Module invalidModule = CS9999;
        assertFalse(modelManager.checkDbValidModule(invalidModule));
    }

    @Test
    public void checkDbValidModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.checkDbValidModule(null));
    }
    
    @Test
    public void checkDbValidModuleCode_validModule_returnsTrue() {
        modelManager.setModuleData(getTypicalModuleData());
        ModuleCode validModuleCode = CS2100.getModuleCode();
        assertTrue(modelManager.checkDbValidModuleCode(validModuleCode));
    }

    @Test
    public void checkDbValidModuleCode_invalidModule_returnsFalse() {
        ModuleCode invalidModuleCode = CS9999.getModuleCode();
        assertFalse(modelManager.checkDbValidModuleCode(invalidModuleCode));
    }

    @Test
    public void checkDbValidModuleCode_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.checkDbValidModuleCode(null));
    }

    @Test
    public void equals() {
        ModulePlan modulePlan = getTypicalModulePlan();
        ModulePlan differentModulePlan = new ModulePlan();
        UserPrefs userPrefs = new UserPrefs();
        ModuleData moduleData = getTypicalModuleData();

        // same values -> returns true
        modelManager = new ModelManager(modulePlan, userPrefs, moduleData);
        ModelManager modelManagerCopy = new ModelManager(modulePlan, userPrefs, moduleData);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentModulePlan, userPrefs, moduleData)));



        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModulePlanFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(modulePlan, differentUserPrefs, moduleData)));
    }
}
