package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a lecturer in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidLecturer(String)}
 */
public class Lecturer {

    public static final String MESSAGE_CONSTRAINTS =
            "Lecturer names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the lecturer's name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String lecturerName;

    /**
     * Constructs a {@code Lecturer}.
     *
     * @param name A valid lecturer's name.
     */
    public Lecturer(String name) {
        requireNonNull(name);
        checkArgument(isValidLecturer(name), MESSAGE_CONSTRAINTS);
        lecturerName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidLecturer(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return lecturerName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lecturer)) {
            return false;
        }

        Lecturer otherName = (Lecturer) other;
        return lecturerName.equals(otherName.lecturerName);
    }

    @Override
    public int hashCode() {
        return lecturerName.hashCode();
    }

}
