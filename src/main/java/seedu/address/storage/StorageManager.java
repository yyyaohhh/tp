package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * Manages storage of ModulePlan data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final ModulePlanStorage modulePlanStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ModulePlanStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ModulePlanStorage modulePlanStorage, UserPrefsStorage userPrefsStorage) {
        this.modulePlanStorage = modulePlanStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ModulePlan methods ==============================

    @Override
    public Path getModulePlanFilePath() {
        return modulePlanStorage.getModulePlanFilePath();
    }

    @Override
    public Optional<ReadOnlyModulePlan> readModulePlan() throws DataLoadingException {
        return readModulePlan(modulePlanStorage.getModulePlanFilePath());
    }

    @Override
    public Optional<ReadOnlyModulePlan> readModulePlan(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return modulePlanStorage.readModulePlan(filePath);
    }

    @Override
    public void saveModulePlan(ReadOnlyModulePlan modulePlan) throws IOException {
        saveModulePlan(modulePlan, modulePlanStorage.getModulePlanFilePath());
    }

    @Override
    public void saveModulePlan(ReadOnlyModulePlan modulePlan, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        modulePlanStorage.saveModulePlan(modulePlan, filePath);
    }

}
