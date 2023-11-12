package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    public void getColourCode() {
        assertEquals(new Grade("A+").getColourCode(), "green");
        assertEquals(new Grade("A").getColourCode(), "green");
        assertEquals(new Grade("A-").getColourCode(), "green");
        assertEquals(new Grade("B+").getColourCode(), "green");
        assertEquals(new Grade("B").getColourCode(), "green");
        assertEquals(new Grade("B-").getColourCode(), "green");
        assertEquals(new Grade("C+").getColourCode(), "green");
        assertEquals(new Grade("C").getColourCode(), "green");
        assertEquals(new Grade("D+").getColourCode(), "green");
        assertEquals(new Grade("D").getColourCode(), "green");
        assertEquals(new Grade("F").getColourCode(), "red");
        assertEquals(new Grade("EXE").getColourCode(), "blue");
        assertEquals(new Grade("IC").getColourCode(), "orange");
        assertEquals(new Grade("IP").getColourCode(), "gray");
        assertEquals(new Grade("W").getColourCode(), "silver");
        assertEquals(new Grade("CS").getColourCode(), "green");
        assertEquals(new Grade("CU").getColourCode(), "red");
        assertEquals(new Grade("S").getColourCode(), "green");
        assertEquals(new Grade("U").getColourCode(), "red");
    }

    @Test
    public void equals() {
        Grade grade = new Grade("A");

        // same object -> returns true
        assertEquals(grade, grade);

        // same values -> returns true
        Grade gradeCopy = new Grade("A");
        assertEquals(grade, gradeCopy);

        // different types -> returns false
        assertNotEquals(grade, 1);

        // null -> returns false
        assertNotEquals(grade, null);

        // different grade -> returns false
        Grade differentGrade = new Grade("B");
        assertNotEquals(grade, differentGrade);
    }

    @Test
    public void hashCodeMethod() {
        Grade grade = new Grade("A");

        // same value
        Grade gradeCopy = new Grade("A");
        assertTrue(grade.hashCode() == gradeCopy.hashCode());

        // different value
        Grade differentGrade = new Grade("B");
        assertFalse(grade.hashCode() == differentGrade.hashCode());
    }
}
