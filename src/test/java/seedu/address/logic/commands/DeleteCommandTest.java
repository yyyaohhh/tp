package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.MODULE_IN_BOTH;
import static seedu.address.testutil.TypicalModules.MODULE_IN_NEITHER;
import static seedu.address.testutil.TypicalModules.MODULE_ONLY_DATA;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlanWithout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ModulePlan;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
    }

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(null));
    }

    @Test
    public void execute_validModule_deleteSuccessful() {
        // Module is present in both moduleData and modulePlan
        Module moduleToDelete = MODULE_IN_BOTH;

        // Expected model's modulePlan excludes the valid module
        ModulePlan expectedModulePlan = getTypicalModulePlanWithout(moduleToDelete);
        ModelManager expectedModel = new ModelManager(expectedModulePlan, new UserPrefs(), getTypicalModuleData());

        DeleteCommand deleteCommand = prepareDeleteCommand(moduleToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                Messages.format(moduleToDelete));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingModule_throwsCommandException() {
        // Module present in moduleData but not modulePlan
        Module missingModule = MODULE_ONLY_DATA;

        DeleteCommand deleteCommand = prepareDeleteCommand(missingModule);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_MODULE_NOT_FOUND,
                missingModule.getModuleCode(), DeleteCommand.COMMAND_WORD));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        // Module not present in moduleData
        Module invalidModule = MODULE_IN_NEITHER;

        DeleteCommand deleteCommand = prepareDeleteCommand(invalidModule);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_MODULE_CODE,
                invalidModule.getModuleCode()));
    }

    @Test
    public void equals() {
        // use random typical modules for their module codes
        Module module = MODULE_IN_BOTH;
        Module otherModule = MODULE_ONLY_DATA;

        // same object -> returns true
        DeleteCommand deleteCommand = prepareDeleteCommand(module);
        assertEquals(deleteCommand, deleteCommand);

        // same values -> returns true
        DeleteCommand deleteCommandCopy = prepareDeleteCommand(module);
        assertEquals(deleteCommand, deleteCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteCommand);

        // null -> returns false
        assertNotEquals(deleteCommand, null);

        // different module -> returns false
        DeleteCommand differentDeleteCommand = prepareDeleteCommand(otherModule);
        assertNotEquals(deleteCommand, differentDeleteCommand);
    }

    @Test
    public void toStringMethod() {
        // use random typical module for its module code
        Module module = MODULE_IN_BOTH;

        DeleteCommand deleteCommand = prepareDeleteCommand(module);
        String expected = DeleteCommand.class.getCanonicalName()
                + "{toDelete=" + module.getModuleCode() + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    // Utility function to create an {@code AddCommand} with the given {@code Module}.
    private DeleteCommand prepareDeleteCommand(Module m) {
        return new DeleteCommand(m.getModuleCode());
    }
}
