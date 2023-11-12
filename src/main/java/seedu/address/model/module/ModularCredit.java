package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of Modular Credits a Module is worth.
 */
public class ModularCredit {
    public static final String MESSAGE_CONSTRAINTS = "Modular credits should be a non-negative integer.";

    public static final String VALIDATION_REGEX = "^[\\p{Digit}]*[.]?[\\p{Digit}]+$";

    private final float modularCredit;

    /**
     * Constructs a {@code ModularCredit}.
     *
     * @param modularCredit A valid modular credit.
     */
    public ModularCredit(String modularCredit) {
        requireNonNull(modularCredit);
        checkArgument(isValidModularCredit(modularCredit), MESSAGE_CONSTRAINTS);
        this.modularCredit = Float.parseFloat(modularCredit);
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidModularCredit(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public float getValue() {
        return modularCredit;
    }

    @Override
    public String toString() {
        return String.valueOf(modularCredit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModularCredit)) {
            return false;
        }

        ModularCredit otherModularCredit = (ModularCredit) other;
        return modularCredit == otherModularCredit.modularCredit;
    }

    @Override
    public int hashCode() {
        return ((Float) modularCredit).hashCode();
    }
}
