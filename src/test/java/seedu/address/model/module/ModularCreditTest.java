package seedu.address.model.module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class ModularCreditTest {
    @Test
    public void constructor_invalidModularCredit_throwsIllegalArgumentException() {
        String invalidModularCreditEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> new ModularCredit(invalidModularCreditEmpty));

        String invalidModularCreditLetters = "one";
        assertThrows(IllegalArgumentException.class, () -> new ModularCredit(invalidModularCreditLetters));
    }

    @Test
    public void isValidModularCredit() {
        // null modular credit
        assertThrows(NullPointerException.class, () -> ModularCredit.isValidModularCredit(null));

        // invalid modular credit
        assertFalse(ModularCredit.isValidModularCredit("")); // empty string
        assertFalse(ModularCredit.isValidModularCredit(" ")); // spaces only
        assertFalse(ModularCredit.isValidModularCredit("-1")); // negative
        assertFalse(ModularCredit.isValidModularCredit("1..2")); // double decimal
        assertFalse(ModularCredit.isValidModularCredit("one")); // letters

        // valid modular credit
        assertTrue(ModularCredit.isValidModularCredit(".5"));
        assertTrue(ModularCredit.isValidModularCredit("1."));
        assertTrue(ModularCredit.isValidModularCredit("3.5"));
        assertTrue(ModularCredit.isValidModularCredit("2"));
        assertTrue(ModularCredit.isValidModularCredit("0"));
    }
}
