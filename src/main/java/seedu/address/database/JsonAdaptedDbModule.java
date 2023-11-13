package seedu.address.database;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;

/**
 * Jackson-friendly version of {@link Module} for database conversions.
 */
public class JsonAdaptedDbModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String title;
    private final String description;
    private final String moduleCredit;

    /**
     * Constructs a {@code JsonAdaptedDbModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedDbModule(@JsonProperty("moduleCode") String moduleCode,
                               @JsonProperty("title") String title,
                               @JsonProperty("description") String description,
                               @JsonProperty("moduleCredit") String moduleCredit
    ) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.moduleCredit = moduleCredit;
    }

    /**
     * Converts this Jackson-friendly adapted module object into a {@Link Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {

        if (moduleCode == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelCode = new ModuleCode(moduleCode);

        if (title == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleName.class.getSimpleName()));
        }
        final ModuleName modelName = new ModuleName(title);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        if (moduleCredit == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModularCredit.class.getSimpleName()));
        }
        if (!ModularCredit.isValidModularCredit(moduleCredit)) {
            throw new IllegalValueException(ModularCredit.MESSAGE_CONSTRAINTS);
        }
        final ModularCredit modelModularCredit = new ModularCredit(moduleCredit);

        return new Module(modelCode, modelName, modelDescription, modelModularCredit);
    }
}
