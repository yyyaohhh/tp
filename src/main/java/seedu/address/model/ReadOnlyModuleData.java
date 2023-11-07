package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;

/**
 * Unmodifiable view of a database module list.
 */
public interface ReadOnlyModuleData {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();
}
