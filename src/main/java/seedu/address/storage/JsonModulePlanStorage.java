package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * A class to access ModulePlan data stored as a json file on the hard disk.
 */
public class JsonModulePlanStorage implements ModulePlanStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModulePlanStorage.class);

    private final Path filePath;

    public JsonModulePlanStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getModulePlanFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyModulePlan> readModulePlan() throws DataLoadingException {
        return readModulePlan(filePath);
    }

    /**
     * Similar to {@link #readModulePlan()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<ReadOnlyModulePlan> readModulePlan(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableModulePlan> jsonModulePlan = JsonUtil.readJsonFile(
                filePath, JsonSerializableModulePlan.class);
        if (!jsonModulePlan.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonModulePlan.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveModulePlan(ReadOnlyModulePlan modulePlan) throws IOException {
        saveModulePlan(modulePlan, filePath);
    }

    /**
     * Similar to {@link #saveModulePlan(ReadOnlyModulePlan)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveModulePlan(ReadOnlyModulePlan modulePlan, Path filePath) throws IOException {
        requireNonNull(modulePlan);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModulePlan(modulePlan), filePath);
    }
}
