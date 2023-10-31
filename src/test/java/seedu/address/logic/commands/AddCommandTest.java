package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_MODULE_NOT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyModuleData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;
import seedu.address.testutil.ModuleBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null, null, null, null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_ADD_MODULE_SUCCESS, Messages.format(validModule)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }
    @Test
    public void execute_moduleInDataNotInPlan_addSuccessful() throws Exception {
        //In ModuleData and not in ModulePlan
        ModelStubWithModule modelStub = new ModelStubWithModule(CS2100);
        Module validModule = modelStub.getModuleFromDb(CS2030S.getModuleCode());

        CommandResult commandResult = new AddCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_ADD_MODULE_SUCCESS, Messages.format(validModule)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(new Module[]{CS2100, validModule}), modelStub.modulesAdded);
    }

    @Test
    public void execute_moduleNotInModuleDataNotInModulePlan_throwsCommandException()  {
        //Not in ModuleData and ModulePlan (Module Not Found)
        ModelStubWithModule modelStub = new ModelStubWithModule(CS2030S);

        assertThrows(ModuleNotFoundException.class, () -> modelStub.getModuleFromDb(CS1101S.getModuleCode()));
    }


    @Test
    public void execute_duplicateModule_throwsCommandException() throws Exception {
        //Both in ModuleData and ModulePlan
        Module validModule = new ModuleBuilder().build();
        AddCommand addCommand = new AddCommand(validModule);
        ModelStubWithModule modelStub = new ModelStubWithModule(validModule);
        
        assertThrows(CommandException.class,
                String.format(AddCommand.MESSAGE_DUPLICATE_MODULE, validModule.getModuleCode()),
                () -> addCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Module cs2030s = new ModuleBuilder().withCode("CS2030S").build();
        Module cs2040s = new ModuleBuilder().withCode("CS2040S").build();
        AddCommand add2030Command = new AddCommand(cs2030s);
        AddCommand add2040Command = new AddCommand(cs2040s);

        //same -> returns true
        assertTrue(add2030Command.equals(add2030Command));

        AddCommand add2030CommandCopy = new AddCommand(cs2030s);
        assertTrue(add2030Command.equals(add2030CommandCopy));

        assertFalse(add2030Command.equals(1));
        assertFalse(add2030Command.equals(null));

        assertFalse(add2030Command.equals(add2040Command));

    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(CS2030S);
        String expected = AddCommand.class.getCanonicalName() + "{moduleCode=" + CS2030S.getModuleCode()
                + ", year=" + CS2030S.getYearTaken() + ", semester=" + CS2030S.getSemesterTaken()
                + ", grade=" + CS2030S.getGrade() + "}";
        assertEquals(expected, addCommand.toString());
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
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
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
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }


    }

    /**
     * A Model stub that always accept the Module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

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
    }

}
