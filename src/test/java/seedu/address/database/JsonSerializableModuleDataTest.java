package seedu.address.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ModuleData;

public class JsonSerializableModuleDataTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModuleDataTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesModuleData.json");
    private static final Path INVALID_MODULES_FILE = TEST_DATA_FOLDER.resolve("invalidModulesModuleData.json");

    @Test
    public void toModelType_typicalModulesFile_success() throws Exception {
        JsonSerializableModuleData dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULES_FILE,
                JsonSerializableModuleData.class).get();
        ModuleData moduleDataFromFile = dataFromFile.toModelType();
        ModuleData typicalModulesModuleData = getTypicalModuleData();
        assertEquals(moduleDataFromFile, typicalModulesModuleData);
    }

    @Test
    public void toModelType_invalidModulesFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModuleData dataFromFile = JsonUtil.readJsonFile(INVALID_MODULES_FILE,
                JsonSerializableModuleData.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
