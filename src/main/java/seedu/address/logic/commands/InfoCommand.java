package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;

/**
 * Displays information of a module identified using it's module code.
 */
public class InfoCommand extends Command {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows information on the module identified by the module code. \n"
            + "Parameters: " + "code \n"
            + "Example: " + COMMAND_WORD + " " + "CS1101S ";
    public static final String MESSAGE_INFO_MODULE_SUCCESS = "%1$s: %2$s \n"
            + "%3$s \n"
            + "%4$s \n";
    public static final String MESSAGE_INFO_MODULE_SUCCESS_SAMPLE = "CS1101S : Programming Methodology \n"
            + "4MCs \n"
            + "This course introduces the concepts of programming and computational problem solving. "
            + "Starting from a small core of fundamental abstractions, the course introduces"
            + " programming as a method for communicating computational processes. \n";

    public static final String MESSAGE_NOT_FOUND_MODULE = "This module is not found.";

    private final ModuleCode moduleCode;

    /**
     * @param moduleCode of the module to be shown
     */
    public InfoCommand(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // TODO: Call a method in model to get the module information

        return new CommandResult(MESSAGE_INFO_MODULE_SUCCESS_SAMPLE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InfoCommand)) {
            return false;
        }

        InfoCommand otherInfoCommand = (InfoCommand) other;
        return moduleCode.equals(otherInfoCommand.moduleCode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("moduleCode", moduleCode)
                .toString();
    }
}
