package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * Represents a storage for {@link seedu.address.model.moduleplan.ModulePlan}.
 */
public interface ModulePlanStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getModulePlanFilePath();

    /**
     * Returns ModulePlan data as a {@link ReadOnlyModulePlan}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyModulePlan> readModulePlan() throws DataLoadingException;

    /**
     * @see #getModulePlanFilePath()
     */
    Optional<ReadOnlyModulePlan> readModulePlan(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyModulePlan} to the storage.
     *
     * @param modulePlan cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveModulePlan(ReadOnlyModulePlan modulePlan) throws IOException;

    /**
     * @see #saveModulePlan(ReadOnlyModulePlan)
     */
    void saveModulePlan(ReadOnlyModulePlan modulePlan, Path filePath) throws IOException;
}
