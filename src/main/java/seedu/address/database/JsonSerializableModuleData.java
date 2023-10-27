package seedu.address.database;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModuleData;

/**
 * Jackson-friendly version of {@link ModuleData}.
 */
public class JsonSerializableModuleData {

    private final List<JsonAdaptedDbModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableModuleData} with the given modules.
     */
    @JsonCreator
    public JsonSerializableModuleData(@JsonProperty("modules") List<JsonAdaptedDbModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts this module data into the model's {@code ModuleData} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModuleData toModelType() throws IllegalValueException {
        ModuleData moduleData = new ModuleData();
        for (JsonAdaptedDbModule m : modules) {
            moduleData.addDbModule(m.toModelType());
        }
        return moduleData;
    }
}
