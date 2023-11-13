package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_CS2101;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.CS2040S;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.testutil.ModuleBuilder;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(CS2040S).build();
        String moduleCodeString = expectedModule.getModuleCode().toString();

        AddCommand expectedAddCommand = new AddCommand(expectedModule.getModuleCode(), expectedModule.getYearTaken(),
                expectedModule.getSemesterTaken(), expectedModule.getGrade());

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + moduleCodeString + YEAR_DESC_CS2040S
                + SEMESTER_DESC_CS2040S + GRADE_DESC_CS2040S, expectedAddCommand);

        System.out.println(moduleCodeString + YEAR_DESC_CS2040S + SEMESTER_DESC_CS2040S
                + GRADE_DESC_CS2040S);

        // no whitespace preamble
        assertParseSuccess(parser, moduleCodeString + YEAR_DESC_CS2040S + SEMESTER_DESC_CS2040S
                + GRADE_DESC_CS2040S, expectedAddCommand);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedModuleString = VALID_CODE_CS2101
                + YEAR_DESC_CS2101 + SEMESTER_DESC_CS2101 + GRADE_DESC_CS2101;

        // multiple years
        assertParseFailure(parser, YEAR_DESC_CS2101 + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR));

        // multiple semesters
        assertParseFailure(parser, SEMESTER_DESC_CS2101 + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEMESTER));

        // multiple grades
        assertParseFailure(parser, GRADE_DESC_CS2101 + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        // multiple fields repeated
        assertParseFailure(parser,
                YEAR_DESC_CS2040S + SEMESTER_DESC_CS2040S + GRADE_DESC_CS2040S
                        + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE));

        // invalid value followed by valid value

        // invalid year
        assertParseFailure(parser, INVALID_YEAR_DESC + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR));

        // invalid semester
        assertParseFailure(parser, INVALID_SEMESTER_DESC + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEMESTER));

        // invalid grade
        assertParseFailure(parser, INVALID_GRADE_DESC + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        // valid value followed by invalid value

        // invalid year
        assertParseFailure(parser, validExpectedModuleString + INVALID_YEAR_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR));

        // invalid semester
        assertParseFailure(parser, validExpectedModuleString + INVALID_SEMESTER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEMESTER));

        // invalid grade
        assertParseFailure(parser, validExpectedModuleString + INVALID_GRADE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing year prefix
        assertParseFailure(parser,
                VALID_CODE_CS2101 + SEMESTER_DESC_CS2101 + " " + VALID_YEAR_CS2101 + GRADE_DESC_CS2101,
                expectedMessage);

        // missing semester prefix
        assertParseFailure(parser,
                VALID_CODE_CS2101 + YEAR_DESC_CS2101 + VALID_SEMESTER_CS2101 + GRADE_DESC_CS2101,
                expectedMessage);

        // missing grade prefix
        assertParseFailure(parser,
                VALID_CODE_CS2101 + YEAR_DESC_CS2101 + SEMESTER_DESC_CS2101 + VALID_GRADE_CS2101,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_CODE_CS2101 + VALID_YEAR_CS2101 + VALID_SEMESTER_CS2101 + VALID_GRADE_CS2101,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid year
        assertParseFailure(parser,
                VALID_CODE_CS2101 + INVALID_YEAR_DESC + SEMESTER_DESC_CS2101 + GRADE_DESC_CS2101,
                Year.MESSAGE_CONSTRAINTS);

        // invalid semester
        assertParseFailure(parser,
                VALID_CODE_CS2101 + YEAR_DESC_CS2101 + INVALID_SEMESTER_DESC + GRADE_DESC_CS2101,
                Semester.MESSAGE_CONSTRAINTS);

        // invalid grade
        assertParseFailure(parser,
                VALID_CODE_CS2101 + YEAR_DESC_CS2101 + SEMESTER_DESC_CS2101 + INVALID_GRADE_DESC,
                Grade.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                VALID_CODE_CS2101 + SEMESTER_DESC_CS2101 + INVALID_YEAR_DESC + INVALID_GRADE_DESC,
                Year.MESSAGE_CONSTRAINTS);


        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + VALID_CODE_CS2101
                        + YEAR_DESC_CS2101 + SEMESTER_DESC_CS2101 + GRADE_DESC_CS2101,
                ModuleCode.MESSAGE_CONSTRAINTS);
    }

}
