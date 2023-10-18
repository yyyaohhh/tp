package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Calculates the user's CAP from each module's grade.
 */
public class CalculateCapCommand extends Command {

    public static final String COMMAND_WORD = "calculateCAP";
    public static final String MESSAGE_CALCULATION_SUCCESS = "Calculated value: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int modularCredits = model.totalModularCredits();

        Float gradePointsByUnits = model.totalGradePointsByUnits();

        Float calculatedCapValue = gradePointsByUnits / modularCredits;

        return new CommandResult(String.format(MESSAGE_CALCULATION_SUCCESS, calculatedCapValue));
    }
}
