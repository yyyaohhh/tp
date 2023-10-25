package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;

/**
 * Represents a Module's prerequisites in the system.
 * */
public class Prerequisite {
    private LinkedList<LinkedList<Module>> prerequisites;

    /**
     * Constructs a new {@code Prerequisite} where modules are stored in linked lists.
     */
    public Prerequisite() {
        this.prerequisites = new LinkedList<>();
    }

    /**
     * Adds a module that is prerequisites with an "AND" relationship with the list of modules.
     *
     * @param andPrerequisite A module that is prerequisite and must all be completed to satisfy the requirement.
     */
    public void addAndPrerequisite(Module andPrerequisite) {
        requireNonNull(andPrerequisite);

        LinkedList<Module> toAdd = new LinkedList<>();
        toAdd.add(andPrerequisite);
        this.prerequisites.add(toAdd);
    }

    /**
     * Deletes a module that is prerequisites with an "AND" relationship with the list of modules.
     *
     * @param toDelete A module to be removed from the list of "AND" prerequisites.
     */
    public void deleteAndPrerequisite(Module toDelete) {
        requireNonNull(toDelete);

        for (int i = 0; i < prerequisites.size(); i++) {
            if (prerequisites.get(i).contains(toDelete)) {
                prerequisites.remove(i);
            }
        }
    }

    /**
     * Adds the specified module as an "OR" prerequisite for this module in the context of another module.
     *
     * @param toAdd          The module to be added as an "OR" prerequisite.
     * @param orRelationship The module is an "OR" prerequisite to the module being added.
     */
    public void addOrPrerequisite(Module toAdd, Module orRelationship) {
        requireNonNull(toAdd);
        requireNonNull(orRelationship);

        for (int i = 0; i < prerequisites.size(); i++) {
            if (prerequisites.get(i).contains(orRelationship)) {
                prerequisites.get(i).add(toAdd);
            }
        }
    }

    /**
     * Deletes the specified module from the list of "OR" prerequisites for this module in the context of another module.
     *
     * @param toDelete       The module to be removed from the list of "OR" prerequisites.
     * @param orRelationship TThe module is an "OR" prerequisite to the module being removed.
     */
    public void deleteOrPrerequisite(Module toDelete, Module orRelationship) {
        requireNonNull(toDelete);
        requireNonNull(orRelationship);

        for (int i = 0; i < prerequisites.size(); i++) {
            if (prerequisites.get(i).contains(orRelationship)) {
                prerequisites.get(i).remove(toDelete);
            }
        }
    }

    /**
     * Checks if the given module is a prerequisite for this module.
     *
     * @param module The module to be checked as a prerequisite.
     * @return true if the given module is a prerequisite for this module, false otherwise.
     */
    public boolean isPrerequisite(Module module) {
        requireNonNull(module);

        for (int i = 0; i < prerequisites.size(); i++) {
            if (prerequisites.get(i).contains(module)) {
                return true;
            }
        }
        return false;
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

        Prerequisite otherPrerequisite = (Prerequisite) other;
        return this.prerequisites.equals(otherPrerequisite.prerequisites);
    }

    /**
     * Returns a string representation of the prerequisites for this module in a readable format.
     *
     * @return A string containing the prerequisites for this module with "AND" and "OR" relationships.
     */
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < prerequisites.size(); i++) {
            output = output.concat("(");
            for (int j = 0; j < prerequisites.get(i).size(); j++) {
                output = output.concat(prerequisites.get(i).get(j).getModuleCode().toString());
                if (j != prerequisites.get(i).size() - 1) {
                    output = output.concat(" OR ");
                }
            }
            output = output.concat(")");
            if (i != prerequisites.size() - 1) {
                output = output.concat(" AND ");
            }
        }
        return output;
    }

}
