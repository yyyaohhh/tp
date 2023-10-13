package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module code. "
            + "Parameters: " + PREFIX_CODE + "code "
            + "Example: " + COMMAND_WORD + " " + PREFIX_CODE + "CS2103T ";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_NOT_FOUND_MODULE = "This module is not found and has not been added.";

    private final ModuleCode toDelete;

    public DeleteCommand(ModuleCode toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module targetMod = model.findModuleUsingCode(toDelete);

        if (!model.hasModule(targetMod)) {
            throw new CommandException(MESSAGE_NOT_FOUND_MODULE);
        }

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
