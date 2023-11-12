package seedu.address.model.moduleplan.exceptions;

/**
 * Signals that the operation will result in duplicate ModulePlanSemester.
 */
public class DuplicateSemesterException extends RuntimeException {
    public DuplicateSemesterException() {
        super("Operation would result in duplicate semesters");
    }
}
