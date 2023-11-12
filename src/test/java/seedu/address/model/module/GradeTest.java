package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GradeTest {
    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "";
        assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // null grade
        assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid grades
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("D-")); // invalid grade only
        assertFalse(Grade.isValidGrade("a")); // invalid grade only
        assertFalse(Grade.isValidGrade("b+")); // invalid grade only

        // valid grades (all possible grades)
        assertTrue(Grade.isValidGrade("A+"));
        assertTrue(Grade.isValidGrade("A"));
        assertTrue(Grade.isValidGrade("A-"));
        assertTrue(Grade.isValidGrade("B+"));
        assertTrue(Grade.isValidGrade("B"));
        assertTrue(Grade.isValidGrade("B-"));
        assertTrue(Grade.isValidGrade("C+"));
        assertTrue(Grade.isValidGrade("C"));
        assertTrue(Grade.isValidGrade("D+"));
        assertTrue(Grade.isValidGrade("D"));
        assertTrue(Grade.isValidGrade("F"));
        assertTrue(Grade.isValidGrade("EXE"));
        assertTrue(Grade.isValidGrade("IC"));
        assertTrue(Grade.isValidGrade("IP"));
        assertTrue(Grade.isValidGrade("W"));
        assertTrue(Grade.isValidGrade("CS"));
        assertTrue(Grade.isValidGrade("CU"));
        assertTrue(Grade.isValidGrade("S"));
        assertTrue(Grade.isValidGrade("U"));
    }
}
