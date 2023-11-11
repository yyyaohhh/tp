package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Description;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

public class ParserUtilTest {
    private static final String INVALID_CODE = "123";
    private static final String INVALID_MC = "-1";
    private static final String INVALID_YEAR = "-4";
    private static final String INVALID_SEMESTER = "5";
    private static final String INVALID_GRADE = "Y";

    private static final String VALID_CODE = "CS2030S";
    private static final String VALID_MC = "4";
    private static final String VALID_DESCRIPTION = "This course is a follow up to CS1010.";
    private static final String VALID_YEAR = "1";
    private static final String VALID_SEMESTER = "2";
    private static final String VALID_GRADE = "A";
    private static final String VALID_NAME = "Programming Methodology II";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCode(null));
    }

    @Test
    public void parseCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleCode(INVALID_CODE));
    }

    @Test
    public void parseCode_validValueWithoutWhitespace_returnsName() throws Exception {
        ModuleCode expectedCode = new ModuleCode(VALID_CODE);
        assertEquals(expectedCode, ParserUtil.parseModuleCode(VALID_CODE));
    }

    @Test
    public void parseCode_validValueWithWhitespace_returnsTrimmedCode() throws Exception {
        String codeWithWhitespace = WHITESPACE + VALID_CODE + WHITESPACE;
        ModuleCode expectedCode = new ModuleCode(VALID_CODE);
        assertEquals(expectedCode, ParserUtil.parseModuleCode(codeWithWhitespace));
    }

    @Test
    public void parseMc_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModularCredit(null));
    }

    @Test
    public void parseMc_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModularCredit(INVALID_MC));
    }

    @Test
    public void parseMc_validValueWithoutWhitespace_returnsMC() throws Exception {
        ModularCredit expectedMc = new ModularCredit(VALID_MC);
        assertEquals(expectedMc, ParserUtil.parseModularCredit(VALID_MC));
    }

    @Test
    public void parseMc_validValueWithWhitespace_returnsTrimmedMC() throws Exception {
        String mcWithWhitespace = WHITESPACE + VALID_MC + WHITESPACE;
        ModularCredit expectedMc = new ModularCredit(VALID_MC);
        assertEquals(expectedMc, ParserUtil.parseModularCredit(mcWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }


    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseYear_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseYear(null));
    }

    @Test
    public void parseYear_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithoutWhitespace_returnsYear() throws Exception {
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedYear() throws Exception {
        String yearWithWhitespace = WHITESPACE + VALID_YEAR + WHITESPACE;
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(yearWithWhitespace));
    }

    @Test
    public void parseSemester_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSemester(null));
    }

    @Test
    public void parseSemester_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSemester(INVALID_SEMESTER));
    }

    @Test
    public void parseSemester_validValueWithoutWhitespace_returnsSemester() throws Exception {
        Semester expectedSemester = new Semester(VALID_SEMESTER);
        assertEquals(expectedSemester, ParserUtil.parseSemester(VALID_SEMESTER));
    }

    @Test
    public void parseSemester_validValueWithWhitespace_returnsTrimmedSemester() throws Exception {
        String semesterWithWhitespace = WHITESPACE + VALID_SEMESTER + WHITESPACE;
        Semester expectedSemester = new Semester(VALID_SEMESTER);
        assertEquals(expectedSemester, ParserUtil.parseSemester(semesterWithWhitespace));
    }

    @Test
    public void parseGrade_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGrade(null));
    }

    @Test
    public void parseGrade_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGrade(INVALID_GRADE));
    }

    @Test
    public void parseGrade_validValueWithoutWhitespace_returnsGrade() throws Exception {
        Grade expectedGrade = new Grade(VALID_GRADE);
        assertEquals(expectedGrade, ParserUtil.parseGrade(VALID_GRADE));
    }

    @Test
    public void parseGrade_validValueWithWhitespace_returnsTrimmedGrade() throws Exception {
        String gradeWithWhitespace = WHITESPACE + VALID_GRADE + WHITESPACE;
        Grade expectedGrade = new Grade(VALID_GRADE);
        assertEquals(expectedGrade, ParserUtil.parseGrade(gradeWithWhitespace));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleName(null));
    }


    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        ModuleName expectedName = new ModuleName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseModuleName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        ModuleName expectedName = new ModuleName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseModuleName(nameWithWhitespace));
    }
}
