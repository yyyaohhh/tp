package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyModuleData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.module.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_CODE;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code InfoCommand}.
 */
public class InfoCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_sampleMessage_success() throws CommandException {
        Module validModule = new ModuleBuilder().build();
        ModelStub modelStub = new ModelStubWithModules();

        System.out.println((validModule.toInfoString()));

        CommandResult commandResult = new InfoCommand(validModule.getModuleCode()).execute(modelStub);

        String expectedMessage = validModule.toInfoString();
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_ModuleNotInData() throws CommandException {
        ModelStub modelStub = new ModelStubWithModules();
        InfoCommand infoCommand = new InfoCommand(new ModuleCode("PC1101"));
        assertInfoCommandFailure(infoCommand, modelStub, MESSAGE_INVALID_MODULE_CODE);
    }


    @Test
    public void equals() {
        ModuleCode firstCode = new ModuleCode("CS2030S");
        ModuleCode secondCode = new ModuleCode("CS2040S");
        InfoCommand infoFirstCommand = new InfoCommand(firstCode);
        InfoCommand infoSecondCommand = new InfoCommand(secondCode);

        // same object -> returns true
        assertTrue(infoFirstCommand.equals(infoFirstCommand));

        // same values -> returns true
        InfoCommand infoFirstCommandCopy = new InfoCommand(firstCode);
        assertTrue(infoFirstCommand.equals(infoFirstCommandCopy));

        // different types -> returns false
        assertFalse(infoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(infoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(infoFirstCommand.equals(infoSecondCommand));
    }

    @Test
    public void toStringMethod() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        InfoCommand infoCommand = new InfoCommand(moduleCode);
        String expected = InfoCommand.class.getCanonicalName() + "{moduleCode=" + moduleCode + "}";
        assertEquals(expected, infoCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class EmptyModelStub extends ModelStub {




        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getModulePlanFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModulePlanFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModulePlan(ReadOnlyModulePlan modulePlan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModulePlan getModulePlan() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Module getModule(ModuleCode code) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public float totalModularCredits() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Float getCap() {
            throw new AssertionError("This method should not be called.");
        }





        @Override
        public ReadOnlyModuleData getModuleData() {
            throw new AssertionError("This method should not be called.");
        }

        public ModuleName getDbModuleName(ModuleCode moduleCode) throws ModuleNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        public Description getDbModuleDescription(ModuleCode moduleCode) throws ModuleNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        public ModularCredit getDbModularCredit(ModuleCode moduleCode) throws ModuleNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkDbValidModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkDbValidModuleCode(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Module getModuleFromDb(ModuleCode moduleCode) {
            return getTypicalModuleData().getModule(moduleCode);
        }

        @Override
        public ObservableList<ModulePlanSemester> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }
    }


    /**
     * A Model stub that always accept the Module being added.
     */
    private class ModelStubWithModules extends EmptyModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();
        private final ModulePlan modulePlan = TypicalModules.getTypicalModulePlan();


        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);

        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ReadOnlyModulePlan getModulePlan() {
            return new ModulePlan();
        }

        @Override
        public ObservableList<ModulePlanSemester> getFilteredModuleList() {
            return modulePlan.getModulePlanSemesterList();
        }
    }

}