package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Deletes a module identified using it's module code.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module code. \n"
            + "Parameters: " + "code \n"
            + "Example: " + COMMAND_WORD + " " + "CS2103T ";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final ModuleCode toDelete;

    public DeleteCommand(ModuleCode toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Database check
        if (!model.checkDbValidModuleCode(toDelete)) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_MODULE_CODE, toDelete));
        }

        // Retrieve module from module plan
        Module targetMod;
        try {
            targetMod = model.getModule(toDelete);
        } catch (ModuleNotFoundException e) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_MODULE_NOT_FOUND, toDelete, COMMAND_WORD));
        }

        // Delete module from module plan and return success message
        model.deleteModule(targetMod);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, Messages.format(targetMod)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return toDelete.equals(otherDeleteCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}
