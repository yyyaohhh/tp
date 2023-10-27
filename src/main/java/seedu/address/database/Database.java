package seedu.address.database;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ModuleData;

/**
 * The API of the Database component.
 */
public interface Database {

    /**
     * Returns the file path of the database file within the resources folder.
     */
    String getDatabaseFilePath();

    /**
     * Returns database information as a {@link ModuleData} object.
     *
     * @throws DataLoadingException if the data could not be loaded.
     */
    ModuleData readDatabase() throws DataLoadingException;

    /**
     * @see #readDatabase()
     */
    ModuleData readDatabase(String filePath) throws DataLoadingException;
}
