package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.testutil.TypicalModules;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalModulesModulePlan.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidModulesModulePlan.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateModulesModulePlan.json");

    @Test
    public void toModelType_typicalModulesFile_success() throws Exception {
        JsonSerializableModulePlan dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableModulePlan.class).get();
        ModulePlan modulePlanFromFile = dataFromFile.toModelType();
        ModulePlan typicalModulesModulePlan = TypicalModules.getTypicalModulePlan();
        assertEquals(modulePlanFromFile, typicalModulesModulePlan);
    }

    @Test
    public void toModelType_invalidModulesFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModulePlan dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableModulePlan.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableModulePlan dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableModulePlan.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableModulePlan.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}
