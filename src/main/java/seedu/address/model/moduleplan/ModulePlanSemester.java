package seedu.address.model.moduleplan;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.module.Year;


/**
 * A semester of the Module Plan timetable.
 */
public class ModulePlanSemester implements Comparable<ModulePlanSemester> {


    private Year year;
    private Semester semester;

    private final UniqueModuleList modules = new UniqueModuleList();

    /**
     * Wraps all data at the Module Plan semester level.
     */
    public ModulePlanSemester(Year year, Semester semester) {
        requireNonNull(year);
        requireNonNull(semester);
        this.year = year;
        this.semester = semester;
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     */
    public void setModules(List<Module> modules) {
        requireNonNull(modules);
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
        requireNonNull(m);
        modules.add(m);
    }

    /**
     * Removes {@code key} from this {@code ModulePlan}.
     */
    public void removeModule(Module key) {
        requireNonNull(key);
        modules.remove(key);
    }


    /**
     * Finds and returns a module using its module code from the internal list of modules.
     *
     * @param code The module code to search for.
     * @return The module with the specified module code, or null if not found.
     * @throws NullPointerException If the provided module code is null.
     */
    public Module getModule(ModuleCode code) {
        requireNonNull(code);
        return modules.find(code);
    }

    /**
     * Calculates and returns the total modular credits of all modules in the collection.
     *
     * @return The total modular credits of all modules in the collection.
     */
    public float totalModularCredits() {
        return modules.modularCredits();
    }

    /**
     * Calculates and returns the total grade points weighted by modular credits of all modules in the collection.
     *
     * @return The total grade points weighted by modular credits of all modules in the collection as a float.
     */
    public Float totalGradePointsWithUnits() {
        return modules.findGradePointsWithUnits();
    }

    /**
     * Calculates and returns the total modular credits of modules with valid grades in the collection.
     *
     * @return The total modular credits of modules with valid grades as a floating-point number.
     */
    public Float totalValidMcs() {
        return modules.findMcsForCap();
    }

    /**
     * Checks if {@code m} in the modules in this particular semester.
     *
     * @param m The module code to check for.
     * @return Whether the module is among the modules in this semester or not.
     */
    public boolean checkModuleBelongToSemester(Module m) {
        requireNonNull(m);

        boolean equalYear = this.year.equals(m.getYearTaken());
        boolean equalSemester = this.semester.equals(m.getSemesterTaken());

        //For advance placement
        if (equalYear && this.year.equals(Year.YEAR_0)) {
            return true;
        }

        return equalYear && equalSemester;
    }

    /**
     * Checks if the given semester is the same semesters.
     */
    public boolean checkIfSameSemester(ModulePlanSemester otherModulePlanSemester) {
        requireNonNull(otherModulePlanSemester);

        boolean yearEquals = this.year.equals(otherModulePlanSemester.year);
        boolean semesterEquals = this.semester.equals(otherModulePlanSemester.semester);

        //For advance placement
        if (yearEquals && this.year.equals(Year.YEAR_0)) {
            return true;
        }

        return yearEquals && semesterEquals;
    }

    /**
     * Whether the semester contains any module.
     *
     * @return True if it is empty and false otherwise
     */
    public boolean isEmpty() {
        return modules.isEmpty();
    }

    /**
     * Returns the unmodifiable module list.
     */
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    /**
     * Makes a new copy of ModulePlanSemester.
     */
    public ModulePlanSemester copy() {
        Year y = new Year(year.toString());
        Semester s = new Semester(semester.getSemesterString());

        return new ModulePlanSemester(y, s);
    }

    @Override
    public String toString() {
        if (year.equals(Year.YEAR_0)) {
            return "Adv Placement";
        }
        return "Year " + year + " " + semester.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModulePlanSemester)) {
            return false;
        }

        ModulePlanSemester otherModulePlanSemester = (ModulePlanSemester) other;
        boolean yearEquals = this.year.equals(otherModulePlanSemester.year);
        boolean semesterEquals = this.semester.equals(otherModulePlanSemester.semester);
        boolean modulesEquals = this.modules.equals(otherModulePlanSemester.modules);


        return yearEquals && semesterEquals && modulesEquals;
    }

    @Override
    public int compareTo(ModulePlanSemester o) {
        int comparedYear = this.year.compareTo(o.year);

        if (comparedYear == 0) {
            return this.semester.compareTo(o.semester);
        }
        return comparedYear;
    }
}
