package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_CS2101;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.testutil.EditModuleDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no module code specified
        assertParseFailure(parser, YEAR_DESC_CS2040S, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, PREFIX_CODE + " " + VALID_CODE_CS2040S, EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

//    @Test
//    public void parse_invalidPreamble_failure() {
//        // negative index
//        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
//
//        // zero index
//        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
//
//        // invalid arguments being parsed as preamble
//        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
//
//        // invalid prefix being parsed as preamble
//        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
//    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, PREFIX_CODE + " "  + VALID_CODE_CS2040S + INVALID_YEAR_DESC,
                Year.MESSAGE_CONSTRAINTS); // invalid year
        assertParseFailure(parser, PREFIX_CODE + " "  + VALID_CODE_CS2040S + INVALID_SEMESTER_DESC,
                Semester.MESSAGE_CONSTRAINTS); // invalid semester
        assertParseFailure(parser, PREFIX_CODE + " "  + VALID_CODE_CS2040S + INVALID_GRADE_DESC,
                Grade.MESSAGE_CONSTRAINTS); // invalid grade

        // invalid year followed by valid semester
        assertParseFailure(parser,
                PREFIX_CODE + " "  + VALID_CODE_CS2040S + INVALID_YEAR_DESC + SEMESTER_DESC_CS2040S,
                Year.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                PREFIX_CODE + " "  + VALID_CODE_CS2040S
                        + INVALID_SEMESTER_DESC + INVALID_YEAR_DESC + VALID_GRADE_CS2040S,
                Semester.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);
        String userInput = PREFIX_CODE + " " + moduleCode + SEMESTER_DESC_CS2101
                + YEAR_DESC_CS2040S + GRADE_DESC_CS2040S;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2101)
                .withYear(VALID_YEAR_CS2040S).withGrade(VALID_GRADE_CS2040S).build();
        EditCommand expectedCommand = new EditCommand(moduleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);
        String userInput = PREFIX_CODE + " " + moduleCode + GRADE_DESC_CS2101 + YEAR_DESC_CS2040S;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withGrade(VALID_GRADE_CS2101)
                .withYear(VALID_YEAR_CS2040S).build();
        EditCommand expectedCommand = new EditCommand(moduleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // year
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2101);
        String moduleCodeString = PREFIX_CODE + " " + moduleCode;
        String userInput = moduleCodeString + YEAR_DESC_CS2040S;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withYear(VALID_YEAR_CS2040S).build();
        EditCommand expectedCommand = new EditCommand(moduleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // semester
        userInput = moduleCodeString + SEMESTER_DESC_CS2040S;
        descriptor = new EditModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2040S).build();
        expectedCommand = new EditCommand(moduleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // grade
        userInput = moduleCodeString + GRADE_DESC_CS2040S;
        descriptor = new EditModuleDescriptorBuilder().withGrade(VALID_GRADE_CS2040S).build();
        expectedCommand = new EditCommand(moduleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);
        String moduleCodeString = PREFIX_CODE + " " + moduleCode;
        String userInput = moduleCodeString + INVALID_SEMESTER_DESC + SEMESTER_DESC_CS2101;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEMESTER));

        // invalid followed by valid
        userInput = moduleCodeString + SEMESTER_DESC_CS2101 + INVALID_SEMESTER_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEMESTER));

        // mulltiple valid fields repeated
        userInput = moduleCodeString + YEAR_DESC_CS2040S + SEMESTER_DESC_CS2040S + GRADE_DESC_CS2040S
                + YEAR_DESC_CS2101 + SEMESTER_DESC_CS2101 + GRADE_DESC_CS2101;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE));

        // multiple invalid values
        userInput = moduleCodeString + INVALID_YEAR_DESC + INVALID_SEMESTER_DESC + INVALID_GRADE_DESC
                + INVALID_YEAR_DESC + INVALID_SEMESTER_DESC + INVALID_GRADE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_GRADE));
    }
}
