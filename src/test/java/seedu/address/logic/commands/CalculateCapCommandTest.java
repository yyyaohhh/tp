package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.CFG1002;
import static seedu.address.testutil.TypicalModules.CS2030S;
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

        //Test for zero MC modules
        model.addModule(IS6000);
        expectedModel.addModule(IS6000);

        assertCommandSuccess(calculateCapCommand, model, expected, expectedModel);

        //Test for CS/CU modules (Grade point as null)
        model.addModule(CFG1002);
        expectedModel.addModule(CFG1002);

        assertCommandSuccess(calculateCapCommand, model, expected, expectedModel);
    }

    @Test
    public void execute_emptyModuleListGivesZero() throws Exception {
        CalculateCapCommandTest.ModelStubAcceptingModule modelStub =
                new CalculateCapCommandTest.ModelStubAcceptingModule();
        assertEquals(modelStub.getModulesAdded(), new ArrayList<Module>());

        String expectedMessage = String.format(CalculateCapCommand.MESSAGE_CALCULATION_SUCCESS, 0.0);

        CommandResult commandResult = new CalculateCapCommand().execute(modelStub);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_csModuleDoesNotAffectCap() throws Exception {
        CalculateCapCommandTest.ModelStubAcceptingModule modelStub =
                new CalculateCapCommandTest.ModelStubAcceptingModule();
        assertEquals(modelStub.getModulesAdded(), new ArrayList<Module>());

        modelStub.addModule(CS2030S);
        modelStub.addModule(CFG1002);

        float expectedCap = modelStub.getCap();

        String expectedMessage = String.format(CalculateCapCommand.MESSAGE_CALCULATION_SUCCESS, expectedCap);

        CommandResult commandResult = new CalculateCapCommand().execute(modelStub);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_zeroMcModuleGivesZero() throws Exception {
        CalculateCapCommandTest.ModelStubAcceptingModule modelStub =
                new CalculateCapCommandTest.ModelStubAcceptingModule();
        assertEquals(modelStub.getModulesAdded(), new ArrayList<Module>());

        modelStub.addModule(IS6000);
        String expectedMessage = String.format(CalculateCapCommand.MESSAGE_CALCULATION_SUCCESS, 0.0);

        CommandResult commandResult = new CalculateCapCommand().execute(modelStub);

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
        public Float getCap() {
            float gradePoints = 0;
            float moduleCredits = 0;
            for (int i = 0; i < modulesAdded.size(); i++) {
                if (modulesAdded.get(i).getGrade().gradePoint() != null) {
                    gradePoints += modulesAdded.get(i).getGrade().gradePoint();
                    moduleCredits += modulesAdded.get(i).getMcValue();
                }
            }

            if (moduleCredits == 0) {
                return 0f;
            }
            return gradePoints / moduleCredits;
        }

        public ArrayList<Module> getModulesAdded() {
            return this.modulesAdded;
        }
    }
}
