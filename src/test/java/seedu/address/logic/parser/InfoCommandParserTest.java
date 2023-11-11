package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.InfoCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.model.module.ModuleCode.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

public class InfoCommandParserTest {

    private final InfoCommandParser parser = new InfoCommandParser();

    @Test
    public void parse_invalidSingleArg_throwsParseException() {
        // Module code constraint exception should be thrown when the given argument
        // contains only one word

        // Single alphanumeric word (no spaces)
        assertParseFailure(parser, "a1b2c3", MESSAGE_CONSTRAINTS);

        // Single word with non-alphanumeric characters
        assertParseFailure(parser, "a_b!!c:e", MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidMultipleArgs_throwsParseException() {
        // Command format exception should be thrown instead of module code constraint
        // exception when the given argument contains more than one word

        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // Multiple words with spaces
        assertParseFailure(parser, "abc de", message);
        assertParseFailure(parser, "a b c d e", message);
    }
}
