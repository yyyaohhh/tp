package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidYear_throwsIllegalArgumentException() {
        // Invalid strings
        assertThrows(IllegalArgumentException.class, () -> new Year(""));
        assertThrows(IllegalArgumentException.class, () -> new Year(" "));
        assertThrows(IllegalArgumentException.class, () -> new Year("one"));

        // Invalid number strings
        assertThrows(IllegalArgumentException.class, () -> new Year("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Year("1.0"));
        assertThrows(IllegalArgumentException.class, () -> new Year("2.5"));
        assertThrows(IllegalArgumentException.class, () -> new Year("7"));
    }

    @Test
    public void isValidYear_invalidYear_returnsFalse() {
        // Null string
        assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // Invalid strings
        assertFalse(Year.isValidYear(""));
        assertFalse(Year.isValidYear(" "));
        assertFalse(Year.isValidYear("one"));

        // Invalid number strings
        assertFalse(Year.isValidYear("-1"));
        assertFalse(Year.isValidYear("1.0"));
        assertFalse(Year.isValidYear("2.5"));
        assertFalse(Year.isValidYear("7"));
    }

    @Test
    public void isValidYear_validYear_returnsTrue() {
        // Valid number strings
        assertTrue(Year.isValidYear("0"));
        assertTrue(Year.isValidYear("1"));
        assertTrue(Year.isValidYear("2"));
        assertTrue(Year.isValidYear("3"));
        assertTrue(Year.isValidYear("4"));
        assertTrue(Year.isValidYear("5"));
        assertTrue(Year.isValidYear("6"));
    }

    @Test
    public void toStringMethod() {
        Year year = new Year("1");
        assertTrue(year.toString().equals("1"));
    }

    @Test
    public void equals() {
        Year year = new Year("1");

        // same object -> returns true
        assertTrue(year.equals(year));

        // same values -> returns true
        Year yearCopy = new Year("1");
        assertTrue(year.equals(yearCopy));

        // different values -> returns false
        Year differentYear = new Year("2");
        assertFalse(year.equals(differentYear));

        // different types -> returns false
        assertFalse(year.equals(1));

        // null -> returns false
        assertFalse(year.equals(null));
    }

    @Test
    public void hashCodeMethod() {
        Year year = new Year("1");
        assertTrue(year.hashCode() == 1);

        // same values -> returns same hashcode
        Year yearCopy = new Year("1");
        assertTrue(year.hashCode() == yearCopy.hashCode());

        Year differentYear = new Year("2");
        assertFalse(year.hashCode() == differentYear.hashCode());
    }

    @Test
    public void compareToMethod() {
        Year year = new Year("1");

        // same object
        assertTrue(year.compareTo(year) == 0);

        // same values
        Year yearCopy = new Year("1");
        assertTrue(year.compareTo(yearCopy) == 0);

        // different values
        Year differentYear = new Year("2");
        assertTrue(year.compareTo(differentYear) < 0);
        assertTrue(differentYear.compareTo(year) > 0);
    }
}
