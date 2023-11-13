package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2040S;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2101;
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
        assertEquals(DESC_CS2040S, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_CS2040S, DESC_CS2040S);

        // null -> returns false
        assertNotEquals(DESC_CS2040S, null);

        // different types -> returns false
        assertNotEquals(5, DESC_CS2040S);

        // different values -> returns false
        assertNotEquals(DESC_CS2040S, DESC_CS2101);

        // different year -> returns false
        EditModuleDescriptor editedCS2040S = new EditModuleDescriptorBuilder(DESC_CS2040S)
                .withYear(VALID_YEAR_CS2101).build();
        assertNotEquals(DESC_CS2040S, editedCS2040S);

        // different semester -> returns false
        editedCS2040S = new EditModuleDescriptorBuilder(DESC_CS2040S).withSemester(VALID_SEMESTER_CS2101).build();
        assertNotEquals(DESC_CS2040S, editedCS2040S);

        // different grade -> returns false
        editedCS2040S = new EditModuleDescriptorBuilder(DESC_CS2040S).withGrade(VALID_GRADE_CS2101).build();
        assertNotEquals(DESC_CS2040S, editedCS2040S);
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
