package seedu.address.model.moduleplan;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2106;
import static seedu.address.testutil.TypicalModules.CS3230;
import static seedu.address.testutil.TypicalModules.GEA1000;
import static seedu.address.testutil.TypicalModules.MA2001;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.moduleplan.exceptions.DuplicateSemesterException;
import seedu.address.model.moduleplan.exceptions.SemesterNotFoundException;

public class ModulePlanSemesterListTest {

    private static final Module MODULE_IN_LIST = GEA1000;
    private static final Module MODULE_NOT_IN_DEFAULT_LIST = CS2106;
    private static final Module MODULE_NOT_IN_LIST_INVALID_SEM = CS3230;
    private static final Module MODULE_NOT_IN_LIST_VALID_SEM = MA2001;

    private static final ModulePlanSemester IN_SEM_LIST = new ModulePlanSemester(new Year("1"), new Semester("1"));
    private static final ModulePlanSemester NOT_IN_SEM_LIST = new ModulePlanSemester(new Year("0"), new Semester("2"));


    private ModulePlanSemesterList modulePlanSemesterList;


    private static List<ModulePlanSemester> getTypicalModulePlanSemesterList() {
        ModulePlanSemester y1s1 = new ModulePlanSemester(new Year("1"), new Semester("1"));
        y1s1.addModule(GEA1000);
        ModulePlanSemester y1s2 = new ModulePlanSemester(new Year("1"), new Semester("2"));

        List<ModulePlanSemester> returnList = new ArrayList<>();
        returnList.add(y1s1);
        returnList.add(y1s2);
        return returnList;
    }



    @BeforeEach
    public void setUp() {
        modulePlanSemesterList = new ModulePlanSemesterList();
        modulePlanSemesterList.setSemesters(getTypicalModulePlanSemesterList());
    }
    @Test
    public void constructor() {
        ModulePlanSemesterList m = new ModulePlanSemesterList();
        assertEquals(ModulePlanSemesterList.DEFAULT_SEMESTERS, m.asUnmodifiableObservableList());
    }

    @Test
    public void setSemester_null_throwsNullPointerException() {
        List<ModulePlanSemester> nullSemesters = null;
        ModulePlanSemesterList nullModulePlanSemesterList = null;

        assertThrows(NullPointerException.class, () -> modulePlanSemesterList.setSemesters(nullSemesters));
        assertThrows(NullPointerException.class, () -> modulePlanSemesterList.setSemesters(nullModulePlanSemesterList));
    }

    @Test
    public void setSemester_duplicateSemester_throwsDuplicateSemesterException() {
        List<ModulePlanSemester> duplicateSemesters = getTypicalModulePlanSemesterList();
        ModulePlanSemester duplicateSem = duplicateSemesters.get(0).copy();
        duplicateSemesters.add(duplicateSem);

        assertThrows(DuplicateSemesterException.class, () -> modulePlanSemesterList.setSemesters(duplicateSemesters));
    }

    @Test
    public void setSemester_validSemester_success() {
        List<ModulePlanSemester> validSemesters = getTypicalModulePlanSemesterList();
        modulePlanSemesterList.setSemesters(validSemesters);
        assertEquals(modulePlanSemesterList.asUnmodifiableObservableList(), validSemesters);

        ModulePlanSemesterList validMpsl = new ModulePlanSemesterList();
        validMpsl.setSemesters(validSemesters);
        modulePlanSemesterList.setSemesters(validMpsl);
        assertEquals(modulePlanSemesterList, validMpsl);

    }

    @Test
    public void containsSemester_returnTrue() {
        ModulePlanSemester validSem = modulePlanSemesterList.asUnmodifiableObservableList().get(0);

        assertTrue(modulePlanSemesterList.containsSemester(validSem));

        //Test for Year 0 Semesters
        ModulePlanSemester y0s1 = new ModulePlanSemester(new Year("0"), new Semester("1"));
        ModulePlanSemester y0s2 = new ModulePlanSemester(new Year("0"), new Semester("2"));
        modulePlanSemesterList.addSemester(y0s1);

        assertTrue(modulePlanSemesterList.containsSemester(y0s2));
    }

    @Test
    public void containsSemester_returnFalse() {
        assertFalse(modulePlanSemesterList.containsSemester(NOT_IN_SEM_LIST));

        assertThrows(NullPointerException.class, () -> modulePlanSemesterList.containsSemester(null));
    }

    @Test
    public void addSemester_duplicateSem_throwsDuplicateSemesterException() {
        ModulePlanSemester duplicateSem = modulePlanSemesterList.asUnmodifiableObservableList().get(0);

        assertThrows(DuplicateSemesterException.class, () -> modulePlanSemesterList.addSemester(duplicateSem));
    }

