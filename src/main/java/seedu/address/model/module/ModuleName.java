package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's name in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ModuleName {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not start with space.";


    public final String moduleName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ModuleName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.moduleName = name;
    }

    /**
     * Returns true for any given string.
     */
    public static boolean isValidName(String test) {
        return true;
    }

    @Override
    public String toString() {
        return moduleName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleName)) {
            return false;
        }

        ModuleName otherName = (ModuleName) other;
        return moduleName.equals(otherName.moduleName);
    }

    @Override
    public int hashCode() {
        return moduleName.hashCode();
    }
}
