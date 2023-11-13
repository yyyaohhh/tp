package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    public static final String MESSAGE_INVALID_MODULE_CODE = "Module code format invalid.";

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE);

        ModuleCode moduleCode;
        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();

        try {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editModuleDescriptor.setYear(ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get()));
        }
        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            editModuleDescriptor.setSemester(ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }
        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            editModuleDescriptor.setGrade(ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get()));
        }

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(moduleCode, editModuleDescriptor);
    }

}
