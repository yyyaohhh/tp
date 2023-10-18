package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of persons.
 */
public class ModulePlanPanel extends UiPart<Region> {
    private static final String FXML = "ModulePlanPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModulePlanPanel.class);

    //TODO modify class.
    @FXML
    private ListView<String> modulePlanView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ModulePlanPanel(/*ObservableList<ModulePlan> modulePlan*/) {
        super(FXML);

        //Placeholder values
        ObservableList<String> items = FXCollections.observableArrayList("Y1S1", "Y1S2", "Y2S1", "Y2S2", "Y3S1", "Y3S2", "Y4S1", "Y4S2");

        modulePlanView.setItems(items);
        modulePlanView.setCellFactory(listView -> new ModulePlanViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ModulePlanViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String string, boolean empty) {
            super.updateItem(string, empty);

            if (empty || string == null) {
                setGraphic(null);
                setText(null);
            } else {
                //Todo change constructor params
                ModulePlanCard m = new ModulePlanCard(string);
                setGraphic(m.getRoot());
            }
        }
    }

}
