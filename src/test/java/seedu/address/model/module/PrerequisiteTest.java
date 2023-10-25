package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2030S;
import static seedu.address.testutil.TypicalModules.CS2040S;
import static seedu.address.testutil.TypicalModules.CS2101;


import org.junit.jupiter.api.Test;

public class PrerequisiteTest {
    @Test
    public void addAndPrerequisite() {
        Prerequisite testPrerequisite = new Prerequisite();
        // null module
        assertThrows(NullPointerException.class, () -> testPrerequisite.addAndPrerequisite(null));

        testPrerequisite.addAndPrerequisite(CS2040S);
        assertTrue(testPrerequisite.isPrerequisite(CS2040S));
    }

    @Test
    public void deleteAndPrerequisite() {
        Prerequisite testPrerequisite = new Prerequisite();
        // null module
        assertThrows(NullPointerException.class, () -> testPrerequisite.deleteAndPrerequisite(null));

        testPrerequisite.deleteAndPrerequisite(CS2040S);
        assertFalse(testPrerequisite.isPrerequisite(CS2040S));
    }

    @Test
    public void addOrPrerequisite() {
        Prerequisite testPrerequisite = new Prerequisite();
        // null module, null orRelationship
        assertThrows(NullPointerException.class, () -> testPrerequisite.addOrPrerequisite(null, null));
        // null module
        assertThrows(NullPointerException.class, () -> testPrerequisite.addOrPrerequisite(null, CS2040S));
        // null orRelationship
        assertThrows(NullPointerException.class, () -> testPrerequisite.addOrPrerequisite(CS2101, null));

        //contains orRelationship
        testPrerequisite.addAndPrerequisite(CS2040S);
        testPrerequisite.addOrPrerequisite(CS2101, CS2040S);
        assertTrue(testPrerequisite.isPrerequisite(CS2101));

        //does not contain orRelationship
        Prerequisite testNoORPrerequisite = new Prerequisite();
        testNoORPrerequisite.addOrPrerequisite(CS2101, CS2040S);
        assertFalse(testNoORPrerequisite.isPrerequisite(CS2101));
    }

    @Test
    public void deleteOrPrerequisite() {
        Prerequisite testPrerequisite = new Prerequisite();
        // null module, null orRelationship
        assertThrows(NullPointerException.class, () -> testPrerequisite.deleteOrPrerequisite(null, null));
        // null module
        assertThrows(NullPointerException.class, () -> testPrerequisite.deleteOrPrerequisite(null, CS2040S));
        // null orRelationship
        assertThrows(NullPointerException.class, () -> testPrerequisite.deleteOrPrerequisite(CS2101, null));

        //contains orRelationship
        testPrerequisite.addAndPrerequisite(CS2040S);
        testPrerequisite.addOrPrerequisite(CS2101, CS2040S);
        testPrerequisite.deleteOrPrerequisite(CS2101, CS2040S);
        assertFalse(testPrerequisite.isPrerequisite(CS2101));

        //wrong orRelationship, not bring deleted
        Prerequisite testNoORPrerequisite = new Prerequisite();
        testNoORPrerequisite.addAndPrerequisite(CS2040S);
        testNoORPrerequisite.addOrPrerequisite(CS2101, CS2040S);
        testNoORPrerequisite.deleteOrPrerequisite(CS2101, CS2030S);
        assertTrue(testNoORPrerequisite.isPrerequisite(CS2101));
    }

    @Test
    public void isPrerequisite() {
        Prerequisite testPrerequisite = new Prerequisite();
        assertThrows(NullPointerException.class, () -> testPrerequisite.isPrerequisite(null));

        testPrerequisite.addAndPrerequisite(CS2040S);
        assertTrue(testPrerequisite.isPrerequisite(CS2040S));
        assertFalse(testPrerequisite.isPrerequisite(CS2101));
    }

    @Test
    public void equals() {
        Prerequisite cs2030s = new Prerequisite();
        cs2030s.addAndPrerequisite(CS2030S);

        Prerequisite cs2040s = new Prerequisite();
        cs2040s.addAndPrerequisite(CS2040S);

        //same object
        assertTrue(cs2030s.equals(cs2030s));

        // null -> returns false
        assertFalse(cs2030s.equals(null));

        // different type -> returns false
        assertFalse(cs2030s.equals(5));

        // different AND prerequisites -> returns false
        assertFalse(cs2030s.equals(cs2040s));

        // different OR prerequisites -> returns false
        Prerequisite cs2101 = new Prerequisite();
        cs2101.addAndPrerequisite(CS2101);
        cs2101.addOrPrerequisite(CS2030S, CS2101);
        Prerequisite cs2101Compare = new Prerequisite();
        cs2101Compare.addAndPrerequisite(CS2101);
        cs2101Compare.addOrPrerequisite(CS2040S, CS2101);

        assertFalse(cs2101.equals(cs2101Compare));
    }

    @Test
    public void toStringMethod() {
        Prerequisite cs2101 = new Prerequisite();
        cs2101.addAndPrerequisite(CS2101);
        cs2101.addAndPrerequisite(CS2030S);
        cs2101.addOrPrerequisite(CS2040S, CS2101);
        String expected = "(CS2101 OR CS2040S) AND (CS2030S)";
        assertEquals(expected, cs2101.toString());
    }
}
