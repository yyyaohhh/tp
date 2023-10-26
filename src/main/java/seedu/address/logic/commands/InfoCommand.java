package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

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
            + "MCs: %3$s \n"
            + "%4$s \n";

    public static final String MESSAGE_NOT_FOUND_MODULE = "No such module: %1$s";

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

        String info;
        try {
            String name = model.getModuleName(moduleCode).toString();
            String mc = model.getModularCredit(moduleCode).toString();
            String description = model.getModuleDescription(moduleCode).toString();
            info = String.format(MESSAGE_INFO_MODULE_SUCCESS, moduleCode, name, mc, description);
        } catch (NoSuchElementException e) {
            throw new CommandException(String.format(MESSAGE_NOT_FOUND_MODULE, moduleCode));
        }

        return new CommandResult(info);
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
