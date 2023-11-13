package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    private UserPrefs userPrefs;

    @BeforeEach
    public void setUp() {
        userPrefs = new UserPrefs();
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPrefs.resetData(null));
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPrefs.setGuiSettings(null));
    }

    @Test
    public void setModulePlanFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userPrefs.setModulePlanFilePath(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        UserPrefs copy = new UserPrefs(userPrefs);
        assertTrue(userPrefs.equals(copy));

        // same object -> returns true
        assertTrue(userPrefs.equals(userPrefs));

        // null -> returns false
        assertFalse(userPrefs.equals(null));

        // different types -> returns false
        assertFalse(userPrefs.equals(0));

        // different gui settings -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(1, 1, 1, 1));
        assertFalse(userPrefs.equals(differentUserPrefs));

        // different module plan file path -> returns false
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModulePlanFilePath(Paths.get("differentFilePath"));
        assertFalse(userPrefs.equals(differentUserPrefs));
    }

    @Test
    public void hashCodeTest() {
        // same values
        UserPrefs copy = new UserPrefs(userPrefs);
        assertTrue(userPrefs.hashCode() == copy.hashCode());

        // different gui settings
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(1, 1, 1, 1));
        assertFalse(userPrefs.hashCode() == differentUserPrefs.hashCode());

        // different module plan file path
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModulePlanFilePath(Paths.get("differentFilePath"));
        assertFalse(userPrefs.hashCode() == differentUserPrefs.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expectedString = "Gui Settings : " + userPrefs.getGuiSettings()
                + "\nLocal data file location : " + userPrefs.getModulePlanFilePath();
        assertTrue(userPrefs.toString().equals(expectedString));
    }
}
