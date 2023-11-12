package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents the semester when a Module is taken in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidSemester(String)}
 */
public class Semester implements Comparable<Semester> {

    public static final String MESSAGE_CONSTRAINTS = "Semester should only be the following: "
            + String.join(", ",
            Arrays.stream(SemesterEnum.values()).map(SemesterEnum::getSemester)
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
        this.semester = SemesterEnum.fromString(semester);
    }

    /**
     * Returns true if a given string is a valid semester.
     */
    public static boolean isValidSemester(String test) {
        return SemesterEnum.fromString(test) != null;
    }

    public String getSemesterString() {
        return semester.getSemester();
    }

    @Override
    public String toString() {
        if (semester.getSemester().matches("^[\\p{Digit}]+$")) {
            return "S" + semester.getSemester();
        }
        return semester.getSemester();
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

    @Override
    public int compareTo(Semester o) {
        return this.semester.compareTo(o.semester);
    }

    /**
     * An enum to represent possible semesters.
     */
    public enum SemesterEnum {
        SEMESTER_1("1"),
        SEMESTER_2("2"),
        SPECIAL_TERM_1("ST1"),
        SPECIAL_TERM_2("ST2");

        private final String semester;

        SemesterEnum(String semester) {
            this.semester = semester;
        }

        /**
         * Converts a {@code String} to {@code SemesterEnum}
         *
         * @param semester The {@code String} representation of a semester.
         * @return The corresponding {@code SemesterEnum}
         */
        public static SemesterEnum fromString(String semester) {
            for (SemesterEnum semesterEnum : SemesterEnum.values()) {
                if (semester.equals(semesterEnum.semester)) {
                    return semesterEnum;
                }
            }
            return null;
        }

        public String getSemester() {
            return this.semester;
        }
    }
}
