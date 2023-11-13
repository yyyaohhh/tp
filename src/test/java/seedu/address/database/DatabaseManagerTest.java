package seedu.address.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalModules.getTypicalModuleData;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ModuleData;

public class DatabaseManagerTest {

    private static final String VALID_FILE_PATH = "database/validFile.json";
    private static final String MISSING_FILE_PATH = "database/missingFile.json";
    private static final String INVALID_FILE_DATA = "database/invalidFileData.json";
    private static final String INVALID_FILE_FORMAT = "database/invalidFileFormat.json";

    @Test
    public void getDatabaseFilePath() {
        DatabaseManager databaseManager = new DatabaseManager(DatabaseManager.DEFAULT_DATABASE_FILEPATH);
        assertEquals("database/moduleinfo.json", databaseManager.getDatabaseFilePath());
    }

    @Test
    public void readDatabase_validFilePath_success() throws DataLoadingException {
        DatabaseManager databaseManager = new DatabaseManager(VALID_FILE_PATH);
        ModuleData actualModuleData = databaseManager.readDatabase();
        ModuleData typicalModuleData = getTypicalModuleData();
        assertEquals(typicalModuleData, actualModuleData);
    }

    @Test
    public void readDatabase_nullFilePath_throwsNullPointerException() {
        DatabaseManager databaseManager = new DatabaseManager(null);
        assertThrows(NullPointerException.class, databaseManager::readDatabase);
    }

    @Test
    public void readDatabase_missingFilePath_throwsDataLoadingException() {
        DatabaseManager databaseManager = new DatabaseManager(MISSING_FILE_PATH);
        assertThrows(DataLoadingException.class, databaseManager::readDatabase);
    }

    @Test
    public void readDatabase_invalidFileData_throwsDataLoadingException() {
        DatabaseManager databaseManager = new DatabaseManager(INVALID_FILE_DATA);
        assertThrows(DataLoadingException.class, databaseManager::readDatabase);
    }

    @Test
    public void readDatabase_invalidFileFormat_throwsDataLoadingException() {
        DatabaseManager databaseManager = new DatabaseManager(INVALID_FILE_FORMAT);
        assertThrows(DataLoadingException.class, databaseManager::readDatabase);
    }
}
