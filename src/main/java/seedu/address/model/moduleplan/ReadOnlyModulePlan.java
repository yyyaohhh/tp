package seedu.address.model.moduleplan;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a module plan
 */
public interface ReadOnlyModulePlan {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<ModulePlanSemester> getModulePlanSemesterList();

}
