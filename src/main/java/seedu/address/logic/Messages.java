package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.module.Module;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "No such module exists: %1$s";
    public static final String MESSAGE_MODULE_NOT_FOUND = "%1$s has not been added, unable to %2$s.";
    public static final String MESSAGE_MODULES_LISTED_OVERVIEW = "%1$d modules listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code module} for display to the user.
     */
    public static String format(Module module) {
        String builder = (module.getModuleCode())
                + "; Semester: "
                + "Y"
                + module.getYearTaken()
                + " "
                + module.getSemesterTaken()
                + "; Grade: "
                + module.getGrade();
        return builder;
    }
}
