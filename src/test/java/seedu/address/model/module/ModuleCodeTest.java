package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertFalse(ModuleCode.isValidModuleCode("CSCSC2103T")); // 5 postfix
    }

    @Test
    public void isValidModuleCode_valid() {

        // valid module codes
        assertTrue(ModuleCode.isValidModuleCode("CS2103T"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000N"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000"));
        assertTrue(ModuleCode.isValidModuleCode("LLJ5094CV"));
        assertTrue(ModuleCode.isValidModuleCode("GESS1025"));

    }
}
