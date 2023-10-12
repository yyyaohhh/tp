package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;



public class ModulePlanCard extends UiPart<Region> {

    private static final String FXML = "ModulePlanCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    public ModulePlanCard() {
        super(FXML);

        //TODO
//        this.modulePlanSemester = modulePlanSemester;
//        modulePlanSemester.getTags().stream()
//                .sorted(Comparator.comparing(tag -> tag.tagName))
//                .forEach(tag -> modules.getChildren().add(new Label(tag.tagName)));
    }
}
