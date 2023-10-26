package seedu.address.database;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link DbModuleList}.
 */
public class JsonAdaptedDbModuleList {

    private final List<JsonAdaptedDbModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDatabase} with the given modules.
     */
    @JsonCreator
    public JsonAdaptedDbModuleList(@JsonProperty("modules") List<JsonAdaptedDbModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts the list into the form required in {@code ModuleDatabase}.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DbModuleList toDbModuleList() throws IllegalValueException {
        DbModuleList dbModuleList = new DbModuleList();
        for (JsonAdaptedDbModule jsonAdaptedDbModule : modules) {
            DbModule dbModule = jsonAdaptedDbModule.toModelType();
            dbModuleList.addDbModule(dbModule);
        }
        return dbModuleList;
    }
}
