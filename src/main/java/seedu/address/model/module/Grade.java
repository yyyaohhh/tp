package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a student's grade in a Module in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {
    // We define an enum inside a class to allow error messages to be abstracted out.
    /**
     * An enum to represent possible grades.
     */
    public enum GradeEnum {
        // Based on https://www.nus.edu.sg/registrar/academic-information-policies/undergraduate-students/modular-system
        A_PLUS("A+", 5.0f),
        A("A", 5.0f),
        A_MINUS("A-", 4.5f),
        B_PLUS("B+", 4.0f),
        B("B", 3.5f),
        B_MINUS("B-", 3.0f),
        C_PLUS("C+", 2.5f),
        C("C", 2.0f),
        D_PLUS("D+", 1.5f),
        D("D", 1.0f),
        F("F", 0.0f),
        EXEMPTED("EXE", null),
        INCOMPLETE("IC", null),
        IN_PROGRESS("IP", null),
        WITHDRAWN("W", null);

        private final String grade;
        private final Float gradePoint;

        GradeEnum(String grade, Float gradePoint) {
            this.grade = grade;
            this.gradePoint = gradePoint;
        }

        /**
         * Converts a {@code String} to {@code GradeEnum}
         * @param grade The {@code String} representation of a grade.
         * @return The corresponding {@code GradeEnum}
         */
        public static GradeEnum fromString(String grade) {
            for (GradeEnum gradeEnum: GradeEnum.values()) {
                if (grade.equals(gradeEnum.grade)) {
                    return gradeEnum;
                }
            }
            return null;
        }

        public String getGrade() {
            return this.grade;
        }
    }

    public static final String MESSAGE_CONSTRAINTS = "Grade should only be the following: "
            + String.join(", ",
            Arrays.stream(GradeEnum.values()).map(GradeEnum::getGrade)
            .collect(Collectors.toList()));

    private final GradeEnum grade;

    /**
     * Constructs a {@code Lecturer}.
     *
     * @param grade A valid lecturer's name.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);
        this.grade = GradeEnum.fromString(grade);
    }

    /**
     * Returns true if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        return GradeEnum.fromString(test) != null;
    }

    @Override
    public String toString() {
        return grade.getGrade();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        return grade.equals(otherGrade.grade);
    }

    @Override
    public int hashCode() {
        return grade.getGrade().hashCode();
    }
}
