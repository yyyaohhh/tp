package seedu.address.database;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ModuleData;

/**
 * Manages reading of module information from database file.
 */
public class DatabaseManager implements Database {

    public static final String DEFAULT_DATABASE_FILEPATH = "database/moduleinfo.json";

    private static final Logger logger = LogsCenter.getLogger(DatabaseManager.class);

    private final String filePath;

    public DatabaseManager(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getDatabaseFilePath() {
        return filePath;
    }

    @Override
    public ModuleData readDatabase() throws DataLoadingException {
        return readDatabase(this.filePath);
    }

    @Override
    public ModuleData readDatabase(String filePath) throws DataLoadingException {
        requireNonNull(filePath);
        logger.fine("Attempting to parse module information: " + filePath);

        Optional<JsonSerializableModuleData> jsonDatabaseOptional = JsonUtil.readJsonResource(
                filePath, JsonSerializableModuleData.class);

        try {
            return jsonDatabaseOptional.get().toModelType();
        } catch (NoSuchElementException nsee) {
            throw new DataLoadingException(nsee);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }
}
