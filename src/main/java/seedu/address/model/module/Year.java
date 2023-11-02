package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the year of a student when a Module is taken in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)}
 */
public class Year implements Comparable<Year> {

    public static final Year YEAR_0 = new Year("0");

    public static final String MESSAGE_CONSTRAINTS = "Year should only contain a number from 0 to 6.";
    public static final String VALIDATION_REGEX = "^[0-6]$";

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

    @Override
    public int compareTo(Year o) {
        return this.year - o.year;
    }
}
