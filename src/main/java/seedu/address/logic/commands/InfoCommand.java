package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_CODE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Displays information of a module identified using it's module code.
 */
public class InfoCommand extends Command {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Shows information on the module identified by the module code. \n"
        + "Parameters: " + "code \n"
        + "Example: " + COMMAND_WORD + " " + "CS1101S ";

    private final ModuleCode moduleCode;

    /**
     * Creates an InfoCommand to show information of the specified module.
     *
     * @param moduleCode of the module to be shown
     */
    public InfoCommand(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module module;
        try {
            module = model.getModuleFromDb(moduleCode);
        } catch (ModuleNotFoundException mnfe) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_CODE, moduleCode));
        }

        // Return success message
        return new CommandResult(module.toInfoString());
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
