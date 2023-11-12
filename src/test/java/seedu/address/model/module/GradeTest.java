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

    @Test
    public void getColourCode() {
        assertTrue(new Grade("A+").getColourCode().equals("green"));
        assertTrue(new Grade("A").getColourCode().equals("green"));
        assertTrue(new Grade("A-").getColourCode().equals("green"));
        assertTrue(new Grade("B+").getColourCode().equals("green"));
        assertTrue(new Grade("B").getColourCode().equals("green"));
        assertTrue(new Grade("B-").getColourCode().equals("green"));
        assertTrue(new Grade("C+").getColourCode().equals("green"));
        assertTrue(new Grade("C").getColourCode().equals("green"));
        assertTrue(new Grade("D+").getColourCode().equals("green"));
        assertTrue(new Grade("D").getColourCode().equals("green"));
        assertTrue(new Grade("F").getColourCode().equals("red"));
        assertTrue(new Grade("EXE").getColourCode().equals("blue"));
        assertTrue(new Grade("IC").getColourCode().equals("orange"));
        assertTrue(new Grade("IP").getColourCode().equals("gray"));
        assertTrue(new Grade("W").getColourCode().equals("silver"));
        assertTrue(new Grade("CS").getColourCode().equals("green"));
        assertTrue(new Grade("CU").getColourCode().equals("red"));
        assertTrue(new Grade("S").getColourCode().equals("green"));
        assertTrue(new Grade("U").getColourCode().equals("red"));
    }

    @Test
    public void equals() {
        Grade grade = new Grade("A");

        // same object -> returns true
        assertTrue(grade.equals(grade));

        // same values -> returns true
        Grade gradeCopy = new Grade("A");
        assertTrue(grade.equals(gradeCopy));

        // different types -> returns false
        assertFalse(grade.equals(1));

        // null -> returns false
        assertFalse(grade.equals(null));

        // different grade -> returns false
        Grade gradeDifferent = new Grade("B");
        assertFalse(grade.equals(gradeDifferent));
    }
}
