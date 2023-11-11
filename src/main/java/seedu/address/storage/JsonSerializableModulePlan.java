package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ModulePlanSemester;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

/**
 * An Immutable ModulePlan that is serializable to JSON format.
 */
@JsonRootName(value = "moduleplan")
public class JsonSerializableModulePlan {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableModulePlan} with the given modules.
     */
    @JsonCreator
    public JsonSerializableModulePlan(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyModulePlan} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableModulePlan}.
     */
    public JsonSerializableModulePlan(ReadOnlyModulePlan source) {
        modules.addAll(
            source.getModulePlanSemesterList().stream()
                .map(ModulePlanSemester::getModuleList)
                .flatMap(ObservableList::stream)
                .map(JsonAdaptedModule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this module plan into the model's {@code ModulePlan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModulePlan toModelType() throws IllegalValueException {
        ModulePlan modulePlan = new ModulePlan();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (modulePlan.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            modulePlan.addModule(module);
        }
        return modulePlan;
    }
}
