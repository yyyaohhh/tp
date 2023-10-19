package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;


import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalModules.getTypicalModulePlan(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Module moduleToDelete = new ModuleBuilder().withCode("CS2040S").build();
        ModuleCode code = moduleToDelete.getModuleCode();
        DeleteCommand deleteCommand = new DeleteCommand(code);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                Messages.format(moduleToDelete));

        ModelManager expectedModel = new ModelManager(model.getModulePlan(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        Module cs2030s = new ModuleBuilder().withCode("CS2030S").build();
        Module cs2040s = new ModuleBuilder().withCode("CS2040S").build();
        ModuleCode firstCode = cs2030s.getModuleCode();
        ModuleCode secondCode = cs2040s.getModuleCode();
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstCode);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondCode);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstCode);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Module cs2030s = new ModuleBuilder().withCode("CS2030S").build();
        ModuleCode code = cs2030s.getModuleCode();
        DeleteCommand deleteCommand = new DeleteCommand(code);
        String expected = DeleteCommand.class.getCanonicalName() + "{toDelete=" + code + "}";
        assertEquals(expected, deleteCommand.toString());
    }

}
