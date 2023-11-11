package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE);


        ModuleCode moduleCode;
        Year year = null;
        Semester semester = null;
        Grade grade = null;
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE);
        if (!argMultimap.getPreamble().isEmpty()) {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getPreamble());
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE),
                new ParseException(MESSAGE_INVALID_COMMAND_FORMAT));
        }

        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        }
        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
        }
        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get());
        }

        if (year == null || semester == null || grade == null) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE),
                new ParseException(MESSAGE_INVALID_COMMAND_FORMAT));
        }

        return new AddCommand(moduleCode, year, semester, grade);
    }

}
