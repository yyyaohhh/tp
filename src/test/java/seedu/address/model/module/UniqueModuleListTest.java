package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2030S;
import static seedu.address.testutil.TypicalModules.CS2040S;
import static seedu.address.testutil.TypicalModules.IS6000;
import static seedu.address.testutil.TypicalModules.MA2001;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.testutil.ModuleBuilder;


public class UniqueModuleListTest {

    private static final Module MODULE_IN_LIST = CS2030S;
    private static final Module MODULE_NOT_IN_LIST = MA2001;

    private UniqueModuleList modules;

    private static List<Module> getTypicalModuleList() {
        List<Module> returnList = new ArrayList<>();
        returnList.add(CS2030S);
        returnList.add(CS2040S);

        return returnList;
    }

    @BeforeEach
    public void setUp() {
        modules = new UniqueModuleList();
        modules.setModules(getTypicalModuleList());
    }
    @Test
    public void constructor() {
        UniqueModuleList moduleList = new UniqueModuleList();
        assertEquals(moduleList.asUnmodifiableObservableList(), new ArrayList<Module>());
    }

    @Test
    public void setModules_null_throwsNullPointerException() {
        List<Module> nullModules = null;
        UniqueModuleList nullModuleList = null;

        assertThrows(NullPointerException.class, () -> modules.setModules(nullModules));
        assertThrows(NullPointerException.class, () -> modules.setModules(nullModuleList));
    }

    @Test
    public void setModules_duplicateModule_throwsDuplicateModuleException() {
        List<Module> duplicateModules = getTypicalModuleList();
        duplicateModules.add(MODULE_IN_LIST);

        assertThrows(DuplicateModuleException.class, () -> modules.setModules(duplicateModules));
    }

    @Test
    public void setModules_validModules_success() {
        List<Module> validModules = Arrays.asList(MODULE_IN_LIST, MODULE_NOT_IN_LIST);
        modules.setModules(validModules);
        assertEquals(modules.asUnmodifiableObservableList(), validModules);

        UniqueModuleList validModuleList = new UniqueModuleList();
        validModuleList.setModules(validModules);
        modules.setModules(validModuleList);
        assertEquals(modules, validModuleList);

    }

    @Test
    public void contains_returnTrue() {
        assertTrue(modules.contains(MODULE_IN_LIST));
    }

    @Test
    public void contains_returnFalse() {
        assertFalse(modules.contains(MODULE_NOT_IN_LIST));

        assertThrows(NullPointerException.class, () -> modules.contains(null));
    }


    @Test
    public void add_duplicateMod_throwsDuplicateModuleException() {
        assertThrows(DuplicateModuleException.class, () -> modules.add(MODULE_IN_LIST));
    }

    @Test
    public void add_nullMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modules.add(null));
    }

    @Test
    public void add_validMod() {
        List<Module> validModules = getTypicalModuleList();
        validModules.add(MODULE_NOT_IN_LIST);
        modules.add(MODULE_NOT_IN_LIST);

        assertEquals(modules.asUnmodifiableObservableList(), validModules);
    }

    @Test
    public void remove_noSuchMod_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> modules.remove(MODULE_NOT_IN_LIST));
    }

    @Test
    public void remove_nullMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modules.remove(null));
    }

    @Test
    public void remove_validMod_success() {
        List<Module> validModules = getTypicalModuleList();
        validModules.remove(MODULE_IN_LIST);

        modules.remove(MODULE_IN_LIST);

        assertEquals(modules.asUnmodifiableObservableList(), validModules);
    }

    @Test
    public void find_validMod_success() {
        Module foundMod = modules.find(MODULE_IN_LIST.getModuleCode());

        assertEquals(foundMod, MODULE_IN_LIST);
    }

    @Test
    public void find_invalidMod_failure() {
        Module foundMod = modules.find(MODULE_NOT_IN_LIST.getModuleCode());

        assertNull(foundMod);
    }


    @Test
    public void modularCredits_success() {
        float expected = CS2030S.getMcValue() + CS2040S.getMcValue();

        assertEquals(modules.modularCredits(), expected);
    }

    @Test
    public void findGradePointsWithUnits_normalMC_success() {
        float expected = CS2030S.getMcValue() * CS2030S.getGrade().gradePoint();
        expected += CS2040S.getMcValue() * CS2040S.getGrade().gradePoint();

        assertEquals(modules.findGradePointsWithUnits(), expected);
    }

    @Test
    public void findGradePointsWithUnits_with0MC_success() {
        modules.add(IS6000);
        float expected = CS2030S.getMcValue() * CS2030S.getGrade().gradePoint();
        expected += CS2040S.getMcValue() * CS2040S.getGrade().gradePoint();

        assertEquals(modules.findGradePointsWithUnits(), expected);
    }

    @Test
    public void findMcsForCap_allWithGradePoint_success() {
        float expected = CS2030S.getMcValue() + CS2040S.getMcValue();
        assertEquals(modules.findMcsForCap(), expected);
    }

    @Test
    public void findMcsForCap_notAllWithGradePoint_success() {
        float expected = CS2030S.getMcValue() + CS2040S.getMcValue();
        Module moduleNoGradePoint = new ModuleBuilder().withGrade("IP").build();
        modules.add(moduleNoGradePoint);

        assertEquals(modules.findMcsForCap(), expected);
    }


    @Test
    public void iterator() {
        List<Module> expectedList = getTypicalModuleList();
        Iterator<Module> iterator = modules.iterator();

        for (Module m: expectedList) {
            assertTrue(iterator.hasNext());
            assertEquals(iterator.next(), m);
        }
        assertFalse(iterator.hasNext());
    }


    @Test
    public void equals() {
        assertEquals(modules, modules);

        UniqueModuleList differentModules = new UniqueModuleList();
        differentModules.setModules(getTypicalModuleList());
        assertEquals(modules, differentModules);

        UniqueModuleList emptyModules = new UniqueModuleList();
        UniqueModuleList emptyModules2 = new UniqueModuleList();
        assertEquals(emptyModules, emptyModules2);

        assertNotEquals(modules, null);

        assertNotEquals(modules, new Integer(0));

        UniqueModuleList modulesWithExtraModule = new UniqueModuleList();
        modulesWithExtraModule.setModules(getTypicalModuleList());
        modulesWithExtraModule.add(MODULE_NOT_IN_LIST);
        assertNotEquals(modules, modulesWithExtraModule);
    }


}
