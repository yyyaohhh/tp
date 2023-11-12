package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void isValidDescription_alwaysTrue() {
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // only whitespace
        assertTrue(Description.isValidDescription("This is a description"));
        assertTrue(Description.isValidDescription("12345"));
        assertTrue(Description.isValidDescription("!@#$%^&*()_+"));
    }

    @Test
    public void toStringMethod() {
        Description description = new Description("This is a description");
        assertTrue(description.toString().equals("This is a description"));
    }

    @Test
    public void equals() {
        Description description = new Description("This is a description");

        // same object -> returns true
        assertEquals(description, description);

        // same values -> returns true
        Description descriptionCopy = new Description("This is a description");
        assertEquals(description, descriptionCopy);

        // different types -> returns false
        assertNotEquals(description, 1);

        // null -> returns false
        assertNotEquals(description, null);

        // different description -> returns false
        Description differentDescription = new Description("This is a different description");
        assertNotEquals(description, differentDescription);
    }

    @Test
    public void hashCodeMethod() {
        Description description = new Description("This is a description");
        assertTrue(description.hashCode() == "This is a description".hashCode());

        // same value
        Description descriptionCopy = new Description("This is a description");
        assertTrue(description.hashCode() == descriptionCopy.hashCode());

        // different value
        Description differentDescription = new Description("This is a different description");
        assertFalse(description.hashCode() == differentDescription.hashCode());
    }
}
