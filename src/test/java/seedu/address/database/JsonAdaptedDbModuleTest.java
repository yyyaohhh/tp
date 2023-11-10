package seedu.address.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.database.JsonAdaptedDbModule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2040S;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;

public class JsonAdaptedDbModuleTest {
    private static final String INVALID_CODE = "ABCDE12345ABC";
    private static final String INVALID_MODULECREDIT = "one";

    private static final String VALID_CODE = CS2040S.getModuleCode().toString();
    private static final String VALID_NAME = CS2040S.getName().toString();
    private static final String VALID_DESCRIPTION = CS2040S.getDescription().toString();
    private static final String VALID_MODULECREDIT = CS2040S.getModularCredit().toString();

    private static final Module VALID_DBMODULE = new Module(CS2040S.getModuleCode(), CS2040S.getName(),
            CS2040S.getDescription(), CS2040S.getModularCredit());

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        JsonAdaptedDbModule module = new JsonAdaptedDbModule(
                VALID_CODE, VALID_NAME, VALID_DESCRIPTION, VALID_MODULECREDIT);
        assertEquals(VALID_DBMODULE, module.toModelType());
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedDbModule module = new JsonAdaptedDbModule(
                INVALID_CODE, VALID_NAME, VALID_DESCRIPTION, VALID_MODULECREDIT);
        assertThrows(IllegalValueException.class, ModuleCode.MESSAGE_CONSTRAINTS, module::toModelType);
    }

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        JsonAdaptedDbModule module = new JsonAdaptedDbModule(
                null, VALID_NAME, VALID_DESCRIPTION, VALID_MODULECREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullModuleName_throwsIllegalValueException() {
        JsonAdaptedDbModule module = new JsonAdaptedDbModule(
                VALID_CODE, null, VALID_DESCRIPTION, VALID_MODULECREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedDbModule module = new JsonAdaptedDbModule(
                VALID_CODE, VALID_NAME, null, VALID_MODULECREDIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidModularCredit_throwsIllegalValueException() {
        JsonAdaptedDbModule module = new JsonAdaptedDbModule(
                VALID_CODE, VALID_NAME, VALID_DESCRIPTION, INVALID_MODULECREDIT);
        assertThrows(IllegalValueException.class, ModularCredit.MESSAGE_CONSTRAINTS, module::toModelType);
    }

    @Test
    public void toModelType_nullModularCredit_throwsIllegalValueException() {
        JsonAdaptedDbModule module = new JsonAdaptedDbModule(
                VALID_CODE, VALID_NAME, VALID_DESCRIPTION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModularCredit.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }
}
