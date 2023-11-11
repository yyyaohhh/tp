package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Calculates the user's total modular credits from their modules taken.
 */
public class CalculateMcCommand extends Command {

    public static final String COMMAND_WORD = "calculateMC";
    public static final String MESSAGE_CALCULATION_SUCCESS = "Total Modular Credits: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        float modularCredits = model.totalModularCredits();

        return new CommandResult(String.format(MESSAGE_CALCULATION_SUCCESS, modularCredits));
    }
}
