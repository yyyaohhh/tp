package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2040S;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedModuleTest {
    private static final String INVALID_CODE = "ABCDE12345ABC";
    private static final String INVALID_YEAR = "one";
    private static final String INVALID_SEMESTER = "S1";
    private static final String INVALID_GRADE = "G";

    private static final String VALID_CODE = CS2040S.getModuleCode().toString();
    private static final String VALID_YEAR = CS2040S.getYearTaken().toString();
    private static final String VALID_SEMESTER = CS2040S.getSemesterTaken().toString();
    private static final String VALID_GRADE = CS2040S.getGrade().toString();

//    @Test
//    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
//        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
//        assertEquals(BENSON, person.toModelType());
//    }
//
//    @Test
//    public void toModelType_invalidName_throwsIllegalValueException() {
//        JsonAdaptedPerson person =
//                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullName_throwsIllegalValueException() {
//        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidPhone_throwsIllegalValueException() {
//        JsonAdaptedPerson person =
//                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullPhone_throwsIllegalValueException() {
//        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidEmail_throwsIllegalValueException() {
//        JsonAdaptedPerson person =
//                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullEmail_throwsIllegalValueException() {
//        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidAddress_throwsIllegalValueException() {
//        JsonAdaptedPerson person =
//                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullAddress_throwsIllegalValueException() {
//        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidTags_throwsIllegalValueException() {
//        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
//        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
//        JsonAdaptedPerson person =
//                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
//        assertThrows(IllegalValueException.class, person::toModelType);
//    }

}
