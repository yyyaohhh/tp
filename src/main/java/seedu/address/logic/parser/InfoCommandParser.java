package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.InfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new InfoCommmand object
 */
public class InfoCommandParser implements Parser<InfoCommand> {

    // Probably should be defined elsewhere
    // Pattern to check for single argument (no spaces)
    private static final Pattern SINGLE_ARGUMENT_FORMAT = Pattern.compile("^[^\\s]+$");

    /**
     * Parses the given {@code String} of arguments in the context of the InfoCommand
     * and returns a InfoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InfoCommand parse(String args) throws ParseException {

        Matcher matcher = SINGLE_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InfoCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(args);
        return new InfoCommand(moduleCode);
    }
}
