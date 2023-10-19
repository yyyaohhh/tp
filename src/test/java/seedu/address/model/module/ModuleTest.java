package seedu.address.model.module;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.ModuleBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_CS2101;
import static seedu.address.testutil.TypicalModules.CS2040S;
import static seedu.address.testutil.TypicalModules.CS2101;

public class ModuleTest {
    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(CS2040S.isSameModule(CS2040S));

        // null -> returns false
        assertFalse(CS2040S.isSameModule(null));

        // same module code, all other attributes different -> returns true
        Module editedCS2040S = new ModuleBuilder(CS2040S).withYear(VALID_YEAR_CS2040S).withSem(VALID_SEMESTER_CS2040S)
                .withGrade(VALID_GRADE_CS2040S).build();
        assertTrue(CS2040S.isSameModule(editedCS2040S));

        // different module code, all other attributes same -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withCode(VALID_CODE_CS2101).build();
        assertFalse(CS2040S.isSameModule(editedCS2040S));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module CS2040SCopy = new ModuleBuilder(CS2040S).build();
        assertTrue(CS2040S.equals(CS2040SCopy));

        // same object -> returns true
        assertTrue(CS2040S.equals(CS2040S));

        // null -> returns false
        assertFalse(CS2040S.equals(null));

        // different type -> returns false
        assertFalse(CS2040S.equals(5));

        // different module -> returns false
        assertFalse(CS2040S.equals(CS2101));

        // different module code -> returns false
        Module editedCS2040S = new ModuleBuilder(CS2040S).withCode(VALID_CODE_CS2101).build();
        assertFalse(CS2040S.equals(editedCS2040S));

        // different year -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withYear(VALID_YEAR_CS2101).build();
        assertFalse(CS2040S.equals(editedCS2040S));

        // different semester -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withSem(VALID_SEMESTER_CS2101).build();
        assertFalse(CS2040S.equals(editedCS2040S));

        // different grade -> returns false
        editedCS2040S = new ModuleBuilder(CS2040S).withGrade(VALID_GRADE_CS2101).build();
        assertFalse(CS2040S.equals(editedCS2040S));
    }

    @Test
    public void toStringMethod() {
        String expected = Module.class.getCanonicalName() + "{moduleCode=" + CS2040S.getModuleCode()
                + ", year=" + CS2040S.getYearTaken() + ", semester=" + CS2040S.getSemesterTaken()
                + ", grade=" + CS2040S.getGrade() + "}";
        assertEquals(expected, CS2040S.toString());
    }
}
