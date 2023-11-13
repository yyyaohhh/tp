package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.MODULE_IN_BOTH;
import static seedu.address.testutil.TypicalModules.MODULE_IN_NEITHER;
import static seedu.address.testutil.TypicalModules.MODULE_ONLY_DATA;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.testutil.ModelStub;

/**
 * Contains unit tests for {@code AddCommand}.
 */
public class AddCommandTest {

    @Test
    public void constructor_nullParameter_throwsNullPointerException() {
        // random typical module
        Module m = MODULE_IN_BOTH;

        assertThrows(NullPointerException.class, () -> new AddCommand(
                null, m.getYearTaken(), m.getSemesterTaken(), m.getGrade()));
        assertThrows(NullPointerException.class, () -> new AddCommand(
                m.getModuleCode(), null, m.getSemesterTaken(), m.getGrade()));
        assertThrows(NullPointerException.class, () -> new AddCommand(
                m.getModuleCode(), m.getYearTaken(), null, m.getGrade()));
        assertThrows(NullPointerException.class, () -> new AddCommand(
                m.getModuleCode(), m.getYearTaken(), m.getSemesterTaken(), null));
    }

    @Test
    public void execute_moduleInDataNotInPlan_addSuccessful() throws Exception {
        //In ModuleData and not in ModulePlan
        ModelStubWithMultipleModule modelStub = new ModelStubWithMultipleModule();
        Module validModule = MODULE_ONLY_DATA;

        AddCommand addCommand = new AddCommand(validModule.getModuleCode(), validModule.getYearTaken(),
                validModule.getSemesterTaken(), validModule.getGrade());
        CommandResult commandResult = addCommand.execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_ADD_MODULE_SUCCESS, Messages.format(validModule)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_moduleNotInModuleDataNotInModulePlan_throwsCommandException() {
        ModelStubWithMultipleModule modelStub = new ModelStubWithMultipleModule();
        Module notInDB = MODULE_IN_NEITHER;

        assertThrows(ModuleNotFoundException.class, () -> modelStub.getModuleFromDb(notInDB.getModuleCode()));
    }


    @Test
    public void execute_duplicateModule_throwsCommandException() {
        //Both in ModuleData and ModulePlan
        Module validModule = MODULE_IN_BOTH;

        AddCommand addCommand = new AddCommand(validModule.getModuleCode(), validModule.getYearTaken(),
                validModule.getSemesterTaken(), validModule.getGrade());
        ModelStubWithMultipleModule modelStub = new ModelStubWithMultipleModule(validModule);

        assertThrows(CommandException.class, String.format(AddCommand.MESSAGE_DUPLICATE_MODULE,
                validModule.getModuleCode()), () -> addCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        // Use random typical modules for their module codes
        Module module = MODULE_IN_BOTH;
        Module otherModule = MODULE_ONLY_DATA;

        // same object -> returns true
        AddCommand addCommand = new AddCommand(module.getModuleCode(), module.getYearTaken(),
                module.getSemesterTaken(), module.getGrade());
        assertEquals(addCommand, addCommand);

        // same values -> returns true
        AddCommand addCommandCopy = new AddCommand(module.getModuleCode(), module.getYearTaken(),
                module.getSemesterTaken(), module.getGrade());
        assertEquals(addCommand, addCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addCommand);

        // null -> returns false
        assertNotEquals(addCommand, null);

        // different module code -> returns false
        AddCommand differentCode = new AddCommand(otherModule.getModuleCode(), module.getYearTaken(),
                module.getSemesterTaken(), module.getGrade());
        assertNotEquals(addCommand, differentCode);

        // different year -> returns false
        AddCommand differentYear = new AddCommand(module.getModuleCode(), otherModule.getYearTaken(),
                module.getSemesterTaken(), module.getGrade());
        assertNotEquals(addCommand, differentYear);

        // different semester -> returns false
        AddCommand differentSemester = new AddCommand(module.getModuleCode(), module.getYearTaken(),
                otherModule.getSemesterTaken(), module.getGrade());
        assertNotEquals(addCommand, differentSemester);

        // different grade -> returns false
        AddCommand differentGrade = new AddCommand(module.getModuleCode(), module.getYearTaken(),
                module.getSemesterTaken(), otherModule.getGrade());
        assertNotEquals(addCommand, differentGrade);
    }

    @Test
    public void toStringMethod() {
        // use random typical module
        Module module = MODULE_IN_BOTH;

        AddCommand addCommand = new AddCommand(module.getModuleCode(), module.getYearTaken(),
                module.getSemesterTaken(), module.getGrade());
        String expected = AddCommand.class.getCanonicalName()
                + "{moduleCode=" + module.getModuleCode()
                + ", year=" + module.getYearTaken()
                + ", semester=" + module.getSemesterTaken()
                + ", grade=" + module.getGrade() + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A Model stub that accepts multiple modules.
     */
    private class ModelStubWithMultipleModule extends ModelStub {
        private final ArrayList<Module> modulesAdded = new ArrayList<>();

        ModelStubWithMultipleModule() {
        }

        ModelStubWithMultipleModule(Module module) {
            requireNonNull(module);
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

        @Override
        public Module getModuleFromDb(ModuleCode moduleCode) {
            return getTypicalModuleData().getModule(moduleCode);
        }
    }

}