    @Test
    public void addSemester_nullSem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanSemesterList.addSemester(null));
    }

    @Test
    public void addSemester_validSem() {
        List<ModulePlanSemester> validSemesters = getTypicalModulePlanSemesterList();
        validSemesters.add(NOT_IN_SEM_LIST);
        Collections.sort(validSemesters);
        modulePlanSemesterList.addSemester(NOT_IN_SEM_LIST);

        assertEquals(modulePlanSemesterList.asUnmodifiableObservableList(), validSemesters);
    }

    @Test
    public void removeSemester_noSuchSem_throwsSemesterNotFoundException() {
        assertThrows(SemesterNotFoundException.class, () -> modulePlanSemesterList.removeSemester(NOT_IN_SEM_LIST));
    }

    @Test
    public void removeSemester_nullSem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanSemesterList.removeSemester(null));
    }

    @Test
    public void removeSemester_validSem() {
        List<ModulePlanSemester> validSemesters = getTypicalModulePlanSemesterList();
        validSemesters.remove(IN_SEM_LIST);
        Collections.sort(validSemesters);
        modulePlanSemesterList.removeSemester(IN_SEM_LIST);

        assertEquals(modulePlanSemesterList.asUnmodifiableObservableList(), validSemesters);
    }


    @Test
    public void containsModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanSemesterList.containsModule(null));
    }

    @Test
    public void containsModule_validModule_returnTrue() {
        Module validModule = MODULE_IN_LIST;

        assertTrue(modulePlanSemesterList.containsModule(validModule));
    }


    @Test
    public void addModule_duplicateModule_throwsDuplicateModuleException() {
        assertThrows(DuplicateModuleException.class, () -> modulePlanSemesterList.addModule(MODULE_IN_LIST));
    }

    @Test
    public void addModule_moduleSemPresent_success() {
        List<ModulePlanSemester> expectedList = getTypicalModulePlanSemesterList();
        expectedList.get(1).addModule(MODULE_NOT_IN_LIST_VALID_SEM);

        modulePlanSemesterList.addModule(MODULE_NOT_IN_LIST_VALID_SEM);
        assertEquals(modulePlanSemesterList.asUnmodifiableObservableList(), expectedList);
    }

    @Test
    public void addModule_moduleSemNotPresent_success() {
        List<ModulePlanSemester> expectedList = getTypicalModulePlanSemesterList();
        expectedList.add(new ModulePlanSemester(MODULE_NOT_IN_LIST_INVALID_SEM.getYearTaken(),
                MODULE_NOT_IN_LIST_INVALID_SEM.getSemesterTaken()));
        expectedList.get(2).addModule(MODULE_NOT_IN_LIST_INVALID_SEM);

        modulePlanSemesterList.addModule(MODULE_NOT_IN_LIST_INVALID_SEM);
        assertEquals(modulePlanSemesterList.asUnmodifiableObservableList(), expectedList);
    }

    @Test
    public void removeModule_moduleNotPresent_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () ->
                modulePlanSemesterList.removeModule(MODULE_NOT_IN_LIST_VALID_SEM));
        assertThrows(ModuleNotFoundException.class, () ->
                modulePlanSemesterList.removeModule(MODULE_NOT_IN_LIST_INVALID_SEM));
    }

    @Test
    public void removeModule_moduleInDefaultList_success() {
        List<ModulePlanSemester> expectedList = getTypicalModulePlanSemesterList();
        expectedList.get(0).removeModule(MODULE_IN_LIST);

        modulePlanSemesterList.removeModule(MODULE_IN_LIST);
        assertEquals(modulePlanSemesterList.asUnmodifiableObservableList(), expectedList);
    }

    @Test
    public void removeModule_moduleNotInDefaultList_success() {
        List<ModulePlanSemester> withNonDefaultSemList = getTypicalModulePlanSemesterList();
        withNonDefaultSemList.add(new ModulePlanSemester(MODULE_NOT_IN_DEFAULT_LIST.getYearTaken(),
                MODULE_NOT_IN_DEFAULT_LIST.getSemesterTaken()));
        withNonDefaultSemList.get(2).addModule(MODULE_NOT_IN_DEFAULT_LIST);
        modulePlanSemesterList.setSemesters(withNonDefaultSemList);

        List<ModulePlanSemester> expectedList = getTypicalModulePlanSemesterList();

        modulePlanSemesterList.removeModule(MODULE_NOT_IN_DEFAULT_LIST);
        assertEquals(modulePlanSemesterList.asUnmodifiableObservableList(), expectedList);
    }

    @Test
    public void findModule_moduleNotPresent_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () ->
                modulePlanSemesterList.findModule(MODULE_NOT_IN_LIST_VALID_SEM.getModuleCode()));
        assertThrows(ModuleNotFoundException.class, () ->
                modulePlanSemesterList.findModule(MODULE_NOT_IN_LIST_INVALID_SEM.getModuleCode()));
    }

    @Test
    public void findModule_modulePresent_success() {
        assertEquals(MODULE_IN_LIST, modulePlanSemesterList.findModule(MODULE_IN_LIST.getModuleCode()));
    }

    @Test
    public void iterator() {
        List<ModulePlanSemester> expectedList = getTypicalModulePlanSemesterList();
        Iterator<ModulePlanSemester> iterator = modulePlanSemesterList.iterator();

        for (ModulePlanSemester m: expectedList) {
            assertTrue(iterator.hasNext());
            assertEquals(iterator.next(), m);
        }
        assertFalse(iterator.hasNext());
    }


    @Test
    public void equals() {
        assertEquals(modulePlanSemesterList, modulePlanSemesterList);

        ModulePlanSemesterList differentSemesters = new ModulePlanSemesterList();
        differentSemesters.setSemesters(getTypicalModulePlanSemesterList());
        assertEquals(modulePlanSemesterList, differentSemesters);

        ModulePlanSemesterList emptySemesters = new ModulePlanSemesterList();
        ModulePlanSemesterList emptySemesters2 = new ModulePlanSemesterList();
        assertEquals(emptySemesters, emptySemesters2);

        assertNotEquals(modulePlanSemesterList, null);

        assertNotEquals(modulePlanSemesterList, 0);

        ModulePlanSemesterList semWithExtraSem = new ModulePlanSemesterList();
        semWithExtraSem.setSemesters(getTypicalModulePlanSemesterList());
        semWithExtraSem.addSemester(NOT_IN_SEM_LIST);
        assertNotEquals(modulePlanSemesterList, semWithExtraSem);
    }


}
