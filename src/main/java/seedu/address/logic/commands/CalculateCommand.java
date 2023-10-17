package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FUNCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FUNCTIONS;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class CalculateCommand extends Command {

    public static final String COMMAND_WORD = "calculate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates the CAP or MC (Modular Credits). "
            + "Parameters: " + PREFIX_FUNCTION + "function "
            + "Example: " + COMMAND_WORD + " " + PREFIX_FUNCTION + "CAP ";

    public static final String MESSAGE_CALCULATION_SUCCESS = "Calculated value: %1$s";
    public static final String MESSAGE_NOT_FOUND_MODULE = "There are no modules so no calculation is done.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        float calculatedValue;

        List<Module> lastShownList = model.getFilteredModuleList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_NOT_FOUND_MODULE);
        }

        model.deleteModule(targetMod);
        return new CommandResult(String.format(MESSAGE_CALCULATION_SUCCESS, Messages.format(targetMod)));
    }
}