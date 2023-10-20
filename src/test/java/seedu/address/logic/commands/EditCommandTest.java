package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.testutil.EditModuleDescriptorBuilder;
import seedu.address.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalModulePlan(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);
        Module module = model.findModuleUsingCode(moduleCode);

        Module editedModule = new ModuleBuilder().withCode(VALID_CODE_CS2040S).build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditCommand editCommand = new EditCommand(moduleCode, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, Messages.format(editedModule));

        Model expectedModel = new ModelManager(new ModulePlan(getTypicalModulePlan()), new UserPrefs());
        expectedModel.setModule(module, editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);
        Module module = model.findModuleUsingCode(moduleCode);

        ModuleBuilder moduleInList = new ModuleBuilder(module);
        Module editedModule = moduleInList
                .withYear(VALID_YEAR_CS2101).withSem(VALID_SEMESTER_CS2101).build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withYear(VALID_YEAR_CS2101).withSemester(VALID_SEMESTER_CS2101).build();
        EditCommand editCommand = new EditCommand(moduleCode, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, Messages.format(editedModule));

        Model expectedModel = new ModelManager(new ModulePlan(model.getModulePlan()), new UserPrefs());
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);

        EditCommand editCommand = new EditCommand(moduleCode, new EditModuleDescriptor());
        Module editedModule = model.findModuleUsingCode(moduleCode);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, Messages.format(editedModule));

        Model expectedModel = new ModelManager(new ModulePlan(model.getModulePlan()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new ModuleCode(VALID_CODE_CS2040S), DESC_CS2040S);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor(DESC_CS2040S);
        EditCommand commandWithSameValues = new EditCommand(new ModuleCode(VALID_CODE_CS2040S), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different module code -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new ModuleCode(VALID_CODE_CS2101), DESC_CS2040S)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(new ModuleCode(VALID_CODE_CS2040S), DESC_CS2101)));
    }

    @Test
    public void toStringMethod() {
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);
        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();
        EditCommand editCommand = new EditCommand(moduleCode, editModuleDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{moduleCode=" + moduleCode + ", editModuleDescriptor="
                + editModuleDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
