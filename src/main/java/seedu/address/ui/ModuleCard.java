package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ModulePlanSemester;

/**
 * TODO: JavaDoc
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



    public ModuleCard(Module module) {
        super(FXML);

        this.module = module;

        moduleCode.setText(this.module.getModuleCode().toString());

        grade.setText(this.module.getGrade().toString());

    }

}
