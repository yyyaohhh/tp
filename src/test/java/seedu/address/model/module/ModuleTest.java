package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_CS2101;
import static seedu.address.testutil.ModuleUtil.clearUserInputFields;
import static seedu.address.testutil.ModuleUtil.getAltGrade;
import static seedu.address.testutil.ModuleUtil.getAltSemester;
import static seedu.address.testutil.ModuleUtil.getAltYear;
import static seedu.address.testutil.TypicalModules.CS2040S;
import static seedu.address.testutil.TypicalModules.CS2101;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ModuleBuilder;

public class ModuleTest {
    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(CS2040S.isSameModule(CS2040S));

        // null -> returns false
        assertFalse(CS2040S.isSameModule(null));

        // same module code, different user inputs -> returns true
        Module editedCS2040S = CS2040S.fillUserInputs(getAltYear(CS2040S.getYearTaken()),
                getAltSemester(CS2040S.getSemesterTaken()), getAltGrade(CS2040S.getGrade()));
        assertTrue(CS2040S.isSameModule(editedCS2040S));

        // different module code, all other attributes same -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withCode(VALID_CODE_CS2101).build();
        assertFalse(CS2040S.isSameModule(editedCS2040S));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module cS2040SCopy = new ModuleBuilder(CS2040S).build();
        assertEquals(CS2040S, cS2040SCopy);

        // same object -> returns true
        assertEquals(CS2040S, CS2040S);

        // null -> returns false
        assertNotEquals(CS2040S, null);

        // different type -> returns false
        assertNotEquals(5, CS2040S);

        // different module -> returns false
        assertNotEquals(CS2040S, CS2101);

        // different module code -> returns false
        Module editedCS2040S = new ModuleBuilder(CS2040S).withCode(VALID_CODE_CS2101).build();
        assertNotEquals(CS2040S, editedCS2040S);

        // different name -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withName(VALID_CODE_CS2101).build();
        assertNotEquals(CS2040S, editedCS2040S);

        // different description -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withDescription(VALID_CODE_CS2101).build();
        assertNotEquals(CS2040S, editedCS2040S);

        // different modular credit -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withModularCredit("0").build();
        assertNotEquals(CS2040S, editedCS2040S);

        // different year -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withYear(VALID_YEAR_CS2101).build();
        assertNotEquals(CS2040S, editedCS2040S);

        // different semester -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withSem(VALID_SEMESTER_CS2101).build();
        assertNotEquals(CS2040S, editedCS2040S);

        // different grade -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withGrade(VALID_GRADE_CS2101).build();
        assertNotEquals(CS2040S, editedCS2040S);

        // one with any null user fields -> returns false
        editedCS2040S = clearUserInputFields(CS2040S);
        assertNotEquals(CS2040S, editedCS2040S);
        assertNotEquals(editedCS2040S, CS2040S);

        // both with null user fields -> returns true
        Module clearedCS2040S = clearUserInputFields(CS2040S);
        editedCS2040S = clearUserInputFields(CS2040S);
        assertEquals(clearedCS2040S, editedCS2040S);
    }

    @Test
    public void toStringMethod() {
        String expected = Module.class.getCanonicalName() + "{moduleCode=" + CS2040S.getModuleCode()
                + ", year=" + CS2040S.getYearTaken() + ", semester=" + CS2040S.getSemesterTaken()
                + ", grade=" + CS2040S.getGrade() + "}";
        assertEquals(expected, CS2040S.toString());
    }
}
