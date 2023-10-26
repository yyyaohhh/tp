package seedu.address.database;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;

/**
 * Represents a Module in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DbModule {
    // Identity fields
    private final ModuleName moduleName;
    private final ModuleCode moduleCode;

    // Data fields
    private final Description description;
    private final ModularCredit modularCredit;

    /**
     * Every field must be present and not null.
     */
    @JsonCreator
    public DbModule(ModuleCode moduleCode, ModuleName name, Description description, ModularCredit modularCredit) {
        requireAllNonNull(name, moduleCode, description);
        this.moduleName = name;
        this.moduleCode = moduleCode;
        this.description = description;
        this.modularCredit = modularCredit;
    }

    public ModuleName getName() {
        return moduleName;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Description getDescription() {
        return description;
    }

    public ModularCredit getModularCredit() {
        return modularCredit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Module)) {
            return false;
        }

        DbModule otherModule = (DbModule) other;
        return this.moduleCode.equals(otherModule.moduleCode)
                && this.moduleName.equals(otherModule.moduleName)
                && this.description.equals(otherModule.description)
                && this.modularCredit.equals(otherModule.modularCredit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("moduleCode", moduleCode)
                .add("moduleName", moduleName)
                .add("description", description)
                .add("modularCredit", modularCredit)
                .toString();
    }
}
