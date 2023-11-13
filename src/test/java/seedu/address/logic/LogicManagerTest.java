package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_CODE;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_CS2040S;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2040S;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;
import seedu.address.storage.JsonModulePlanStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private final Model model = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonModulePlanStorage modulePlanStorage =
                new JsonModulePlanStorage(temporaryFolder.resolve("moduleplan.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(modulePlanStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete AB1234T";
        assertCommandException(deleteCommand, String.format(MESSAGE_INVALID_MODULE_CODE, "AB1234T"));
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = HelpCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, HelpCommand.SHOWING_HELP_MESSAGE, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredModuleList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e               the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an ModulePlanStorage that throws the IOException e when saving
        JsonModulePlanStorage modulePlanStorage = new JsonModulePlanStorage(prefPath) {
            @Override
            public void saveModulePlan(ReadOnlyModulePlan modulePlan, Path filePath) throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(modulePlanStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveModulePlan method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + " " + VALID_CODE_CS2040S
                + YEAR_DESC_CS2040S + SEMESTER_DESC_CS2040S + GRADE_DESC_CS2040S;
        Module expectedModule = CS2040S;
        ModelManager expectedModel = new ModelManager(getTypicalModulePlan(), new UserPrefs(), getTypicalModuleData());
        expectedModel.addModule(expectedModule);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
