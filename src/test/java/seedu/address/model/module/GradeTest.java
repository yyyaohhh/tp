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

        // valid grades
        assertTrue(Grade.isValidGrade("A+"));
        assertTrue(Grade.isValidGrade("B-"));
        assertTrue(Grade.isValidGrade("F"));
        assertTrue(Grade.isValidGrade("IP"));
        assertTrue(Grade.isValidGrade("W"));
    }
}
