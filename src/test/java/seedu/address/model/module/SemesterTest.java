package seedu.address.model.module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class SemesterTest {
    @Test
    public void constructor_invalidSemester_throwsIllegalArgumentException() {
        String invalidSemester = "";
        assertThrows(IllegalArgumentException.class, () -> new Semester(invalidSemester));
    }

    @Test
    public void isValidSemester() {
        // null grade
        assertThrows(NullPointerException.class, () -> Semester.isValidSemester(null));

        // invalid grades
        assertFalse(Semester.isValidSemester("")); // empty string
        assertFalse(Semester.isValidSemester(" ")); // spaces only
        assertFalse(Semester.isValidSemester("3")); // invalid semester only

        // valid grades
        assertTrue(Semester.isValidSemester("1"));
        assertTrue(Semester.isValidSemester("2"));
    }
}
