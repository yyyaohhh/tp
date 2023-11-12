package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Description;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * Jackson-friendly version of {@link Module}.
 */
public class JsonAdaptedModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String code;
    private final String year;
    private final String sem;
    private final String grade;
    private final String name;
    private final String description;
    private final String modularCredit;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */

    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("code") String code,
                             @JsonProperty("year") String year,
                             @JsonProperty("sem") String sem,
                             @JsonProperty("grade") String grade,
                             @JsonProperty("name") String name,
                             @JsonProperty("description") String description,
                             @JsonProperty("modularCredit") String modularCredit) {
        this.code = code;
        this.year = year;
        this.sem = sem;
        this.grade = grade;
        this.name = name;
        this.description = description;
        this.modularCredit = modularCredit;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        code = source.getModuleCode().toString();
        year = source.getYearTaken().toString();
        sem = source.getSemesterTaken().getSemesterString();
        grade = source.getGrade().toString();
        name = source.getName().toString();
        description = source.getDescription().toString();
        modularCredit = source.getModularCredit().toString();
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {

        if (code == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(code)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelCode = new ModuleCode(code);

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName()));
        }
        if (!Year.isValidYear(year)) {
            throw new IllegalValueException(Year.MESSAGE_CONSTRAINTS);
        }
        final Year modelYear = new Year(year);

        if (sem == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Semester.class.getSimpleName()));
        }
        if (!Semester.isValidSemester(sem)) {
            throw new IllegalValueException(Semester.MESSAGE_CONSTRAINTS);
        }
        final Semester modelSem = new Semester(sem);

        if (grade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        final Grade modelGrade = new Grade(grade);

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleName.class.getSimpleName()));
        }
        if (!ModuleName.isValidName(name)) {
            throw new IllegalValueException(ModuleName.MESSAGE_CONSTRAINTS);
        }
        final ModuleName modelName = new ModuleName(name);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (modularCredit == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModularCredit.class.getSimpleName()));
        }
        if (!ModularCredit.isValidModularCredit(modularCredit)) {
            throw new IllegalValueException(ModularCredit.MESSAGE_CONSTRAINTS);
        }
        final ModularCredit modelModularCredit = new ModularCredit(modularCredit);

        return new Module(modelCode, modelYear, modelSem, modelGrade, modelName, modelDescription,
                modelModularCredit);
    }

}
