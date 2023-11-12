package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2040S;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Description;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

public class JsonAdaptedModuleTest {
    private static final String INVALID_CODE = "ABCDE12345ABC";
    private static final String INVALID_YEAR = "one";
    private static final String INVALID_SEMESTER = "S1";
    private static final String INVALID_GRADE = "G";
    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_MODULAR_CREDIT = "-1";

    private static final String VALID_CODE = CS2040S.getModuleCode().toString();
    private static final String VALID_YEAR = CS2040S.getYearTaken().toString();
    private static final String VALID_SEMESTER = CS2040S.getSemesterTaken().getSemesterString();
    private static final String VALID_GRADE = CS2040S.getGrade().toString();
    private static final String VALID_NAME = CS2040S.getName().toString();
    private static final String VALID_DESCRIPTION = CS2040S.getDescription().toString();
    private static final String VALID_MODULAR_CREDIT = CS2040S.getModularCredit().toString();

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        JsonAdaptedModule module = new JsonAdaptedModule(CS2040S);
        assertEquals(CS2040S, module.toModelType());
    }

    @Test
    public void toModelType_invalidCode_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(INVALID_CODE, VALID_YEAR, VALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullCode_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(null, VALID_YEAR, VALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidYear_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, INVALID_YEAR, VALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = Year.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullYear_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, null, VALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidSemester_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, INVALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = Semester.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullSemester_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, null, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Semester.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, VALID_SEMESTER, INVALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = Grade.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, VALID_SEMESTER, null,
                        VALID_NAME, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, VALID_SEMESTER, VALID_GRADE,
                        null, VALID_DESCRIPTION, VALID_MODULAR_CREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, VALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, null, VALID_MODULAR_CREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidMC_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, VALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, INVALID_MODULAR_CREDIT);
        String expectedMessage = ModularCredit.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullMC_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, VALID_YEAR, VALID_SEMESTER, VALID_GRADE,
                        VALID_NAME, VALID_DESCRIPTION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModularCredit.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }


}
