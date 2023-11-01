package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * A Model stub that always accept the module being added.
 */
public class ModelStubAcceptingModuleAdded extends ModelStub {
    private final ArrayList<Module> modulesAdded = new ArrayList<>();

    @Override
    public void addModule(Module module) {
        requireNonNull(module);
        if (modulesAdded.contains(module)) {
            throw new DuplicateModuleException();
        }
        modulesAdded.add(module);
    }

    @Override
    public Module getModule(ModuleCode code) {
        requireNonNull(code);
        for (int i = 0; i < modulesAdded.size(); i++) {
            if (modulesAdded.get(i).getModuleCode().equals(code)) {
                return modulesAdded.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteModule(Module module) {
        if (!modulesAdded.contains(module)) {
            throw new ModuleNotFoundException();
        }
        modulesAdded.remove(module);
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modulesAdded.stream().anyMatch(module::isSameModule);
    }

    public ArrayList<Module> getModulesAdded() {
        return this.modulesAdded;
    }
}
