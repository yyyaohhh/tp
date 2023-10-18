package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.moduleplan.ModulePlanSemester;


public class ModulePlanCard extends UiPart<Region> {

    private static final String FXML = "ModulePlanCard.fxml";


    public final ModulePlanSemester modulePlanSemester;

    @FXML
    private VBox modulePlanCardPane;
    @FXML
    private Label semester;
    @FXML
    private FlowPane modules;



    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ModulePlanCard(ModulePlanSemester modulePlanSemester) {
        super(FXML);

        this.modulePlanSemester = modulePlanSemester;

        semester.setText(this.modulePlanSemester.toString());

        modulePlanSemester.getModuleList().stream()
                .forEach(module -> modules.getChildren().add(new Label(module.getModuleCode().toString())));
    }
}
