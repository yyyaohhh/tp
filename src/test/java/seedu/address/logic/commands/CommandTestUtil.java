package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditModuleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_YEAR_1 = "2";
    public static final String VALID_YEAR_2 = "3";
    public static final String VALID_SEMESTER_1 = "SEMESTER_1";
    public static final String VALID_SEMESTER_2 = "SEMESTER_2";
    public static final String VALID_GRADE_1 = "IP";
    public static final String VALID_GRADE_2 = "W";

    public static final String YEAR_DESC_1 = " " + PREFIX_YEAR + VALID_YEAR_1;
    public static final String YEAR_DESC_2 = " " + PREFIX_YEAR + VALID_YEAR_2;
    public static final String SEMESTER_DESC_1 = " " + PREFIX_SEMESTER + VALID_SEMESTER_1;
    public static final String SEMESTER_DESC_2 = " " + PREFIX_SEMESTER + VALID_SEMESTER_2;
    public static final String GRADE_DESC_AMY = " " + PREFIX_GRADE + VALID_GRADE_1;
    public static final String GRADE_DESC_BOB = " " + PREFIX_GRADE + VALID_GRADE_2;

    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "-1"; // '-' not allowed in names
    public static final String INVALID_SEMESTER_DESC = " " + PREFIX_SEMESTER + "1"; // invalid semester enum
    public static final String INVALID_GRADE_DESC = " " + PREFIX_GRADE + "ab"; // invalid grade enum

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditModuleDescriptor DESC_1;
    public static final EditCommand.EditModuleDescriptor DESC_2;

    static {
        DESC_1 = new EditModuleDescriptorBuilder().withYear(VALID_YEAR_1)
                .withSemester(VALID_SEMESTER_1).withGrade(VALID_GRADE_1).build();
        DESC_2 = new EditModuleDescriptorBuilder().withYear(VALID_YEAR_2)
                .withSemester(VALID_SEMESTER_2).withGrade(VALID_GRADE_2).build();
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
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
