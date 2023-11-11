package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.IS6000;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CalculateCapCommand.
 */
public class CalculateCapCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
        expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
    }

    @Test
    public void execute_calculateCap_success() {
        CalculateCapCommand calculateCapCommand = new CalculateCapCommand();

        String expected = String.format(CalculateCapCommand.MESSAGE_CALCULATION_SUCCESS, model.getCap());

        assertCommandSuccess(calculateCapCommand, model, expected, expectedModel);

        //Test for zero MC modules as well
        model.addModule(IS6000);
        expectedModel.addModule(IS6000);
        assertCommandSuccess(calculateCapCommand, model, expected, expectedModel);

    }

}
