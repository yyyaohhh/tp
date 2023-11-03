package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.module.ModuleCode;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code InfoCommand}.
 */
public class InfoCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_sampleMessage_success() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        String expectedMessage = String.format(InfoCommand.MESSAGE_INFO_MODULE_SUCCESS, moduleCode);

        assertCommandSuccess(new InfoCommand(moduleCode), model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        ModuleCode firstCode = new ModuleCode("CS2030S");
        ModuleCode secondCode = new ModuleCode("CS2040S");
        InfoCommand infoFirstCommand = new InfoCommand(firstCode);
        InfoCommand infoSecondCommand = new InfoCommand(secondCode);

        // same object -> returns true
        assertTrue(infoFirstCommand.equals(infoFirstCommand));

        // same values -> returns true
        InfoCommand infoFirstCommandCopy = new InfoCommand(firstCode);
        assertTrue(infoFirstCommand.equals(infoFirstCommandCopy));

        // different types -> returns false
        assertFalse(infoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(infoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(infoFirstCommand.equals(infoSecondCommand));
    }

    @Test
    public void toStringMethod() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        InfoCommand infoCommand = new InfoCommand(moduleCode);
        String expected = InfoCommand.class.getCanonicalName() + "{moduleCode=" + moduleCode + "}";
        assertEquals(expected, infoCommand.toString());
    }

}