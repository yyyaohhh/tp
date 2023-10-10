package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
        assertFalse(Semester.isValidSemester("SEMESTER_3")); // invalid semester
        assertFalse(Semester.isValidSemester("3")); // invalid semester

        // valid grades
        assertTrue(Semester.isValidSemester("SEMESTER_1"));
        assertTrue(Semester.isValidSemester("SEMESTER_2"));
        assertTrue(Semester.isValidSemester("SPECIAL_TERM_1"));
        assertTrue(Semester.isValidSemester("SPECIAL_TERM_2"));
    }
}
