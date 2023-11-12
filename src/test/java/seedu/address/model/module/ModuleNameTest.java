package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ModuleNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleName(null));
    }

    @Test
    public void isValidName_alwaysTrue() {
        assertTrue(ModuleName.isValidName("")); // empty string
        assertTrue(ModuleName.isValidName(" ")); // only whitespace
        assertTrue(ModuleName.isValidName("Software Engineering"));
        assertTrue(ModuleName.isValidName("12345"));
        assertTrue(ModuleName.isValidName("!@#$%^&*()_+"));
    }

    @Test
    public void toStringMethod() {
        ModuleName moduleName = new ModuleName("Software Engineering");
        assertTrue(moduleName.toString().equals("Software Engineering"));
    }

    @Test
    public void equals() {
        ModuleName moduleName = new ModuleName("Software Engineering");

        // same object -> returns true
        assertTrue(moduleName.equals(moduleName));

        // same values -> returns true
        ModuleName moduleNameCopy = new ModuleName("Software Engineering");
        assertTrue(moduleName.equals(moduleNameCopy));

        // different types -> returns false
        assertFalse(moduleName.equals(1));

        // null -> returns false
        assertFalse(moduleName.equals(null));

        // different description -> returns false
        ModuleName differentModuleName = new ModuleName("Effective Communication for Computing Professionals");
        assertFalse(moduleName.equals(differentModuleName));
    }

    @Test
    public void hashCodeMethod() {
        ModuleName moduleName = new ModuleName("Software Engineering");
        assertTrue(moduleName.hashCode() == "Software Engineering".hashCode());

        ModuleName moduleNameCopy = new ModuleName("Software Engineering");
        assertTrue(moduleName.hashCode() == moduleNameCopy.hashCode());

        ModuleName differentModuleName = new ModuleName("Effective Communication for Computing Professionals");
        assertFalse(moduleName.hashCode() == differentModuleName.hashCode());
    }
}
