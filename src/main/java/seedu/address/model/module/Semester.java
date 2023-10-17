package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents the semester when a Module is taken in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidSemester(String)}
 */
public class Semester {
    /**
     * An enum to represent possible semesters.
     */
    public enum SemesterEnum {
        SEMESTER_1,
        SEMESTER_2,
        SPECIAL_TERM_1,
        SPECIAL_TERM_2;
    }

    public static final String MESSAGE_CONSTRAINTS = "Semester should only be the following: "
            + String.join(", ",
            Arrays.stream(SemesterEnum.values()).map(SemesterEnum::toString)
            .collect(Collectors.toList()));

    public final SemesterEnum semester;

    /**
     * Constructs a {@code Semester}.
     *
     * @param semester A valid Semester.
     */
    public Semester(String semester) {
        requireNonNull(semester);
        checkArgument(isValidSemester(semester), MESSAGE_CONSTRAINTS);
        this.semester = SemesterEnum.valueOf(semester);
    }

    /**
     * Returns true if a given string is a valid semester.
     */
    public static boolean isValidSemester(String test) {
        try {
            SemesterEnum.valueOf(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
