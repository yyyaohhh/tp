package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.*;


import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalModulePlan(),
            new UserPrefs(), getTypicalModuleData());

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_deleteSuccessful() {
        Module moduleToDelete = CS2040S;
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
        ModelStubAcceptingModuleAddedAndDelete modelStub = new ModelStubAcceptingModuleAddedAndDelete();
        modelStub.addModule(CS2030S);
        Module validModule = CS2030S;

        CommandResult commandResult = new DeleteCommand(validModule.getModuleCode()).execute(modelStub);
        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, Messages.format(validModule)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(), modelStub.getModulesAdded());
    }


    @Test
    public void execute_moduleNotInModuleDataNotInModulePlan_throwsModuleNotFoundException()  {
        //Not in ModuleData and ModulePlan (Module Not Found) -> Fail
        ModelStubWithModule modelStub = new ModelStubWithModule(CS2040S);
        modelStub.getModuleFromDb(CS2030S.getModuleCode());

        assertThrows(ModuleNotFoundException.class, () -> modelStub.getModuleFromDb(CS3230.getModuleCode()));
    }


    @Test
    public void execute_missingModule_throwsModuleNotFoundException() {
        //In ModuleData and not in ModulePlan -> Fail
        ModelStubAcceptingModuleAddedAndDelete modelStub = new ModelStubAcceptingModuleAddedAndDelete();
        modelStub.addModule(CS2100);
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
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;

        /**
         * Initializes a ModelStubWithModule with the given module.
         *
         * @param module The module to be associated with the ModelStubWithModule.
         */
        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }

        @Override
        public boolean checkDbValidModuleCode(ModuleCode moduleCode) {
            return getTypicalModuleData().checkDbValidModuleCode(moduleCode);
        }

        @Override
        public Module getModuleFromDb(ModuleCode moduleCode) {
            return getTypicalModuleData().getModule(moduleCode);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAddedAndDelete extends ModelStub {
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
        public Module getModule(ModuleCode code) {
            requireNonNull(code);
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

        @Override
        public boolean checkDbValidModuleCode(ModuleCode moduleCode) {
            return getTypicalModuleData().checkDbValidModuleCode(moduleCode);
        }

        @Override
        public Module getModuleFromDb(ModuleCode moduleCode) {
            return getTypicalModuleData().getModule(moduleCode);
        }

        public ArrayList<Module> getModulesAdded() {
            return this.modulesAdded;
        }
    }
}
