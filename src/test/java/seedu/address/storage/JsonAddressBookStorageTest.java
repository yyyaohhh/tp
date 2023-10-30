package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2040S;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.CS2101;
import static seedu.address.testutil.TypicalModules.CS2106;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModulePlan(null));
    }

    private java.util.Optional<ReadOnlyModulePlan> readModulePlan(String filePath) throws Exception {
        return new JsonModulePlanStorage(Paths.get(filePath)).readModulePlan(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readModulePlan("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readModulePlan("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readModulePlan("invalidModuleModulePlan.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readModulePlan("invalidAndValidModuleModulePlan.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        ModulePlan original = getTypicalModulePlan();
        JsonModulePlanStorage jsonModulePlanStorage = new JsonModulePlanStorage(filePath);

        // Save in new file and read back
        jsonModulePlanStorage.saveModulePlan(original, filePath);
        ReadOnlyModulePlan readBack = jsonModulePlanStorage.readModulePlan(filePath).get();
        assertEquals(original, new ModulePlan(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addModule(CS2100);
        original.removeModule(CS2040S);
        jsonModulePlanStorage.saveModulePlan(original, filePath);
        readBack = jsonModulePlanStorage.readModulePlan(filePath).get();
        assertEquals(original, new ModulePlan(readBack));

        // Save and read without specifying file path
        original.addModule(CS2106);
        jsonModulePlanStorage.saveModulePlan(original); // file path not specified
        readBack = jsonModulePlanStorage.readModulePlan().get(); // file path not specified
        assertEquals(original, new ModulePlan(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModulePlan(null, "SomeFile.json"));
    }

    /**
     * Saves {@code modulePlan} at the specified {@code filePath}.
     */
    private void saveModulePlan(ReadOnlyModulePlan modulePlan, String filePath) {
        try {
            new JsonModulePlanStorage(Paths.get(filePath))
                    .saveModulePlan(modulePlan, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModulePlan(new ModulePlan(), null));
    }
}
