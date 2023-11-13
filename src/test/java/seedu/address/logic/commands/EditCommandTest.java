package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModuleUtil.getAltGrade;
import static seedu.address.testutil.ModuleUtil.getAltSemester;
import static seedu.address.testutil.ModuleUtil.getAltYear;
import static seedu.address.testutil.TypicalModules.MODULE_IN_BOTH;
import static seedu.address.testutil.TypicalModules.MODULE_IN_NEITHER;
import static seedu.address.testutil.TypicalModules.MODULE_ONLY_DATA;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import org.junit.jupiter.api.BeforeEach;
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

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditCommand.
 */
public class EditCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        // Module present in both moduleData and modulePlan
        Module module = MODULE_IN_BOTH;

        // Edited module with alternate year, semester and grade
        Module editedModule = module.fillUserInputs(getAltYear(module.getYearTaken()),
                getAltSemester(module.getSemesterTaken()), getAltGrade(module.getGrade()));

        // Descriptor with the same alternate year, semester and grade
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();

        // Expected model's modulePlan replaces the original module with the edited module
        ModulePlan expectedModulePlan = getTypicalModulePlan();
        expectedModulePlan.setModule(module, editedModule);
        Model expectedModel = new ModelManager(expectedModulePlan, new UserPrefs(), getTypicalModuleData());

        EditCommand editCommand = new EditCommand(module.getModuleCode(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, Messages.format(editedModule));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        // Module present in both moduleData and modulePlan
        Module module = MODULE_IN_BOTH;

        // Edited module with alternate year and semester (grade unchanged)
        Module editedModule = module.fillUserInputs(getAltYear(module.getYearTaken()),
                getAltSemester(module.getSemesterTaken()), module.getGrade());

        // Descriptor with the same alternate year and semester (grade unchanged)
        EditModuleDescriptor descriptor = new EditModuleDescriptor();
        descriptor.setYear(editedModule.getYearTaken());
        descriptor.setSemester(editedModule.getSemesterTaken());

        // Expected model's modulePlan replaces the original module with the edited module
        ModulePlan expectedModulePlan = getTypicalModulePlan();
        expectedModulePlan.setModule(module, editedModule);
        Model expectedModel = new ModelManager(expectedModulePlan, new UserPrefs(), getTypicalModuleData());

        EditCommand editCommand = new EditCommand(module.getModuleCode(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, Messages.format(editedModule));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        // Module present in both moduleData and modulePlan
        Module module = MODULE_IN_BOTH;

        // Empty descriptor
        EditModuleDescriptor descriptor = new EditModuleDescriptor();

        // Expected model's modulePlan remains the same
        Model expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());

        EditCommand editCommand = new EditCommand(module.getModuleCode(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, Messages.format(module));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingModule_throwsCommandException() {
        // Module present in moduleData but not modulePlan
        Module missingModule = MODULE_ONLY_DATA;

        EditCommand editCommand = new EditCommand(missingModule.getModuleCode(), new EditModuleDescriptor());

        String expectedMessage = String.format(Messages.MESSAGE_MODULE_NOT_FOUND,
                missingModule.getModuleCode(), EditCommand.COMMAND_WORD);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        // Module not present in moduleData
        Module invalidModule = MODULE_IN_NEITHER;

        EditCommand editCommand = new EditCommand(invalidModule.getModuleCode(), new EditModuleDescriptor());

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_MODULE_CODE,
                invalidModule.getModuleCode());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new ModuleCode(VALID_CODE_CS2040S), DESC_CS2040S);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor(DESC_CS2040S);
        EditCommand commandWithSameValues = new EditCommand(new ModuleCode(VALID_CODE_CS2040S), copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(standardCommand, new HelpCommand());

        // different module code -> returns false
        assertNotEquals(standardCommand, new EditCommand(new ModuleCode(VALID_CODE_CS2101), DESC_CS2040S));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditCommand(new ModuleCode(VALID_CODE_CS2040S), DESC_CS2101));
    }

    @Test
    public void toStringMethod() {
        ModuleCode moduleCode = new ModuleCode(VALID_CODE_CS2040S);
        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();
        EditCommand editCommand = new EditCommand(moduleCode, editModuleDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{moduleCode=" + moduleCode
                + ", editModuleDescriptor=" + editModuleDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
