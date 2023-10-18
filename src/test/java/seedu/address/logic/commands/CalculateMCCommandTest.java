package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CalculateMCCommand.
 */
public class CalculateMCCommandTest {
    private Model model;
    private Model expectedModel;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalModules.getTypicalModulePlan(), new UserPrefs());
        expectedModel = new ModelManager(model.getModulePlan(), new UserPrefs());
    }


}
