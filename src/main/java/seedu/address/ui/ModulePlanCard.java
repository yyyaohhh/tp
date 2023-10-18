package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


/**
 * TODO: JavaDoc
 */
public class ModulePlanCard extends UiPart<Region> {

    private static final String FXML = "ModulePlanCard.fxml";


    //TODO uncomment when ModulePlan is added.
    //public final ModulePlan modulePlanSemester;

    @FXML
    private VBox modulePlanCardPane;
    @FXML
    private Label semester;
    @FXML
    private FlowPane modules;



    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ModulePlanCard(String semester) {
        super(FXML);

        //Placeholder text
        this.semester.setText(semester);
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));
        modules.getChildren().add(new Label("CS2103"));


        //TODO
//        this.modulePlanSemester = modulePlanSemester;
//        modulePlanSemester.getTags().stream()
//                .sorted(Comparator.comparing(tag -> tag.tagName))
//                .forEach(tag -> modules.getChildren().add(new Label(tag.tagName)));
    }
}
