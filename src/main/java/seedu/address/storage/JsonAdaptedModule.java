package seedu.address.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.*;
import seedu.address.model.module.Module;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final List<JsonAdaptedLecturer> lecturers = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */

    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("code") String code, @JsonProperty("year") String year,
                             @JsonProperty("sem") String sem, @JsonProperty("grade") String grade,
                             @JsonProperty("name") String name,
                             @JsonProperty("description") String description,
                             @JsonProperty("lecturers") List<JsonAdaptedLecturer> lecturers) {
        this.code = code;
        this.year = year;
        this.sem = sem;
        this.grade = grade;
        this.name = name;
        this.description = description;
        if (lecturers != null) {
            this.lecturers.addAll(lecturers);
        }
    }
    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        code = source.getModuleCode().toString();
        year = source.getYearTaken().toString();
        sem = source.getSemesterTaken().toString();
        grade = source.getGrade().toString();
        name = source.getName().toString();
        description = source.getDescription().toString();
        lecturers.addAll(source.getLecturers().stream()
                .map(JsonAdaptedLecturer::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Lecturer> moduleLecturers = new ArrayList<>();
        for (JsonAdaptedLecturer lecturer : lecturers) {
            moduleLecturers.add(lecturer.toModelType());
        }

        if (code == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName()));
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Semester.class.getSimpleName()));
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

        if(name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleName.class.getSimpleName()));
        }
        if (!ModuleName.isValidName(name)) {
            throw new IllegalValueException(ModuleName.MESSAGE_CONSTRAINTS);
        }
        final ModuleName modelName = new ModuleName(name);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if(!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Lecturer> modelLecturer = new HashSet<>(moduleLecturers);
        return new Module(modelCode, modelYear, modelSem, modelGrade, modelName, modelDescription, modelLecturer);
    }

}
