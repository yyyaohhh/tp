package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Year {
    public static final String MESSAGE_CONSTRAINTS = "Year should only contain 4 digits.";

    public static final String VALIDATION_REGEX = "^[\\p{Digit}]{4}$";

    public final Integer year;

    /**
     * Constructs a {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_CONSTRAINTS);
        this.year = Integer.parseInt(year);
    }

    /**
     * Returns true if a given string is a valid year.
     */
    public static boolean isValidYear(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return year.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Year)) {
            return false;
        }

        Year otherName = (Year) other;
        return year.equals(otherName.year);
    }

    @Override
    public int hashCode() {
        return year.hashCode();
    }
}
