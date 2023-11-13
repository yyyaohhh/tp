package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {
    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCodeEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCodeEmpty));

        String invalidModuleCodeLower = "cs2040s";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCodeLower));
    }

    @Test
    public void isValidModuleCode_invalid() {
        // null module code
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid module codes
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("CS50")); // 2 digits
        assertFalse(ModuleCode.isValidModuleCode("C2103T")); // prefix too short
        assertFalse(ModuleCode.isValidModuleCode("C2103TTT")); // 3 postfix
        assertFalse(ModuleCode.isValidModuleCode("CSCSC2103T")); // 5 prefix
    }

    @Test
    public void isValidModuleCode_valid() {
        // valid module codes
        assertTrue(ModuleCode.isValidModuleCode("MA1521"));
        assertTrue(ModuleCode.isValidModuleCode("CS2103T"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000N"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000"));
        assertTrue(ModuleCode.isValidModuleCode("LLJ5094CV"));
        assertTrue(ModuleCode.isValidModuleCode("GESS1025"));
    }

    @Test
    public void equals() {
        ModuleCode moduleCode = new ModuleCode("CS2103T");

        // same object -> returns true
        assertEquals(moduleCode, moduleCode);

        // same values -> returns true
        ModuleCode moduleCodeCopy = new ModuleCode("CS2103T");
        assertEquals(moduleCode, moduleCodeCopy);

        // different types -> returns false
        assertNotEquals(moduleCode, 1);

        // null -> returns false
        assertNotEquals(moduleCode, null);

        // different module code -> returns false
        ModuleCode differentModuleCode = new ModuleCode("CS2101");
        assertNotEquals(moduleCode, differentModuleCode);
    }

    @Test
    public void hashCodeMethod() {
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        assertTrue(moduleCode.hashCode() == "CS2103T".hashCode());

        // same values -> returns same hashcode
        ModuleCode moduleCodeCopy = new ModuleCode("CS2103T");
        assertTrue(moduleCode.hashCode() == moduleCodeCopy.hashCode());

        // different module code -> returns different hashcode
        ModuleCode differentModuleCode = new ModuleCode("CS2101");
        assertFalse(moduleCode.hashCode() == differentModuleCode.hashCode());
    }
}
