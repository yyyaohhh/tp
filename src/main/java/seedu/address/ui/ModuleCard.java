package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleCard.fxml";


    public final Module module;

    @FXML
    private HBox moduleCardPane;
    @FXML
    private Label moduleCode;
    @FXML
    private Label grade;


    /**
     * Creates a {@code ModuleCard} with the given {@code Module} to display.
     */
    public ModuleCard(Module module) {
        super(FXML);

        this.module = module;

        moduleCode.setText(this.module.getModuleCode().toString());
        grade.setText(this.module.getGrade().toString());

        // Set label colour according to the grade.
        grade.setStyle("-fx-background-color: " + this.module.getGrade().getColourCode() + ";");


    }

}
