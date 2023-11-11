package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

        assertCommandSuccess(addCommand, model,
                String.format(AddCommand.MESSAGE_ADD_MODULE_SUCCESS, Messages.format(validModule)), expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        // Module present in modulePlan and moduleData
        Module duplicateModule = MODULE_IN_BOTH;

        // Duplicate module with the same user inputs
        assertCommandFailure(prepareAddCommand(duplicateModule), model,
                String.format(AddCommand.MESSAGE_DUPLICATE_MODULE, duplicateModule.getModuleCode()));
        
        // Duplicate module with alternate user inputs
        assertCommandFailure(prepareAltUserInputsAddCommand(duplicateModule), model,
                String.format(AddCommand.MESSAGE_DUPLICATE_MODULE, duplicateModule.getModuleCode()));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        // Module not present in moduleData
        Module invalidModule = MODULE_IN_NEITHER;

        assertCommandFailure(prepareAddCommand(invalidModule), model,
                String.format(Messages.MESSAGE_INVALID_MODULE_CODE, invalidModule.getModuleCode()));
    }

    // Utility function to create an {@code AddCommand} with the given {@code Module}.
    private AddCommand prepareAddCommand(Module m) {
        return new AddCommand(m.getModuleCode(), m.getYearTaken(), m.getSemesterTaken(), m.getGrade());
    }

    // Utility function to create an {@code AddCommand} with alternate user inputs than the given {@code Module}.
    private AddCommand prepareAltUserInputsAddCommand(Module m) {
        Year altYear = new Year("1");
        if (altYear.equals(m.getYearTaken())) {
            altYear = new Year("2");
        }

        Semester altSemester = new Semester("1");
        if (altSemester.equals(m.getSemesterTaken())) {
            altSemester = new Semester("2");
        }

        Grade altGrade = new Grade("A");
        if (altGrade.equals(m.getGrade())) {
            altGrade = new Grade("B");
        }

        return new AddCommand(m.getModuleCode(), altYear, altSemester, altGrade);
    }
}
