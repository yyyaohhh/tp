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

    public static final String MESSAGE_CONSTRAINTS = "Grade should only be the following: "
            + String.join(", ",
            Arrays.stream(GradeEnum.values()).map(GradeEnum::getGrade)
                    .collect(Collectors.toList()));
    private final GradeEnum grade;

    /**
     * Constructs a {@code Grade}.
     *
     * @param grade A valid grade.
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

    public Float gradePoint() {
        return grade.getGradePoint();
    }

    public String getColourCode() {
        return grade.getColourCode();
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
        return grade.hashCode();
    }

    /**
     * An enum to represent possible grades.
     */
    public enum GradeEnum {
        // Based on the following:
        // https://www.nus.edu.sg/registrar/academic-information-policies/undergraduate-students/
        // modular-system
        // https://www.nus.edu.sg/registrar/academic-information-policies/undergraduate-students/
        // continuation-and-graduation-requirements
        A_PLUS("A+", 5.0f, "green"),
        A("A", 5.0f, "green"),
        A_MINUS("A-", 4.5f, "green"),
        B_PLUS("B+", 4.0f, "green"),
        B("B", 3.5f, "green"),
        B_MINUS("B-", 3.0f, "green"),
        C_PLUS("C+", 2.5f, "green"),
        C("C", 2.0f, "green"),
        D_PLUS("D+", 1.5f, "green"),
        D("D", 1.0f, "green"),
        F("F", 0.0f, "red"),
        EXEMPTED("EXE", null, "blue"),
        INCOMPLETE("IC", null, "orange"),
        IN_PROGRESS("IP", null, "gray"),
        WITHDRAWN("W", null, "silver"),
        COMPLETED_SATISFACTORY("CS", null, "green"),
        COMPLETED_UNSATISFACTORY("CU", null, "red"),
        SATISFACTORY("S", null, "green"),
        UNSATISFACTORY("U", null, "red");

        private final String grade;
        private final Float gradePoint;
        private final String colourCode;

        GradeEnum(String grade, Float gradePoint, String colourCode) {
            this.grade = grade;
            this.gradePoint = gradePoint;
            this.colourCode = colourCode;
        }

        /**
         * Converts a {@code String} to {@code GradeEnum}
         *
         * @param grade The {@code String} representation of a grade.
         * @return The corresponding {@code GradeEnum}
         */
        public static GradeEnum fromString(String grade) {
            for (GradeEnum gradeEnum : GradeEnum.values()) {
                if (grade.equals(gradeEnum.grade)) {
                    return gradeEnum;
                }
            }
            return null;
        }

        public String getGrade() {
            return this.grade;
        }

        public Float getGradePoint() {
            return this.gradePoint;
        }

        public String getColourCode() {
            return this.colourCode;
        }
    }
}
