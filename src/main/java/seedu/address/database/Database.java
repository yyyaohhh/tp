package seedu.address.database;

import seedu.address.commons.exceptions.DataLoadingException;

/**
 * The API of the Database component.
 */
public interface Database {

    /**
     * Returns the file path of the database file within the resources folder.
     */
    String getDatabaseFilePath();

    /**
     * Returns database information as a {@link DbModuleList}.
     *
     * @throws DataLoadingException if the data could not be loaded.
     */
    DbModuleList readDatabase() throws DataLoadingException;

    /**
     * @see #readDatabase()
     */
    DbModuleList readDatabase(String filePath) throws DataLoadingException;
}
