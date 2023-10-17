package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CalculateCommand.MESSAGE_NOT_FOUND_MODULE;
import static seedu.address.testutil.TypicalModules.CS2030S;
import static seedu.address.testutil.TypicalModules.getTypicalAddressBook;
import static seedu.address.testutil.TypicalModules.getTypicalModules;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class CalculateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new CalculateCommand(), model, MESSAGE_NOT_FOUND_MODULE);
    }

}
