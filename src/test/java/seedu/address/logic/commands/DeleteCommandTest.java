package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS1101S;
import static seedu.address.testutil.TypicalModules.CS2030S;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.testutil.ModelStubAcceptingModuleAdded;
import seedu.address.testutil.ModelStubWithModule;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalModules.getTypicalModulePlan(),
            new UserPrefs(), getTypicalModuleData());

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_deleteSuccessful() {
        Module moduleToDelete = TypicalModules.CS2040S;
        ModuleCode code = moduleToDelete.getModuleCode();
        DeleteCommand deleteCommand = new DeleteCommand(code);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                Messages.format(moduleToDelete));

        ModelManager expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleInDataAndInPlan_deleteSuccessful() throws Exception {
        //In both ModuleData and ModulePlan -> Success
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        modelStub.addModule(CS2030S);
        Module validModule = modelStub.getModuleFromDb(CS2030S.getModuleCode());

        CommandResult commandResult = new DeleteCommand(validModule.getModuleCode()).execute(modelStub);

        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, Messages.format(validModule)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(), modelStub.getModulesAdded());
    }


    @Test
    public void execute_moduleNotInModuleDataNotInModulePlan_throwsModuleNoteFoundException()  {
        //Not in ModuleData and ModulePlan (Module Not Found) -> Fail
        ModelStubWithModule modelStub = new ModelStubWithModule(CS1101S);

        assertThrows(ModuleNotFoundException.class, () -> modelStub.getModuleFromDb(CS1101S.getModuleCode()));
    }


    @Test
    public void execute_missingModule_throwsModuleNotFoundException() {
        //In ModuleData and not in ModulePlan -> Fail
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        modelStub.addModule(CS2100);
        Module validModule = modelStub.getModuleFromDb(CS2030S.getModuleCode());

        DeleteCommand deleteCommand = new DeleteCommand(validModule.getModuleCode());

        assertThrows(ModuleNotFoundException.class, () -> deleteCommand.execute(modelStub));
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

        // different module -> returns false
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
