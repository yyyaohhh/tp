package seedu.address.model.moduleplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.GEA1000;
import static seedu.address.testutil.TypicalModules.CS2040S;



import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;
import seedu.address.model.moduleplan.exceptions.DuplicateSemesterException;

import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

public class ModulePlanTest {

    private final ModulePlan modulePlan = new ModulePlan();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), modulePlan.getModulePlanSemesterList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlan.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModulePlan_replacesData() {
        ModulePlan newData = TypicalModules.getTypicalModulePlan();
        modulePlan.resetData(newData);
        assertEquals(newData, modulePlan);
    }

    @Test
    public void resetData_withDuplicateSemester_throwsDuplicateSemesterException() {
        // Two persons with the same identity fields
        ModulePlanSemester y1s1 = new ModulePlanSemester(new Year("1"), new Semester("SEMESTER_1"));
        ModulePlanSemester duplicateY1s1 = new ModulePlanSemester(new Year("1"), new Semester("SEMESTER_1"));


        List<ModulePlanSemester> newModulePlan = Arrays.asList(y1s1, duplicateY1s1);
        ModulePlanStub newData = new ModulePlanStub(newModulePlan);

        assertThrows(DuplicateSemesterException.class, () -> modulePlan.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlan.hasModule(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modulePlan.hasModule(GEA1000));
    }

    @Test
    public void hasModule_moduleInModulePlan_returnsTrue() {
        modulePlan.addSemester(new ModulePlanSemester(CS2040S.getYearTaken(), CS2040S.getSemesterTaken()));
        modulePlan.addModule(CS2040S);
        assertTrue(modulePlan.hasModule(CS2040S));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInModulePlan_returnsTrue() {
        modulePlan.addSemester(new ModulePlanSemester(CS2040S.getYearTaken(), CS2040S.getSemesterTaken()));
        modulePlan.addModule(CS2040S);
        Module editedCS2040S = new ModuleBuilder()
                .withCode("CS2040S")
                .withYear("1")
                .withSem("SEMESTER_1")
                .withGrade("B-").build();

        assertTrue(modulePlan.hasModule(editedCS2040S));
    }

    @Test
    public void getModulePlanSemesterList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modulePlan.getModulePlanSemesterList().remove(0));
    }

    @Test
    public void toStringMethod() {
//        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
//        assertEquals(expected, modulePlan.toString());
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
