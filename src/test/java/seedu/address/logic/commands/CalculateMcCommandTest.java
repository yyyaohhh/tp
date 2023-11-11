package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.IS6000;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.testutil.ModelStub;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CalculateMcCommand.
 */
public class CalculateMcCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
    }

    @Test
    public void execute_calculateMc_success() {
        CalculateMcCommand mcCommand = new CalculateMcCommand();
        float modularCredits = model.totalModularCredits();
        String expectedMessage = String.format(CalculateMcCommand.MESSAGE_CALCULATION_SUCCESS, modularCredits);

        ModelManager expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());

        assertCommandSuccess(mcCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyModuleListGivesZero() throws Exception {
        ModelStubAcceptingModule modelStub = new ModelStubAcceptingModule();
        assertEquals(modelStub.getModulesAdded(), new ArrayList<Module>());

        String expectedMessage = String.format(CalculateMcCommand.MESSAGE_CALCULATION_SUCCESS, 0.0);

        CommandResult commandResult = new CalculateMcCommand().execute(modelStub);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_zeroMcModulesGivesZero() throws Exception {
        ModelStubAcceptingModule modelStub = new ModelStubAcceptingModule();
        assertEquals(modelStub.getModulesAdded(), new ArrayList<Module>());

        modelStub.addModule(IS6000);
        String expectedMessage = String.format(CalculateMcCommand.MESSAGE_CALCULATION_SUCCESS, 0.0);

        CommandResult commandResult = new CalculateMcCommand().execute(modelStub);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    private class ModelStubAcceptingModule extends ModelStub {
        private final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            if (modulesAdded.contains(module)) {
                throw new DuplicateModuleException();
            }
            modulesAdded.add(module);
        }

        @Override
        public void deleteModule(Module module) {
            if (!modulesAdded.contains(module)) {
                throw new ModuleNotFoundException();
            }
            modulesAdded.remove(module);
        }

        @Override
        public float totalModularCredits() {
            float modularCredits = 0;
            for (int i = 0; i < modulesAdded.size(); i++) {
                modularCredits += modulesAdded.get(i).getModularCredit().getValue();
            }
            return modularCredits;
        }

        public ArrayList<Module> getModulesAdded() {
            return this.modulesAdded;
        }
    }
}
