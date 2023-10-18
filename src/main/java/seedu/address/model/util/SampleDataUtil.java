package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Description;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Lecturer;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS1231S"), new Year("1"), new Semester("1"), new Grade("A+"),
                new ModuleName("Discrete Structures"),
                new Description("Introduces mathematical tools required the study of Computer Science."),
                Set.of(new Lecturer("Aaron Tan")), new ModularCredit("4")),

            new Module(new ModuleCode("CS2040S"), new Year("1"), new Semester("2"), new Grade("A"),
                new ModuleName("Data Structures and Algorithms"),
                new Description("Covers the design and implementation of efficient data structures and algorithms."),
                Set.of(new Lecturer("Tan Sun Teck")), new ModularCredit("4")),

            new Module(new ModuleCode("CS2101"), new Year("2"), new Semester("1"), new Grade("A-"),
                new ModuleName("Effective Communication for Computing Professionals"),
                new Description("Introduces the necessary conceptual and analytic tools for systematic and rigorous development of software systems."),
                Set.of(new Lecturer("Lee Bu Sung")), new ModularCredit("4")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Module sampleModule : getSampleModules()) {
            sampleAb.addModule(sampleModule);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
