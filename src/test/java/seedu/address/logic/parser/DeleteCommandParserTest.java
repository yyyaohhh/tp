package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.module.ModuleCode.MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalModules.CS2030S;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, CS2030S.getModuleCode().toString(), new DeleteCommand(CS2030S.getModuleCode()));
    }

    @Test
    public void parse_invalidSingleArg_throwsParseException() {
        assertParseFailure(parser, "ab", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "2030s", MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidMultipleArgs_throwsParseException() {
        assertParseFailure(parser, "a b", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2030 s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }
}
