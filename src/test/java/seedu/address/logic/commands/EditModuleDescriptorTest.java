package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_CS2101;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.testutil.EditModuleDescriptorBuilder;

public class EditModuleDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditModuleDescriptor descriptorWithSameValues = new EditModuleDescriptor(DESC_CS2040S);
        assertTrue(DESC_CS2040S.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2040S.equals(DESC_CS2040S));

        // null -> returns false
        assertFalse(DESC_CS2040S.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2040S.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2040S.equals(DESC_CS2101));

        // different year -> returns false
        EditModuleDescriptor editedCS2040S = new EditModuleDescriptorBuilder(DESC_CS2040S)
                .withYear(VALID_YEAR_CS2101).build();
        assertFalse(DESC_CS2040S.equals(editedCS2040S));

        // different semester -> returns false
        editedCS2040S = new EditModuleDescriptorBuilder(DESC_CS2040S).withSemester(VALID_SEMESTER_CS2101).build();
        assertFalse(DESC_CS2040S.equals(editedCS2040S));

        // different grade -> returns false
        editedCS2040S = new EditModuleDescriptorBuilder(DESC_CS2040S).withGrade(VALID_GRADE_CS2101).build();
        assertFalse(DESC_CS2040S.equals(editedCS2040S));
    }

    @Test
    public void toStringMethod() {
        EditModuleDescriptor editPersonDescriptor = new EditModuleDescriptor();
        String expected = EditModuleDescriptor.class.getCanonicalName() + "{year="
                + editPersonDescriptor.getYear().orElse(null) + ", semester="
                + editPersonDescriptor.getSemester().orElse(null) + ", grade="
                + editPersonDescriptor.getGrade().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
