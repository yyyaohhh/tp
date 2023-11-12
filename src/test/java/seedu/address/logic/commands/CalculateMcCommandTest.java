package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.MODULE_ZERO_MC;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleplan.ModulePlan;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CalculateMcCommand.
 */
public class CalculateMcCommandTest {

    private Model actualModel;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        actualModel = new ModelManager(new ModulePlan(), new UserPrefs(), getTypicalModuleData());
        expectedModel = new ModelManager(new ModulePlan(), new UserPrefs(), getTypicalModuleData());
    }

    @Test
    public void execute_calculateMc_success() {
        // Both actual and expected models should be identical
        actualModel.setModulePlan(getTypicalModulePlan());
        expectedModel.setModulePlan(getTypicalModulePlan());

        String expectedMessage = String.format(CalculateMcCommand.MESSAGE_CALCULATION_SUCCESS,
                actualModel.totalModularCredits());

        assertCommandSuccess(new CalculateMcCommand(), actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyModulePlan_success() {
        // Expected MC should be 0.0
        String expectedMessage = String.format(CalculateMcCommand.MESSAGE_CALCULATION_SUCCESS, 0.0);

        assertCommandSuccess(new CalculateMcCommand(), actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_zeroMcModulesGivesZero() throws Exception {
        actualModel.addModule(MODULE_ZERO_MC);
        expectedModel.addModule(MODULE_ZERO_MC);

        // Expected MC should be 0.0
        String expectedMessage = String.format(CalculateMcCommand.MESSAGE_CALCULATION_SUCCESS, 0.0);

        assertCommandSuccess(new CalculateMcCommand(), actualModel, expectedMessage, expectedModel);
    }
}
