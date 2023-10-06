package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Semester {
    public static final String MESSAGE_CONSTRAINTS = "Semester should only be 1 or 2";

    public static final String VALIDATION_REGEX = "^[12]{1}$";

    public final Integer semester;

    /**
     * Constructs a {@code Semester}.
     *
     * @param semester A valid Semester.
     */
    public Semester(String semester) {
        requireNonNull(semester);
        checkArgument(isValidSemester(semester), MESSAGE_CONSTRAINTS);
        this.semester = Integer.parseInt(semester);
    }

    /**
     * Returns true if a given string is a valid year.
     */
    public static boolean isValidSemester(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return semester.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Semester)) {
            return false;
        }

        Semester otherName = (Semester) other;
        return semester.equals(otherName.semester);
    }

    @Override
    public int hashCode() {
        return semester.hashCode();
    }
}
