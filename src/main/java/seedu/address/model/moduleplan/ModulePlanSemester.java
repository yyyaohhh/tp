package seedu.address.model.moduleplan;


import javafx.collections.ObservableList;
import seedu.address.model.module.*;
import seedu.address.model.module.Module;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A semester of the Module Plan timetable.
 */
public class ModulePlanSemester {

    private Year year;
    private Semester semester;

    private final UniqueModuleList modules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
    }

    /**
     * Wraps all data at the Module Plan semester level.
     */
    public ModulePlanSemester(Year year, Semester semester) {
        this.year =  year;
        this.semester = semester;
    }



    /**
     * Replaces the contents of the module list with {@code modules}.
     *
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code m} exists in this semester.
     */
    public boolean hasModule(Module m) {
        requireNonNull(m);
        return modules.contains(m);
    }

    /**
     * Adds a module to the module plan.
     */
    public void addModule(Module m) {
        modules.add(m);
    }

    /**
     * Removes {@code key} from this {@code ModulePlan}.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModules(target, editedModule);
    }

    /**
     * Finds and returns a module using its module code from the internal list of modules.
     *
     * @param code The module code to search for.
     * @return The module with the specified module code, or null if not found.
     * @throws NullPointerException If the provided module code is null.
     */
    public Module findUsingCode(ModuleCode code) {
        requireNonNull(code);
        return modules.find(code);
    }

    /**
     * Calculates and returns the total modular credits of all modules in the collection.
     *
     * @return The total modular credits of all modules in the collection.
     */
    public int totalModularCredits() {
        return modules.modularCredits();
    }

    /**
     * Calculates and returns the total grade points weighted by modular credits of all modules in the collection.
     *
     * @return The total grade points weighted by modular credits of all modules in the collection as a floating-point number.
     */
    public Float totalGradePointsByUnits() {
        return modules.gradePointsWithUnits();
    }

    /**
     * Checks if {@code m} in the modules in this particular semester.
     *
     * @param m The module code to check for.
     * @return Whether the module is among the modules in this semester or not.
     */
    public boolean checkModuleInSemester(Module m) {
        boolean equalYear = this.year.equals(m.getYearTaken());
        boolean equalSemester = this.semester.equals(m.getSemesterTaken());

        if (equalYear && equalSemester) {
            return true;
        }
        return false;
    }

    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public String toString(){
        return "Year " + year.toString() + " " + semester.toString();
    }

}
