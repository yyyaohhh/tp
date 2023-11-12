package seedu.address.model.moduleplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2030S;
import static seedu.address.testutil.TypicalModules.CS2040S;
import static seedu.address.testutil.TypicalModules.MA2001;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.testutil.ModuleBuilder;


public class ModulePlanSemesterTest {

    private static final Year YEAR = new Year("1");
    private static final Semester SEMESTER = new Semester("2");

    private static final Module MODULE_IN_LIST = CS2030S;
    private static final Module MODULE_NOT_IN_LIST = MA2001;

    private ModulePlanSemester modulePlanSemester;

    private static List<Module> getTypicalModuleList() {
        List<Module> returnList = new ArrayList<>();
        returnList.add(MODULE_IN_LIST);
        returnList.add(CS2040S);
        return returnList;
    }

    @BeforeEach
    public void setUp() {
        modulePlanSemester = new ModulePlanSemester(YEAR, SEMESTER);
    }

    @Test
    public void constructor() {
        ModulePlanSemester m = new ModulePlanSemester(new Year("1"), new Semester("2"));

        assertEquals(modulePlanSemester, m);
        assertEquals(m.getModuleList(), new ArrayList<>());
    }

    @Test
    public void setModules_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanSemester.setModules(null));
    }

    @Test
    public void setModules_validModule_success() {
        List<Module> expectedModules = getTypicalModuleList();

        modulePlanSemester.setModules(expectedModules);
        assertEquals(modulePlanSemester.getModuleList(), expectedModules);
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanSemester.hasModule(null));
    }

    @Test
    public void hasModule_validModule_returnTrue() {
        Module validModule = MODULE_IN_LIST;

        assertFalse(modulePlanSemester.hasModule(validModule));
    }

    @Test
    public void hasModule_invalidModule_returnFalse() {
        Module invalidModule = MODULE_NOT_IN_LIST;

        assertFalse(modulePlanSemester.hasModule(invalidModule));
    }


    @Test
    public void checkModuleBelongToSemester_year0_returnTrue() {
        modulePlanSemester = new ModulePlanSemester(new Year("0"), new Semester("1"));

        Module y0s1 = new ModuleBuilder().withYear("0").withSem("1").build();
        Module y0s2 = new ModuleBuilder().withYear("0").withSem("2").build();
        Module y0st1 = new ModuleBuilder().withYear("0").withSem("ST1").build();
        Module y0st2 = new ModuleBuilder().withYear("0").withSem("ST2").build();


        assertTrue(modulePlanSemester.checkModuleBelongToSemester(y0s1));
        assertTrue(modulePlanSemester.checkModuleBelongToSemester(y0s2));
        assertTrue(modulePlanSemester.checkModuleBelongToSemester(y0st1));
        assertTrue(modulePlanSemester.checkModuleBelongToSemester(y0st2));
    }

    @Test
    public void checkModuleBelongToSemester_y1s2_returnTrue() {
        Module y1s2 = new ModuleBuilder().withYear("1").withSem("2").build();

        assertTrue(modulePlanSemester.checkModuleBelongToSemester(y1s2));
    }

    @Test
    public void checkModuleBelongToSemester_returnFalse() {
        //Same Year Different Sem
        Module y1s1 = new ModuleBuilder().withYear("1").withSem("1").build();
        Module y1st2 = new ModuleBuilder().withYear("1").withSem("ST1").build();
        assertFalse(modulePlanSemester.checkModuleBelongToSemester(y1s1));
        assertFalse(modulePlanSemester.checkModuleBelongToSemester(y1st2));

        //Same Sem Different Year
        Module y2s2 = new ModuleBuilder().withYear("2").withSem("2").build();
        Module y6s2 = new ModuleBuilder().withYear("6").withSem("2").build();
        assertFalse(modulePlanSemester.checkModuleBelongToSemester(y2s2));
        assertFalse(modulePlanSemester.checkModuleBelongToSemester(y6s2));

        //Different Sem Different Year
        Module y0s1 = new ModuleBuilder().withYear("0").withSem("1").build();
        Module y3st2 = new ModuleBuilder().withYear("3").withSem("ST2").build();
        Module y6st1 = new ModuleBuilder().withYear("6").withSem("ST1").build();
        assertFalse(modulePlanSemester.checkModuleBelongToSemester(y0s1));
        assertFalse(modulePlanSemester.checkModuleBelongToSemester(y3st2));
        assertFalse(modulePlanSemester.checkModuleBelongToSemester(y6st1));
    }

    @Test
    public void checkIfSameSemester_year0_returnTrue() {
        modulePlanSemester = new ModulePlanSemester(new Year("0"), new Semester("1"));

        ModulePlanSemester y0s1 = new ModulePlanSemester(new Year("0"), new Semester("1"));
        ModulePlanSemester y0s2 = new ModulePlanSemester(new Year("0"), new Semester("2"));
        ModulePlanSemester y0st1 = new ModulePlanSemester(new Year("0"), new Semester("ST1"));
        ModulePlanSemester y0st2 = new ModulePlanSemester(new Year("0"), new Semester("ST2"));


        assertTrue(modulePlanSemester.checkIfSameSemester(y0s1));
        assertTrue(modulePlanSemester.checkIfSameSemester(y0s2));
        assertTrue(modulePlanSemester.checkIfSameSemester(y0st1));
        assertTrue(modulePlanSemester.checkIfSameSemester(y0st2));
    }

    @Test
    public void checkIfSameSemester_y1s2_returnTrue() {
        ModulePlanSemester y1s2 = new ModulePlanSemester(new Year("1"), new Semester("2"));

        assertTrue(modulePlanSemester.checkIfSameSemester(y1s2));
    }

    @Test
    public void checkIfSameSemester_returnFalse() {
        //Same Year Different Sem
        ModulePlanSemester y1s1 = new ModulePlanSemester(new Year("1"), new Semester("1"));
        ModulePlanSemester y1st2 = new ModulePlanSemester(new Year("1"), new Semester("ST1"));
        assertFalse(modulePlanSemester.checkIfSameSemester(y1s1));
        assertFalse(modulePlanSemester.checkIfSameSemester(y1st2));

        //Same Sem Different Year
        ModulePlanSemester y2s2 = new ModulePlanSemester(new Year("2"), new Semester("2"));
        ModulePlanSemester y6s2 = new ModulePlanSemester(new Year("6"), new Semester("2"));
        assertFalse(modulePlanSemester.checkIfSameSemester(y2s2));
        assertFalse(modulePlanSemester.checkIfSameSemester(y6s2));

        //Different Sem Different Year
        ModulePlanSemester y0s1 = new ModulePlanSemester(new Year("0"), new Semester("1"));
        ModulePlanSemester y3st2 = new ModulePlanSemester(new Year("3"), new Semester("ST2"));
        ModulePlanSemester y6st1 = new ModulePlanSemester(new Year("6"), new Semester("ST1"));
        assertFalse(modulePlanSemester.checkIfSameSemester(y0s1));
        assertFalse(modulePlanSemester.checkIfSameSemester(y3st2));
        assertFalse(modulePlanSemester.checkIfSameSemester(y6st1));
    }



    @Test
    public void toString_year0_returnAdvancePlacement() {
        String expectedString = "Adv Placement";

        ModulePlanSemester y0s1 = new ModulePlanSemester(new Year("0"), new Semester("1"));
        ModulePlanSemester y0s2 = new ModulePlanSemester(new Year("0"), new Semester("2"));
        ModulePlanSemester y0st1 = new ModulePlanSemester(new Year("0"), new Semester("ST1"));
        ModulePlanSemester y0st2 = new ModulePlanSemester(new Year("0"), new Semester("ST2"));

        assertEquals(y0s1.toString(), expectedString);
        assertEquals(y0s2.toString(), expectedString);
        assertEquals(y0st1.toString(), expectedString);
        assertEquals(y0st2.toString(), expectedString);
    }

    @Test
    public void toString_normalYear_returnCorrect() {
        String expectedString = "Year " + YEAR + " " + SEMESTER;

        assertEquals(modulePlanSemester.toString(), expectedString);
    }



}
