package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Lecturer;

/**
 * Jackson-friendly version of {@link Lecturer}.
 */
public class JsonAdaptedLecturer {
    private final String lecturerName;

    /**
     * Constructs a {@code JsonAdaptedLecturer} with the given {@code lecturerName}.
     */
    @JsonCreator
    public JsonAdaptedLecturer(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    /**
     * Converts a given {@code Lecturer} into this class for Jackson use.
     */
    public JsonAdaptedLecturer(Lecturer source) {
        lecturerName = source.lecturerName;
    }

    @JsonValue
    public String getLecturerName() {
        return lecturerName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Lecturer toModelType() throws IllegalValueException {
        if (!Lecturer.isValidLecturer(lecturerName)) {
            throw new IllegalValueException(Lecturer.MESSAGE_CONSTRAINTS);
        }
        return new Lecturer(lecturerName);
    }

}
