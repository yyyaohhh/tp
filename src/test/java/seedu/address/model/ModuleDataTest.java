package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.CS9999;
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
import seedu.address.testutil.TypicalModules;

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
        ModuleData newData = TypicalModules.getTypicalModuleData();
        moduleData.resetData(newData);
        assertEquals(newData, moduleData);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two modules with the same identity fields
        Module duplicateModule = new Module(CS2100.getModuleCode(), CS2100.getName(),
                CS2100.getDescription(), CS2100.getModularCredit());

        List<Module> newModuleData = Arrays.asList(CS2100, duplicateModule);
        ModuleDataStub newData = new ModuleDataStub(newModuleData);

        assertThrows(DuplicateModuleException.class, () -> moduleData.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleData.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInModuleData_returnsFalse() {
        assertFalse(moduleData.hasModule(CS9999));
    }

    @Test
    public void hasModule_moduleInModuleData_returnsTrue() {
        moduleData.resetData(getTypicalModuleData());
        assertTrue(moduleData.hasModule(CS2100));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInModuleData_returnsTrue() {
        moduleData.resetData(getTypicalModuleData());
        assertTrue(moduleData.hasModule(CS2100));
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
        assertTrue(moduleData.equals(moduleDataCopy));

        // same object -> returns true
        assertTrue(moduleData.equals(moduleData));

        // null -> returns false
        assertFalse(moduleData.equals(null));

        // different types -> returns false
        assertFalse(moduleData.equals(5));

        // different moduleData -> returns false
        assertFalse(moduleData.equals(new ModuleData()));

        // different moduleData -> returns false
        ModuleData differentModuleData = new ModuleData();
        differentModuleData.addModule(CS9999);
        assertFalse(moduleData.equals(differentModuleData));
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
