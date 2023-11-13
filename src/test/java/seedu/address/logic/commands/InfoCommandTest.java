package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_CODE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.MODULE_IN_BOTH;
import static seedu.address.testutil.TypicalModules.MODULE_IN_NEITHER;
import static seedu.address.testutil.TypicalModules.MODULE_ONLY_DATA;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code InfoCommand}.
 */
public class InfoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        // Both actual and expected models should be identical
        model = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
        expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), model.getModuleData());
    }

    @Test
    public void execute_validModule_success() {
        // Module is present in both moduleData and modulePlan
        Module module = MODULE_IN_BOTH;
        String expectedMessage = module.toInfoString();
        InfoCommand infoCommand = prepareInfoCommand(module);
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);

        // Module is present in moduleData but not modulePlan
        module = MODULE_ONLY_DATA;
        expectedMessage = module.toInfoString();
        infoCommand = prepareInfoCommand(module);
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        // Module is not present in moduleData
        Module invalidModule = MODULE_IN_NEITHER;

        InfoCommand infoCommand = prepareInfoCommand(invalidModule);

        assertCommandFailure(infoCommand, model, String.format(MESSAGE_INVALID_MODULE_CODE,
                invalidModule.getModuleCode()));
    }


    @Test
    public void equals() {
        // use random typical modules for their module codes
        Module module = MODULE_IN_BOTH;
        Module otherModule = MODULE_ONLY_DATA;

        // same object -> returns true
        InfoCommand infoCommand = prepareInfoCommand(module);
        assertEquals(infoCommand, infoCommand);

        // same values -> returns true
        InfoCommand infoCommandCopy = prepareInfoCommand(module);
        assertEquals(infoCommand, infoCommandCopy);

        // different types -> returns false
        assertNotEquals(1, infoCommand);

        // null -> returns false
        assertNotEquals(infoCommand, null);

        // different module -> returns false
        InfoCommand differentInfoCommand = prepareInfoCommand(otherModule);
        assertNotEquals(infoCommand, differentInfoCommand);
    }

    @Test
    public void toStringMethod() {
        // use random typical module for its module code
        Module module = MODULE_IN_BOTH;

        InfoCommand infoCommand = prepareInfoCommand(module);
        String expected = InfoCommand.class.getCanonicalName()
                + "{moduleCode=" + module.getModuleCode() + "}";
        assertEquals(expected, infoCommand.toString());
    }

    // Utility function to create a {@code InfoCommand} with the given {@code Module}.
    private InfoCommand prepareInfoCommand(Module m) {
        return new InfoCommand(m.getModuleCode());
    }
}
