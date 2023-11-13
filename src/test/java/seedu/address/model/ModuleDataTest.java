package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.ModuleUtil.clearUserInputFields;
import static seedu.address.testutil.TypicalModules.MODULE_IN_NEITHER;
import static seedu.address.testutil.TypicalModules.MODULE_ONLY_DATA;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.DuplicateModuleException;

public class ModuleDataTest {

    private final ModuleData moduleData = new ModuleData();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), moduleData.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleData.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModuleData_replacesData() {
        ModuleData newData = getTypicalModuleData();
        moduleData.resetData(newData);
        assertEquals(newData, moduleData);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two modules with the same identity fields
        Module module = MODULE_ONLY_DATA;
        Module duplicateModule = new Module(module.getModuleCode(), module.getName(),
                module.getDescription(), module.getModularCredit());

        List<Module> newModuleData = Arrays.asList(module, duplicateModule);
        ModuleDataStub newData = new ModuleDataStub(newModuleData);

        assertThrows(DuplicateModuleException.class, () -> moduleData.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleData.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInModuleData_returnsFalse() {
        Module module = MODULE_IN_NEITHER;
        assertFalse(moduleData.hasModule(module));
    }

    @Test
    public void hasModule_moduleInModuleData_returnsTrue() {
        moduleData.resetData(getTypicalModuleData());

        // Module with user inputs
        Module module = MODULE_ONLY_DATA;
        assertTrue(moduleData.hasModule(module));

        // Module without user inputs
        module = clearUserInputFields(module);
        assertTrue(moduleData.hasModule(module));
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> moduleData.getModuleList().remove(0));
    }

    @Test
    public void toStringMethod() {
        moduleData.resetData(getTypicalModuleData());
        String expectedString = ModuleData.class.getCanonicalName() + "{modules="
                + moduleData.getModuleList() + "}";
        assertEquals(expectedString, moduleData.toString());
    }

    @Test
    public void equals() {
        moduleData.resetData(getTypicalModuleData());

        // same values -> returns true
        ModuleData moduleDataCopy = new ModuleData();
        moduleDataCopy.resetData(getTypicalModuleData());
        assertEquals(moduleData, moduleDataCopy);

        // same object -> returns true
        assertEquals(moduleData, moduleData);

        // null -> returns false
        assertNotEquals(moduleData, null);

        // different types -> returns false
        assertNotEquals(5, moduleData);

        // different moduleData -> returns false
        assertNotEquals(moduleData, new ModuleData());

        // different moduleData -> returns false
        ModuleData differentModuleData = new ModuleData();
        differentModuleData.addModule(MODULE_IN_NEITHER);
        assertNotEquals(moduleData, differentModuleData);
    }

    @Test
    public void hashCodeTest() {
        moduleData.resetData(getTypicalModuleData());

        // different moduleData
        ModuleData differentModuleData = new ModuleData();
        differentModuleData.addModule(MODULE_IN_NEITHER);
        assertFalse(moduleData.hashCode() == differentModuleData.hashCode());
    }

    /**
     * A stub ReadOnlyModuleData whose modules list can violate interface constraints.
     */
    private static class ModuleDataStub implements ReadOnlyModuleData {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();

        ModuleDataStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
    }

}
