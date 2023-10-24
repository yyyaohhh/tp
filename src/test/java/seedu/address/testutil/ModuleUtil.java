package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.model.module.Module;


/**
 * A utility class for Module.
 */
public class ModuleUtil {
    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Module module) {
        return AddCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(module.getModuleCode().toString() + " ");
        sb.append(PREFIX_YEAR + module.getYearTaken().year.toString() + " ");
        sb.append(PREFIX_SEMESTER + module.getSemesterTaken().semester.getSemester() + " ");
        sb.append(PREFIX_GRADE + module.getGrade().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditModuleDescriptor}'s details.
     */
    public static String getEditModuleDescriptorDetails(EditModuleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getYear().ifPresent(year -> sb.append(PREFIX_YEAR).append(year).append(" "));
        descriptor.getSemester().ifPresent(
                semester -> sb.append(PREFIX_SEMESTER).append(semester.getSemesterString()).append(" "));
        descriptor.getGrade().ifPresent(grade -> sb.append(PREFIX_GRADE).append(grade).append(" "));

        return sb.toString();
    }
}
