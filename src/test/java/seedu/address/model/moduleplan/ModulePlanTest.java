package seedu.address.model.moduleplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ModuleUtil.getAltGrade;
import static seedu.address.testutil.ModuleUtil.getAltSemester;
import static seedu.address.testutil.ModuleUtil.getAltYear;
import static seedu.address.testutil.TypicalModules.MODULE_IN_BOTH;
import static seedu.address.testutil.TypicalModules.MODULE_ONLY_DATA;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.moduleplan.exceptions.DuplicateSemesterException;

public class ModulePlanTest {

    private final ModulePlan modulePlan = new ModulePlan();

    @Test
    public void constructor() {
        assertEquals(ModulePlanSemesterList.DEFAULT_SEMESTERS, modulePlan.getModulePlanSemesterList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlan.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModulePlan_replacesData() {
        ModulePlan newData = getTypicalModulePlan();
        modulePlan.resetData(newData);
        assertEquals(newData, modulePlan);
    }

    @Test
    public void resetData_withDuplicateSemester_throwsDuplicateSemesterException() {
        // Two ModulePlanSemester with the same identity fields
        ModulePlanSemester y1s1 = new ModulePlanSemester(new Year("1"), new Semester("1"));
        ModulePlanSemester duplicateY1s1 = new ModulePlanSemester(new Year("1"), new Semester("1"));


        List<ModulePlanSemester> newModulePlan = Arrays.asList(y1s1, duplicateY1s1);
        ModulePlanStub newData = new ModulePlanStub(newModulePlan);

        assertThrows(DuplicateSemesterException.class, () -> modulePlan.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlan.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInModulePlan_returnsFalse() {
        assertFalse(modulePlan.hasModule(MODULE_ONLY_DATA));
    }

    @Test
    public void hasModule_moduleInModulePlan_returnsTrue() {
        modulePlan.resetData(getTypicalModulePlan());
        assertTrue(modulePlan.hasModule(MODULE_IN_BOTH));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInModulePlan_returnsTrue() {
        modulePlan.resetData(getTypicalModulePlan());
        Module module = MODULE_IN_BOTH;
        module = module.fillUserInputs(getAltYear(module.getYearTaken()), getAltSemester(module.getSemesterTaken()),
                getAltGrade(module.getGrade()));

        assertTrue(modulePlan.hasModule(module));
    }

    @Test
    public void getModulePlanSemesterList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modulePlan.getModulePlanSemesterList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ModulePlan.class.getCanonicalName()
                + "{semesters=" + modulePlan.getModulePlanSemesterList() + "}";
        assertEquals(expected, modulePlan.toString());
    }


    @Test
    public void equals_sameInstance_returnTrue() {
        assertTrue(modulePlan.equals(modulePlan));
    }

    @Test
    public void equals_differentObj_returnFalse() {
        assertFalse(modulePlan.equals(new Integer(1)));
    }


    /**
     * A stub ReadOnlyModulePlan whose list can violate interface constraints.
     */
    private static class ModulePlanStub implements ReadOnlyModulePlan {
        private final ObservableList<ModulePlanSemester> semesters = FXCollections.observableArrayList();

        ModulePlanStub(Collection<ModulePlanSemester> semesters) {
            this.semesters.setAll(semesters);
        }

        @Override
        public ObservableList<ModulePlanSemester> getModulePlanSemesterList() {
            return semesters;
        }
    }

}
