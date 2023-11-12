package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertFalse(Semester.isValidSemester("ST3")); // invalid semester
        assertFalse(Semester.isValidSemester("3")); // invalid semester

        // valid grades
        assertTrue(Semester.isValidSemester("1"));
        assertTrue(Semester.isValidSemester("2"));
        assertTrue(Semester.isValidSemester("ST1"));
        assertTrue(Semester.isValidSemester("ST2"));
    }

    @Test
    public void toStringMethod() {
        assertTrue(new Semester("1").toString().equals("S1"));
        assertTrue(new Semester("2").toString().equals("S2"));
        assertTrue(new Semester("ST1").toString().equals("ST1"));
        assertTrue(new Semester("ST2").toString().equals("ST2"));
    }

    @Test
    public void equals() {
        Semester semester = new Semester("1");

        // same object -> returns true
        assertEquals(semester, semester);

        // same values -> returns true
        Semester semesterCopy = new Semester("1");
        assertEquals(semester, semesterCopy);

        // different types -> returns false
        assertNotEquals(semester, 1);

        // null -> returns false
        assertNotEquals(semester, null);

        // different semester -> returns false
        Semester differentSemester = new Semester("2");
        assertNotEquals(semester, differentSemester);
    }

    @Test
    public void hashCodeMethod() {
        Semester semester = new Semester("1");

        // same value
        Semester semesterCopy = new Semester("1");
        assertTrue(semester.hashCode() == semesterCopy.hashCode());

        // different value
        Semester differentSemester = new Semester("2");
        assertFalse(semester.hashCode() == differentSemester.hashCode());
    }

    @Test
    public void compareTo() {
        Semester semester = new Semester("2");

        // same object
        assertTrue(semester.compareTo(semester) == 0);

        // same value
        Semester semesterCopy = new Semester("2");
        assertTrue(semester.compareTo(semesterCopy) == 0);

        // different values
        assertTrue(semester.compareTo(new Semester("1")) > 0);
        assertTrue(semester.compareTo(new Semester("ST1")) < 0);
        assertTrue(semester.compareTo(new Semester("ST2")) < 0);
    }
}
