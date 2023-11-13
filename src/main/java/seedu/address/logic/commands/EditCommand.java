package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Edits the details of an existing module in the module plan.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the module code. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "CODE "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "[" + PREFIX_SEMESTER + "SEMESTER] "
            + "[" + PREFIX_GRADE + "GRADE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2103T "
            + PREFIX_YEAR + "2 "
            + PREFIX_SEMESTER + "1 "
            + PREFIX_GRADE + "IP";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_MODULE_CODE_CHANGE = "Changing module code is not allowed.";

    private final ModuleCode moduleCode;
    private final EditModuleDescriptor editModuleDescriptor;

    /**
     * @param moduleCode           of the module to edit
     * @param editModuleDescriptor details to edit the module with
     */
    public EditCommand(ModuleCode moduleCode, EditModuleDescriptor editModuleDescriptor) {
        requireNonNull(moduleCode);
        requireNonNull(editModuleDescriptor);

        this.moduleCode = moduleCode;
        this.editModuleDescriptor = new EditModuleDescriptor(editModuleDescriptor);
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit, EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        Year updatedYear = editModuleDescriptor.getYear().orElse(moduleToEdit.getYearTaken());
        Semester updatedSemester = editModuleDescriptor.getSemester().orElse(moduleToEdit.getSemesterTaken());
        Grade updatedGrade = editModuleDescriptor.getGrade().orElse(moduleToEdit.getGrade());

        return moduleToEdit.fillUserInputs(updatedYear, updatedSemester, updatedGrade);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Database check
        if (!model.checkDbValidModuleCode(moduleCode)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_MODULE_CODE, moduleCode));
        }

        // Retrieve module from module plan
        Module moduleToEdit;
        try {
            moduleToEdit = model.getModule(moduleCode);
        } catch (ModuleNotFoundException mnfe) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_MODULE_NOT_FOUND, moduleCode, COMMAND_WORD));
        }

        // Edit module
        Module editedModule = createEditedModule(moduleToEdit, editModuleDescriptor);
        assert editedModule.isSameModule(moduleToEdit) : MESSAGE_MODULE_CODE_CHANGE;

        // Update module plan and return success message
        model.setModule(moduleToEdit, editedModule);
        return new CommandResult(
                String.format(MESSAGE_EDIT_MODULE_SUCCESS, Messages.format(editedModule)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return moduleCode.equals(otherEditCommand.moduleCode)
                && editModuleDescriptor.equals(otherEditCommand.editModuleDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("moduleCode", moduleCode)
                .add("editModuleDescriptor", editModuleDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleDescriptor {
        private Year year;
        private Semester semester;
        private Grade grade;

        public EditModuleDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setYear(toCopy.year);
            setSemester(toCopy.semester);
            setGrade(toCopy.grade);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(year, semester, grade);
        }

        public Optional<Year> getYear() {
            return Optional.ofNullable(year);
        }

        public void setYear(Year year) {
            this.year = year;
        }

        public Optional<Semester> getSemester() {
            return Optional.ofNullable(semester);
        }

        public void setSemester(Semester semester) {
            this.semester = semester;
        }

        public Optional<Grade> getGrade() {
            return Optional.ofNullable(grade);
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleDescriptor)) {
                return false;
            }

            EditModuleDescriptor otherEditModuleDescriptor = (EditModuleDescriptor) other;
            return Objects.equals(year, otherEditModuleDescriptor.year)
                    && Objects.equals(semester, otherEditModuleDescriptor.semester)
                    && Objects.equals(grade, otherEditModuleDescriptor.grade);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("year", year)
                    .add("semester", semester)
                    .add("grade", grade)
                    .toString();
        }
    }
}
