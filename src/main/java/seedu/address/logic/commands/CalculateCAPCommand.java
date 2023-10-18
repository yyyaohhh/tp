package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class CalculateCAPCommand extends Command {

    public static final String COMMAND_WORD = "calculateCAP";
    public static final String MESSAGE_CALCULATION_SUCCESS = "Calculated value: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int modularCredits = model.totalModularCredits();

        Float gradePointsByUnits  = model.totalGradePointsByUnits();

        Float calculatedCAPValue = gradePointsByUnits / modularCredits;

        return new CommandResult(String.format(MESSAGE_CALCULATION_SUCCESS, calculatedCAPValue));
    }
}