package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Adds a module to the module plan.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to the module plan. \n"
            + "Parameters: "
            + "Code "
            + PREFIX_YEAR + "Year "
            + PREFIX_SEMESTER + "Semester "
            + PREFIX_GRADE + "Grade \n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2103T "
            + PREFIX_YEAR + "2 "
            + PREFIX_SEMESTER + "1 "
            + PREFIX_GRADE + "B ";

    public static final String MESSAGE_ADD_MODULE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "%1$s has already been added.";

    private final ModuleCode moduleCode;
    private final Year year;
    private final Semester semester;
    private final Grade grade;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddCommand(ModuleCode moduleCode, Year year, Semester semester, Grade grade) {
        requireAllNonNull(moduleCode, year, semester, grade);
        this.moduleCode = moduleCode;
        this.year = year;
        this.semester = semester;
        this.grade = grade;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Retieve module from database
        Module newModule;
        try {
            newModule = model.getModuleFromDb(moduleCode);
        } catch (ModuleNotFoundException mnfe) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_CODE, moduleCode));
        }

        // Add module with user inputs to module plan
        Module moduleToAdd;
        try {
            moduleToAdd = newModule.fillUserInputs(year, semester, grade);
            model.addModule(moduleToAdd);
        } catch (DuplicateModuleException dme) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE, moduleCode));
        }
        return new CommandResult(String.format(MESSAGE_ADD_MODULE_SUCCESS, Messages.format(moduleToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return this.moduleCode.equals(otherAddCommand.moduleCode)
                && this.year.equals(otherAddCommand.year)
                && this.semester.equals(otherAddCommand.semester)
                && this.grade.equals(otherAddCommand.grade);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("moduleCode", moduleCode)
                .add("year", year)
                .add("semester", semester)
                .add("grade", grade)
                .toString();
    }
}
