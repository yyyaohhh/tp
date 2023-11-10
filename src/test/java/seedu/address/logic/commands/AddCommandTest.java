package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.*;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
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
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

public class AddCommandTest {

    private Model model = new ModelManager(getTypicalModulePlanWithout(CS2100),
            new UserPrefs(), getTypicalModuleData());

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null, null, null, null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        Module toAdd = new ModuleBuilder().build();
        AddCommand addCommand = new AddCommand(toAdd.getModuleCode(), toAdd.getYearTaken(), toAdd.getSemesterTaken(),
                toAdd.getGrade());

        String expectedMessage = String.format(AddCommand.MESSAGE_ADD_MODULE_SUCCESS,
                Messages.format(toAdd));
        ModelManager expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
        assertCommandSuccess(addCommand,model, expectedMessage, expectedModel);

    }
    @Test
    public void execute_moduleInDataNotInPlan_addSuccessful() throws Exception {
        //In ModuleData and not in ModulePlan
        ModelStubWithMultipleModule modelStub = new ModelStubWithMultipleModule(CS2100);
        Module validModule = CS2030S;

        AddCommand addCommand = new AddCommand(validModule.getModuleCode(), validModule.getYearTaken(),
                validModule.getSemesterTaken(), validModule.getGrade());
        CommandResult commandResult = addCommand.execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_ADD_MODULE_SUCCESS, Messages.format(validModule)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(new Module[]{CS2100, validModule}), modelStub.modulesAdded);
    }

    @Test
    public void execute_moduleNotInModuleDataNotInModulePlan_throwsCommandException()  {
        ModelStubWithMultipleModule modelStub = new ModelStubWithMultipleModule(CS2030S);
        Module notInDB = CS3230;

        assertThrows(ModuleNotFoundException.class, () -> modelStub.getModuleFromDb(notInDB.getModuleCode()));
    }


    @Test
    public void execute_duplicateModule_throwsCommandException() throws Exception {
        //Both in ModuleData and ModulePlan
        Module validModule = new ModuleBuilder().build();

        AddCommand addCommand = new AddCommand(validModule.getModuleCode(), validModule.getYearTaken(),
                validModule.getSemesterTaken(), validModule.getGrade());
        ModelStubWithMultipleModule modelStub = new ModelStubWithMultipleModule(validModule);
        
        assertThrows(CommandException.class,
                String.format(AddCommand.MESSAGE_DUPLICATE_MODULE, validModule.getModuleCode()),
                () -> addCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Module cs2030s = new ModuleBuilder().withCode("CS2030S").build();
        Module cs2040s = new ModuleBuilder().withCode("CS2040S").build();
        AddCommand add2030Command = new AddCommand(cs2030s.getModuleCode(), cs2030s.getYearTaken(),
                cs2030s.getSemesterTaken(), cs2030s.getGrade());
        AddCommand add2040Command = new AddCommand(cs2040s.getModuleCode(), cs2040s.getYearTaken(),
                cs2040s.getSemesterTaken(), cs2040s.getGrade());

        //same -> returns true
        assertTrue(add2030Command.equals(add2030Command));


        AddCommand add2030CommandCopy = new AddCommand(cs2030s.getModuleCode(), cs2030s.getYearTaken(),
                cs2030s.getSemesterTaken(), cs2030s.getGrade());
        assertTrue(add2030Command.equals(add2030CommandCopy));

        assertFalse(add2030Command.equals(1));
        assertFalse(add2030Command.equals(null));

        assertFalse(add2030Command.equals(add2040Command));

    }

    @Test
    public void toStringMethod() {

        AddCommand addCommand = new AddCommand(CS2030S.getModuleCode(), CS2030S.getYearTaken(),
                CS2030S.getSemesterTaken(), CS2030S.getGrade());
        String expected = AddCommand.class.getCanonicalName() + "{moduleCode=" + CS2030S.getModuleCode()
                + ", year=" + CS2030S.getYearTaken() + ", semester=" + CS2030S.getSemesterTaken()
                + ", grade=" + CS2030S.getGrade() + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class EmptyModelStub extends ModelStub {
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
        public float totalModularCredits() {
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
    private class ModelStubWithMultipleModule extends EmptyModelStub {
        private final Module module;
        final ArrayList<Module> modulesAdded = new ArrayList<>();



        ModelStubWithMultipleModule(Module module) {
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
    private class ModelStubAcceptingModuleAdded extends ModelStubWithMultipleModule {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

        ModelStubAcceptingModuleAdded(Module module) {
            super(module);
        }

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
