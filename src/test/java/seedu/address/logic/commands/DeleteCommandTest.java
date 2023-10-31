package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS1101S;
import static seedu.address.testutil.TypicalModules.CS2030S;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyModuleData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalModules.getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_deleteSuccessful() {
        Module moduleToDelete = TypicalModules.CS2040S;
        ModuleCode code = moduleToDelete.getModuleCode();
        DeleteCommand deleteCommand = new DeleteCommand(code);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                Messages.format(moduleToDelete));

        ModelManager expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleInDataAndInPlan_deleteSuccessful() throws Exception {
        //In both ModuleData and ModulePlan -> Success
        DeleteCommandTest.ModelStubWithModule modelStub = new DeleteCommandTest.ModelStubWithModule(CS2030S);
        Module validModule = modelStub.getModuleFromDb(CS2030S.getModuleCode());

        CommandResult commandResult = new DeleteCommand(validModule.getModuleCode()).execute(modelStub);

        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, Messages.format(validModule)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(), modelStub.modulesAdded);
    }

    @Test
    public void execute_moduleNotInModuleDataNotInModulePlan_throwsModuleNoteFoundException()  {
        //Not in ModuleData and ModulePlan (Module Not Found) -> Fail
        DeleteCommandTest.ModelStubWithModule modelStub = new DeleteCommandTest.ModelStubWithModule(CS1101S);

        assertThrows(ModuleNotFoundException.class,
                () -> modelStub.getModuleFromDb(CS1101S.getModuleCode()));
    }


    @Test
    public void execute_missingModule_throwsModuleNotFoundException() {
        //In ModuleData and not in ModulePlan -> Fail
        DeleteCommandTest.ModelStubWithModule modelStub = new DeleteCommandTest.ModelStubWithModule(CS2100);
        Module validModule = modelStub.getModuleFromDb(CS2030S.getModuleCode());

        DeleteCommand deleteCommand = new DeleteCommand(validModule.getModuleCode());

        assertThrows(ModuleNotFoundException.class, () -> deleteCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Module cs2030s = new ModuleBuilder().withCode("CS2030S").build();
        Module cs2040s = new ModuleBuilder().withCode("CS2040S").build();
        ModuleCode firstCode = cs2030s.getModuleCode();
        ModuleCode secondCode = cs2040s.getModuleCode();
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstCode);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondCode);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstCode);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different module -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Module cs2030s = new ModuleBuilder().withCode("CS2030S").build();
        ModuleCode code = cs2030s.getModuleCode();
        DeleteCommand deleteCommand = new DeleteCommand(code);
        String expected = DeleteCommand.class.getCanonicalName() + "{toDelete=" + code + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

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
        public int totalModularCredits() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Float getCap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModuleData(ReadOnlyModuleData moduleData) {
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
            return getTypicalModuleData().checkDbValidModuleCode(moduleCode);
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
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends DeleteCommandTest.ModelStub {
        private final Module module;
        final ArrayList<Module> modulesAdded = new ArrayList<>();


        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
            modulesAdded.add(module);
        }

        @Override
        public void addModule(Module module) {
            if (modulesAdded.contains(module)) {
                throw new DuplicateModuleException();
            }
            modulesAdded.add(module);
        }

        @Override
        public Module getModule(ModuleCode code) {
            for (int i = 0; i < modulesAdded.size(); i++) {
                if (modulesAdded.get(i).getModuleCode().equals(code)) {
                    return modulesAdded.get(i);
                }
            }
            return null;
        }

        @Override
        public void deleteModule(Module module) {
            if (!modulesAdded.contains(module)) {
                throw new ModuleNotFoundException();
            }
            modulesAdded.remove(module);
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

    }
}
