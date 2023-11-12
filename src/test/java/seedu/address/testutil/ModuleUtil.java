package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;


/**
 * A utility class for Module.
 */
public class ModuleUtil {
    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddCommand(Module module) {
        return AddCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        String sb = module.getModuleCode().toString()
                + " " + PREFIX_YEAR + module.getYearTaken().year.toString()
                + " " + PREFIX_SEMESTER + module.getSemesterTaken().semester.getSemester()
                + " " + PREFIX_GRADE + module.getGrade().toString() + " ";
        return sb;
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

    public static Module clearUserInputFields(Module m) {
        return new Module(m.getModuleCode(), m.getName(), m.getDescription(), m.getModularCredit());
    }

    public static Year getAltYear(Year year) {
        return year.equals(new Year("1")) ? new Year("2") : new Year("1");
    }

    public static Semester getAltSemester(Semester semester) {
        return semester.equals(new Semester("1")) ? new Semester("2") : new Semester("1");
    }

    public static Grade getAltGrade(Grade grade) {
        return grade.equals(new Grade("A")) ? new Grade("B") : new Grade("A");
    }
}
