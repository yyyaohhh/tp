package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import seedu.address.model.module.Module;

/**
 * A Model stub that contains a single module.
 */
public class ModelStubWithModule extends ModelStub {
    private final Module module;

    /**
     * Initializes a ModelStubWithModule with the given module.
     *
     * @param module The module to be associated with the ModelStubWithModule.
     */
    public ModelStubWithModule(Module module) {
        requireNonNull(module);
        this.module = module;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return this.module.isSameModule(module);
    }
}
