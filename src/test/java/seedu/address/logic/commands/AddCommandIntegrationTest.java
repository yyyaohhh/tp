package seedu.address.logic.commands;

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
import static seedu.address.testutil.TypicalModules.getTypicalModulePlanWith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.moduleplan.ModulePlan;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
    }

    @Test
    public void execute_newModule_success() {
        // Module present in moduleData but not modulePlan
        Module validModule = MODULE_ONLY_DATA;

        // Expected model's modulePlan includes the valid module
        ModulePlan expectedModulePlan = getTypicalModulePlanWith(validModule);
        Model expectedModel = new ModelManager(expectedModulePlan, new UserPrefs(), getTypicalModuleData());

        AddCommand addCommand = prepareAddCommand(validModule);

        String expectedMessage = String.format(AddCommand.MESSAGE_ADD_MODULE_SUCCESS, Messages.format(validModule));

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        // Module present in modulePlan and moduleData
        Module duplicateModule = MODULE_IN_BOTH;

        String expectedMessage = String.format(AddCommand.MESSAGE_DUPLICATE_MODULE, duplicateModule.getModuleCode());

        // Duplicate module with the same user inputs
        assertCommandFailure(prepareAddCommand(duplicateModule), model, expectedMessage);

        // Duplicate module with alternate user inputs
        assertCommandFailure(prepareAltUserInputsAddCommand(duplicateModule), model, expectedMessage);
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        // Module not present in moduleData
        Module invalidModule = MODULE_IN_NEITHER;

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_MODULE_CODE, invalidModule.getModuleCode());

        assertCommandFailure(prepareAddCommand(invalidModule), model, expectedMessage);
    }

    // Utility function to create an {@code AddCommand} with the given {@code Module}.
    private AddCommand prepareAddCommand(Module m) {
        return new AddCommand(m.getModuleCode(), m.getYearTaken(), m.getSemesterTaken(), m.getGrade());
    }

    // Utility function to create an {@code AddCommand} with alternate user inputs than the given {@code Module}.
    private AddCommand prepareAltUserInputsAddCommand(Module m) {
        Year altYear = getAltYear(m.getYearTaken());
        Semester altSemester = getAltSemester(m.getSemesterTaken());
        Grade altGrade = getAltGrade(m.getGrade());
        return new AddCommand(m.getModuleCode(), altYear, altSemester, altGrade);
    }
}
