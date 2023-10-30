package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * API of the Storage component
 */
public interface Storage extends ModulePlanStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getModulePlanFilePath();

    @Override
    Optional<ReadOnlyModulePlan> readModulePlan() throws DataLoadingException;

    @Override
    void saveModulePlan(ReadOnlyModulePlan addressBook) throws IOException;

}
