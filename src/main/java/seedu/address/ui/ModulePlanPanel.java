package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.moduleplan.ModulePlanSemester;

/**
 * Panel containing the list of persons.
 */
public class ModulePlanPanel extends UiPart<Region> {
    private static final String FXML = "ModulePlanPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModulePlanPanel.class);

    @FXML
    private ListView<ModulePlanSemester> modulePlanView;

    /**
     * Creates a {@code ModulePlanPanel} with the given {@code ObservableList}.
     */
    public ModulePlanPanel(ObservableList<ModulePlanSemester> modulePlan) {
        super(FXML);

        modulePlanView.setItems(modulePlan);
        modulePlanView.setCellFactory(listView -> new ModulePlanViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ModulePlanSemester}
     * using a {@code ModulePlanCard}.
     */
    class ModulePlanViewCell extends ListCell<ModulePlanSemester> {
        @Override
        protected void updateItem(ModulePlanSemester semester, boolean empty) {
            super.updateItem(semester, empty);

            if (empty || semester == null) {
                setGraphic(null);
                setText(null);
            } else {
                ModulePlanCard m = new ModulePlanCard(semester);
                setGraphic(m.getRoot());
            }
        }
    }

}
