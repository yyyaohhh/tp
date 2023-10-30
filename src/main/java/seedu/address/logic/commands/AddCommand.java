package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

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

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already has already been added.";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Database check
        if (!model.checkDbValidModule(toAdd)) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_MODULE_CODE, toAdd.getModuleCode()));
        }

        // Module plan duplicate check
        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        // Populating module details from database
        ModuleCode moduleCode = toAdd.getModuleCode();
        Module newModule = new Module(moduleCode,
                                      toAdd.getYearTaken(),
                                      toAdd.getSemesterTaken(),
                                      toAdd.getGrade(),
                                      model.getDbModuleName(moduleCode),
                                      model.getDbModuleDescription(moduleCode),
                                      model.getDbModularCredit(moduleCode));

        // Add to module plan and return success message
        model.addModule(newModule);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newModule)));
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
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
