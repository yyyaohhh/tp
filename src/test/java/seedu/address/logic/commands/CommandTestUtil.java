package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.testutil.EditModuleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_CODE_CS2040S = "CS2040S";
    public static final String VALID_CODE_CS2101 = "CS2101";
    public static final String VALID_YEAR_CS2040S = "1";
    public static final String VALID_YEAR_CS2101 = "2";
    public static final String VALID_SEMESTER_CS2040S = "2";
    public static final String VALID_SEMESTER_CS2101 = "1";
    public static final String VALID_GRADE_CS2040S = "A-";
    public static final String VALID_GRADE_CS2101 = "IP";

    public static final String YEAR_DESC_CS2040S = " " + PREFIX_YEAR + VALID_YEAR_CS2040S;
    public static final String YEAR_DESC_CS2101 = " " + PREFIX_YEAR + VALID_YEAR_CS2101;
    public static final String SEMESTER_DESC_CS2040S = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS2040S;
    public static final String SEMESTER_DESC_CS2101 = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS2101;
    public static final String GRADE_DESC_CS2040S = " " + PREFIX_GRADE + VALID_GRADE_CS2040S;
    public static final String GRADE_DESC_CS2101 = " " + PREFIX_GRADE + VALID_GRADE_CS2101;

    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "-1"; // '-' not allowed in names
    public static final String INVALID_SEMESTER_DESC = " " + PREFIX_SEMESTER + "0"; // invalid semester enum
    public static final String INVALID_GRADE_DESC = " " + PREFIX_GRADE + "ab"; // invalid grade enum

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditModuleDescriptor DESC_CS2040S;
    public static final EditCommand.EditModuleDescriptor DESC_CS2101;

    static {
        DESC_CS2040S = new EditModuleDescriptorBuilder().withYear(VALID_YEAR_CS2040S)
                .withSemester(VALID_SEMESTER_CS2040S).withGrade(VALID_GRADE_CS2040S).build();
        DESC_CS2101 = new EditModuleDescriptorBuilder().withYear(VALID_YEAR_CS2101)
                .withSemester(VALID_SEMESTER_CS2101).withGrade(VALID_GRADE_CS2101).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the module plan, filtered module list and selected module in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ModulePlan expectedModulePlan = new ModulePlan(actualModel.getModulePlan());
        List<ModulePlanSemester> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedModulePlan, actualModel.getModulePlan());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }


    /**
     * Executes.
     */
    public static void assertInfoCommandFailure(Command command, Model actualModel, String expectedMessage) {
        ModulePlan expectedModulePlan = new ModulePlan(actualModel.getModulePlan());
        List<ModulePlanSemester> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        try {
            System.out.println(command.execute(actualModel).getFeedbackToUser());
        } catch (Exception e) {
            System.out.println(e);
        }

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel).getFeedbackToUser());
        assertEquals(expectedModulePlan, actualModel.getModulePlan());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }


}
