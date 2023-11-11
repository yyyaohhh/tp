package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ModulePlanSemester;

/**
 * TODO: JavaDoc
 */
public class ModulePlanCard extends UiPart<Region> {

    private static final String FXML = "ModulePlanCard.fxml";


    public final ModulePlanSemester modulePlanSemester;

    @FXML
    private VBox modulePlanCardPane;
    @FXML
    private Label semester;
    @FXML
    private ListView<Module> modules;


    /**
     * Creates a {@code ModulePlanCard} with the given {@code ModulePlanSemester} and index to display.
     */
    public ModulePlanCard(ModulePlanSemester modulePlanSemester) {
        super(FXML);

        this.modulePlanSemester = modulePlanSemester;

        semester.setText(this.modulePlanSemester.toString());

        modules.setItems(modulePlanSemester.getModuleList());
        modules.setCellFactory(listView -> new ModulePlanCard.ModuleViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                ModuleCard m = new ModuleCard(module);
                setGraphic(m.getRoot());
            }
        }
    }
}
