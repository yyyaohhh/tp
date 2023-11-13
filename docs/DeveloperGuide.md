---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# MODCRAFT Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).


Libraries used in this project:

- [Jackson](https://github.com/FasterXML/jackson)
- [JavaFX](https://openjfx.io/)
- [JUnit5](https://github.com/junit-team/junit5)
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

<br>

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="400" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

<br>

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Database`**](#database-component) parses data from within the App on startup. This data is used to support user input validation according to the business logic.<br>
[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<br>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete CS2030S`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above), as well as the [**`Database`**](#database-component) component,

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<br>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ModulePlanPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `ModulePlanSemester` object residing in the `Model`.

<br>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

<br>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete CS2030S")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to a `ModulePlanParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

<br>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

<br>

How the parsing works:
* When called upon to parse a user command, the `ModulePlanParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `ModulePlanParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<br>

### Model component
**API** : [`Model.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="600" />

<br>

The `Model` component,

* stores the module plan data i.e., all `Module` objects (which are contained in `UniqueModuleList` objects).
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` object.
* stores a `ModuleData` object that represents the information on all modules. This is exposed to the outside as a `ReadOnlyModuleData` object.
* does not depend on any of the other four components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** The module plan data is split into different semesters (e.g. Year 1 S1, Year 1 S2, Year 2 S1, etc). Instead of one `UniqueModuleList` storing all of the user's modules across multiple semesters, each semester's modules are stored in their own `UniqueModuleList` object. Nevertheless, modules are required to be unique across semesters, meaning that the same module will be prevented from being added to multiple semesters. The implementation of this check can be found in `ModulePlanSemesterList`.

</box>

<br>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

<br>

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve modifiable objects that belong to the `Model`)

<br>

### Database component

**API** : [`Database.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/database/Database.java)

<puml src="diagrams/DatabaseClassDiagram.puml" width="250" />

<br>

The `Database` component,
* reads the module information from JSON format to the corresponding `ModuleData` object.
* depends on some classes in the `Model` component (because the `Database` component's job is to retrieve read-only objects that belong to the `Model`)

<box type="info" seamless>

**Note:** The module data is stored within the resource folder. In the case where the data cannot be read successfully, a `RuntimeException` is deliberately triggered to forcefully halt the application's execution. This is necessary because all features are reliant on the module data.

</box>

<br>

### Common classes

Classes used by multiple components are in the [`seedu.address.commons`](https://github.com/AY2324S1-CS2103T-T13-0/tp/tree/master/src/main/java/seedu/address/commons) package.

<br>

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features and commands are implemented.

* [Module Database Feature](#module-database-feature)
* [Module Plan Feature](#module-plan-feature)
* [UI Feature](#ui-feature)
* [Module Storage Feature](#module-storage-feature)
* [Info Module Command](#info-module-command)
* [Add Module Command](#add-module-command)
* [Edit Module Command](#edit-module-command)
* [Delete Module Command](#delete-module-command)
* [Calculate CAP Command](#calculate-cap-command)
* [Calculate Modular Credits (MCs) Command](#calculate-modular-credits-mcs-command)
* [Undo/redo feature](#proposed-undoredo-feature)

<br>

### Module Database Feature

**Overview:**

ModCraft contains an internal list of all possible modules, which forms the backbone of the application. This `ModuleData` allows for validation of user input and provides the required module information to be displayed.

The module information is stored as `moduleinfo.json` in the `src/main/resources/database` folder. Users are not permitted to access the file as uninformed modifications could cause the application to invariably fail at startup.

**Feature details:**

1.   When the user launches the application, the data is converted from JSON format to the `ModuleData` class, which supports verification and retrieval of `Module` information.
2.   During command execution, the `ModuleCode` input by the user is validated to exist within `ModuleData`.
3.   If the `ModuleCode` is found to be invalid, an error message is displayed to the user.
4.   Otherwise, the command execution continues to retrieve information about the `Module` if needed. The content of this step differs between the different commands, more details are provided for each individual command below.

**Initialization sequence:**
1. At startup, `MainApp` calls `DatabaseManager#readDatabase` to attempt to parse the `moduleinfo.json` file.
2. The `DatabaseManager` deserializes the JSON file into a `JsonSerializableModuleData` object by calling `JsonUtil#readJsonResource`. <br>
   2a. The `JsonSerializableModuleData` object represents a list of `JsonAdaptedDbModule` objects, which are created during deserialization.
3. The `DatabaseManager` then calls `JsonSerailizableModuleData#toModelType` to create the `ModuleData` object. <br>
   3a. `JsonSerailizableModuleData` calls `JsonAdaptedDbModule#toModelType` for the creation of each module.
4. The `ModuleData` is returned to `MainApp` where it is used to initialize `ModelManager`, which is used during command execution.
5. A `DataLoadingException` is thrown if any of the above steps fail, which could happen if
   * the file cannot be found,
   * an error occurs during deserialization, or
   * the data contains invalid values.


This can be shown through following sequence diagram:

<puml src="diagrams/ModuleDataInitSequenceDiagram.puml" />

### Module Plan Feature
- how semesters are setup
- what happens a user attempts to add a duplicate module

### UI Feature?
- how the ui is setup
- what happens when the user inputs a command

### Module Storage Feature
- how the storage works
- what happens when it fails to load
- what happens when the user modifies the moduleplan
- userprefs considered the same feature? if too long can split into another one

### Info Module Command

**Overview:**

**Feature details:**


### Add Module Command
**Overview:**
The `add` command is used to add a module to the module plan with the information fields `Module Code`, `Year Taken`,
    `Semester Taken`, and `Grade`.

The format for the `add` command can be found [here](#adding-a-module)

**Feature details:**
1. The user executes the `add` command.
2. If any of the fields are not provided, an error message with the appropriate command usage will be displayed.
3. If any of the command parameters are invalid, an error message with the appropriate parameter format will be displayed.
4. The `Module` is then searched in the `model` to see if it is an existing module that NUS offers. If the module is not
offered, an error message will be displayed.
5. The `Module` is then checked with `ModulePlan` to see if it has already been added to the module plan previously.
If the module has already been added the User's module plan, an error message will be displayed.
6. If all the previous stages complete without exceptions or errors, the `Module` will be added to the `ModulePlan`

The activity diagram for adding a `Module` into the module plan

<puml src="diagrams/AddModuleActivityDiagram.puml" width="450" />


### Edit Module Command

<br>

#### Implementation

The edit mechanism uses `EditModuleDescriptor` to abstract out the fields to edit. It can be found as a publicly accessible class within `EditCommand`. Currently, it only contains fields for `Year`, `Semester` and `Grade`, which are the only attributes of `Module` that can be edited for now. Besides this, it largely follows the parser and command structure as described in [Logic](#logic-component).

We shall now illustrate how `EditModuleDescriptor` is used.

Here is a *Sequence Diagram* showing the parser in action:

<puml src="diagrams/EditParseSequenceDiagram.puml" width="450" />

And here is a *Sequence Diagram* showing the command being executed:

<puml src="diagrams/EditExecuteSequenceDiagram.puml" width="450" />

As can be seen, this is a helpful class to store fields that need to be edited.

### Delete Module Command

**Overview:**<br>

The `delete` command is used to delete a module from the module plan. The module can only be deleted if it is already present in one of the semesters in the module plan.<br>

The format of the `delete` command can be found [here](https://ay2324s1-cs2103t-t13-0.github.io/tp/UserGuide.html#deleting-a-module-delete).<br>

**Feature details:**<br>

1. The user executes the `delete` command.
2. If the module code field is not provided, an error message with the correct command usage will be shown.
3. If invalid module code format is provided, an error message with the correct module code format will be shown.
4. If valid module code format is provided but `Module` does not exist in database, an error message informing user that the `Module` does not exist will be shown.
5. The `Module` is then cross-referenced in the `Model` to check if a module with the same `ModuleCode` exists in the module plan.
6. If the module does not exist in the module plan, an error message informing the user that the `Module` has not added to the module plan will be shown.
7. If all previous steps are completed without exceptions, the new `Module` will be successfully deleted from the module plan.

<br>

The following activity diagram shows the logic of deleting a `Module` from the module plan:

<puml src="diagrams/DeleteCommandActivityDiagram.puml" width="450" />

<br>

The sequence of the `delete` command is as follows:<br>

1. The user inputs the `delete` command.<br>
e.g. `delete CS3230`
2. The `LogicManager` calls the `ModulePlanParser#parseCommand` to parse the command.
3. The `ModulePlanParser` then creates a new `DeleteCommandParser` to parse the fields provided by the user and a new `DeleteCommand` is created.
4. The `DeleteCommand` checks if the `ModuleCode` is valid by calling `Model#checkDbValidModuleCode`.
4. The `DeleteCommand` then checks if the `Model` contains a module with the same `ModuleCode` by calling `Model#getModule`.
5. If the `ModuleCode` is valid and `Model` contains the module, the `DeleteCommand` calls `Model#deleteModule` to delete the module from to the module plan.

The following sequence diagram shows how the `delete` command works:

<puml src="diagrams/DeleteCommandSequenceDiagram.puml" width="450" />

<br>

### Calculate CAP Command

**Overview:**<br>

The `calculateCAP` command is used to calculate the Cumulative Average Point (CAP) of all valid modules in the module plan, using their grade points and modular credits. <br>

The format of the `calculateCAP` command can be found [here](https://ay2324s1-cs2103t-t13-0.github.io/tp/UserGuide.html#calculating-the-total-current-cap-calculatecap).<br>

**Feature details:**<br>

1. The user executes the `calculateCAP` command.
2. If the previous step is completed without exceptions, the CAP will be calculated and displayed.

<br>

The sequence of the `calculateCAP` command is as follows:<br>

1. The user inputs the `calculateCAP` command.<br>
2. The `LogicManager` calls the `ModulePlanParser#parseCommand` to parse the command.
3. The `ModulePlanParser` then creates a new `CalculateCapCommand`.
4. The `CalculateCapCommand` calculates the CAP by calling `Model#getCap`.

The following sequence diagram shows how the `calculateCAP` command works:

<puml src="diagrams/CalculateCapSequenceDiagram.puml" width="450" />

<br>

### Calculate Modular Credits (MCs) Command

**Overview:**<br>

The `calculateMC` command is used to calculate the total sum of Modular Credits (MCs) of all modules in the module plan, regardless of their grades. <br>

The format of the `calculateMC` command can be found [here](https://ay2324s1-cs2103t-t13-0.github.io/tp/UserGuide.html#calculating-the-total-current-modular-credits-mcs-calculatemc).<br>

**Feature details:**<br>

1. The user executes the `calculateMC` command.
2. If the previous step is completed without exceptions, the number of MCs will be calculated and displayed.

<br>

The sequence of the `calculateMC` command is as follows:<br>

1. The user inputs the `calculateMC` command.<br>
2. The `LogicManager` calls the `ModulePlanParser#parseCommand` to parse the command.
3. The `ModulePlanParser` then creates a new `CalculateMcCommand`.
4. The `CalculateMcCommand` calculates the CAP by calling `Model#totalModularCredits`.

The following sequence diagram shows how the `calculateMC` command works:

<puml src="diagrams/CalculateMcSequenceDiagram.puml" width="450" />

<br>

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedModulePlan`. It extends `ModulePlan` with an undo/redo history, stored internally as an `ModulePlanStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedModulePlan#commit()` — Saves the current module plan state in its history.
* `VersionedModulePlan#undo()` — Restores the previous module plan state from its history.
* `VersionedModulePlan#redo()` — Restores a previously undone module plan state from its history.

These operations are exposed in the `Model` interface as `Model#commitModulePlan()`, `Model#undoModulePlan()` and `Model#redoModulePlan()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedModulePlan` will be initialized with the initial module plan state, and the `currentStatePointer` pointing to that single module plan state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete CS1010` command to delete the module `CS1010` in the module plan. The `delete` command calls `Model#commitModulePlan()`, causing the modified state of the module plan after the `delete CS1010` command executes to be saved in the `modulePlanStateList`, and the `currentStatePointer` is shifted to the newly inserted module plan state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add CS2030 …​` to add a new module. The `add` command also calls `Model#commitModulePlan()`, causing another modified module plan state to be saved into the `modulePlanStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitModulePlan()`, so the module plan state will not be saved into the `modulePlanStateList`.

</box>

Step 4. The user now decides that adding the module was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoModulePlan()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous module plan state, and restores the module plan to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial ModulePlan state, then there are no previous ModulePlan states to restore. The `undo` command uses `Model#canUndoModulePlan()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoModulePlan()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the module plan to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `modulePlanStateList.size() - 1`, pointing to the latest module plan state, then there are no undone ModulePlan states to restore. The `redo` command uses `Model#canRedoModulePlan()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

<br>

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire module plan.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the module being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

<br>

--------------------------------------------------------------------------------------------------------------------

## **Future enhancements**

For our next steps, we plan to add the following features:
* Check if a module is CS/CU or not, and check if the grade given from user input is valid for that module.
* Check which semesters a module is offered, and check if the semester given from user input is valid for that module.
* Check for a module's pre-requisites, and check if they are fulfilled before allowing the user to add the module.
* Check for a module's pre-clusions, and prevent users from adding modules that are pre-clusions of each other.
* Check for a module's co-requisites, and remind users to add modules that are co-requisites of each other and to take them concurrently.
* Check for a module's availability of S/U options, and prevent users from inputting S/U grades to non-S/U-able modules.
* Add support for allowing users to add a module that was failed in a previous semester to another new semester.

* <br>

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

<br>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* is planning to take NUS modules
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Provide a fast and easy way for students to track courses to take to meet graduation requirements and plan courses to take, optimized for users who prefer typing in CLI.

<br>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …                                                                 | I want to …                                                                                 | So that I can…                                               |
|----------|------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|--------------------------------------------------------------|
| `* * *`  | fast typer                                                             | use the typing interface                                                                    | I save time from interacting with the GUI                    |
| `* * *`  | current student                                                        | set my major                                                                                |                                                              |
| `* * *`  | current student                                                        | add modules to "taken" / "taking" / "plan to take" lists                                    |                                                              |
| `* * *`  | current student                                                        | search for modules by module code or module name                                            |                                                              |
| `* * *`  | current student                                                        | remove modules from the lists                                                               |                                                              |
| `* * *`  | current student                                                        | access data entered in a previous session                                                   |                                                              |
| `* * *`  | forgetful student                                                      | view which year and semester I have taken certain modules                                   |                                                              |
| `* * *`  | current student                                                        | see the modules I need to take to graduate                                                  | I can apply for and take those modules to graduate           |
| `* * *`  | incoming freshman                                                      | see the modules I might need to take for my course                                          | I can make an informed decision about what I am applying for |
| `* * *`  | current student                                                        | check how many MCs I haven taken                                                            | I know how many more I need to take to graduate              |
| `* * *`  | overworked student                                                     | just check what modules I have taken                                                        | I don't have to remember them myself                         |
| `* *`    | student going on exchange                                              | see which modules can be mapped over                                                        |                                                              |
| `* *`    | current student                                                        | see how the modules I plan to take affect what modules I will be able to take in the future | I can plan my future semesters                               |
| `* *`    | current student                                                        | check what prerequisite modules I need to take                                              | I can take this specific module                              |
| `* *`    | struggling student                                                     | see what modules I can use my remaining S/Us on                                             | I can plan my modules accordingly                            |
| `* *`    | current student                                                        | set the grade for taken mods and expected grade for future mods                             | the app can calculate my CAP for me                          |
| `* *`    | current student                                                        | see my current calculated CAP / predicted graduation CAP                                    | I do not have to calculate it myself                         |
| `* *`    | graduated student                                                      | check what modules I have taken to revise the skills I have learnt in university            | I can use my skills at my job                                |
| `* *`    | graduated student                                                      | check the modules I have taken for completing my resume and answering interview questions   | I can get the job                                            |
| `* *`    | student with many friends in different majors                          | check whether I can also take the modules they are taking                                   | we can help each other with the module                       |
| `* *`    | double major/degree student                                            | check what modules I can double count                                                       | I can lower my workload                                      |
| `* *`    | student looking to take a second major or minor                        | see if I can take the modules required                                                      | I can obtain the second major or minor                       |
| `* *`    | student staying in RC/NUSC or taking other special programs (like SPM) | check for modules that fulfill requirements                                                 | I can replace the general education modules                  |
| `* *`    | student that wants to take niche modules                               | search for it                                                                               | I know what are the options                                  |
| `* *`    | current student                                                        | see if certain modules are available in the current semester                                | I can plan my study plan or take it this semester            |
| `* *`    | student who likes a certain professor                                  | see which semester that professor will be teaching the module                               | I can take their module                                      |
| `* *`    | student who dislikes a certain professor                               | see which modules they will be teaching                                                     | I can avoid them                                             |
| `* *`    | student taking credit-bearing internships                              | see how my internships fulfill my graduation requirement and MCs                            |                                                              |
| `* *`    | student taking a focus area                                            | see if the module I have taken satisfy my focus area                                        |                                                              |
| `* *`    | failing student                                                        | see if I can drop certain modules                                                           | I can graduate through an alternate path                     |
| `* *`    | student looking for work                                               | see which modules give me skills I need                                                     | I can apply for certain jobs/internships                     |
| `* *`    | student who wants to switch courses                                    | check which modules I can carry over and still take                                         | I can graduate                                               |
| `*`      | advanced user                                                          | manually edit the data file                                                                 | I can manipulate the data as I wish                          |
| `*`      | student that hates a specific module                                   | check for other alternative modules so that I can avoid that module                         | I can prevent excessive stress                               |
| `*`      | ambitious student                                                      | find the fastest way to graduate                                                            |                                                              |
| `*`      | kiasu student                                                          | see which modules are popular/oversubscribed                                                | I can adjust my academic plan                                |
| `*`      | JC/Poly student                                                        | see what modules I am exempted from                                                         |                                                              |
| `*`      | foreign student                                                        | see the bridging modules I am required to take                                              |                                                              |
| `*`      | current student                                                        | check if I am exempted from the bridging modules                                            | I can take them to skip the mod                              |
| `*`      | Advanced Placement student                                             | add what modules I have taken before                                                        |                                                              |
| `*`      | current student                                                        | see which modules have a diagnostic test                                                    | I can skip the mod                                           |
| `*`      | tryhard student                                                        | see what modules are available in the special term                                          |                                                              |

<br>

### Use cases

(For all use cases below, the **System** is `ModCraft` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Planning mods to take in the upcoming semester**

**MSS**

1. User searches a module that he wants to take next semester
2. Modcraft displays the information for the module.
3. User adds that module to their module plan.
4. Modcraft shows the module in the User’s module plan.
Steps 1-4 are repeated for each module the User is interested in

Use case ends.

**Extensions**

* 1a. Modcraft detects that the module does not exist.
    * 1a1. Modcraft informs the user it is unavailable
    * 1a2. User searches another module
Steps 1a1 to 1a2 are repeated until the module is available
Use case resumes from step 2.

* *a. At any time, user choose to delete a module
    * *a1. User deletes the module
    * *a2. Modcraft removes the module from the timetable
Use case resumes from step 1.


**Use case: UC02 - Updating end of semester grades**

**MSS**

1. User inputs grades for a module that they have taken.
2. Modcraft shows the updated grade in the timetable.
Steps 1-2 are repeated for each grade the user would like to update for.

Use case ends.

**Extensions**

* 1a. Grade is invalid
    * 1a1. Modcraft shows the user the grade is invalid
    * 1a2. User inputs correct grade
Steps 1a1 and 1a2 are repeated until the user inputs the correct grade
Use case resumes from step 2.


**Use case: UC08 - Indicating exempted modules**

**MSS**

1. User inputs `EXE` as the grade for a module that they have taken.
2. System shows the updated grade for the module, which is `EXE`, in the timetable.
   Steps 1-2 are repeated for each module that the user would like to indicate as exempted.

Use case ends.

**Extensions**

* 1a. Module code is invalid
    * 1a1. System shows the user that the module code inputted is invalid
    * 1a2. User inputs correct module code
      Steps 1a1 and 1a2 are repeated until the user inputs the correct module code
      Use case resumes from step 2.


**Use case: UC09 - S/Uing modules**

**MSS**

1. User inputs `S` or `U` as the grade for a module that they have taken.
2. System shows the updated grade for the module, which is `S` or `U`, in the timetable.
   Steps 1-2 are repeated for each module that the user would like to indicate as Satisfactory (S) or Unsatisfactory (U).

Use case ends.

**Extensions**

* 1a. Module code is invalid
    * 1a1. System shows the user that the module code inputted is invalid
    * 1a2. User inputs correct module code
      Steps 1a1 and 1a2 are repeated until the user inputs the correct module code
      Use case resumes from step 2.

<br>

___
#### **Use Case: UC06 - Indicating Advanced Placement Modules**

**MSS**

1. User searches for the advanced placement module that they have taken or are planning to take using the info command.
2. User adds the module to the module plan.
3. Modcraft shows the module in the module plan.

Use case ends.

**Extensions**

* 1a. Modcraft detects that the module does not exist or not available for advanced placement.
    * 1a1. Modcraft informs the user it is unavailable.
    * 1a2. User searches another module.
Steps 1a1 to 1a2 are repeated until the module is available.
Use case resumes from step 2.

___

#### **Use Case: UC07 - Indicating Special Term Modules**

**MSS**

1. User searches for a special term module using the info command.
2. Modcraft shows that the module is available to be taken in the special term.
3. User adds the module to the module plan.
4. Modcraft shows the module in the User's module plan.

Use case ends.

**Extensions**
* 1a. Modcraft detects that the module does not exist or not available for advanced placement.
    * 1a1. Modcraft informs the user it is unavailable.
    * 1a2. User searches another module.
      Steps 1a1 to 1a2 are repeated until the module is available.
      Use case resumes from step 2.
* 3a. User wants to indicate that the module is taken or to be taken in Special Term 1 or Special Term 2
  * 3a1. User uses the add command and specifies the semester to be `s/ST1` for Special Term 1 or `s/ST2` for Special Term 2
  * Use case resumes from step 4.

___
### Non-Functional Requirements

1. Should work on any mainstream OS as long as it has Java 11 or above installed.
2. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
3. Should be able to hold up to 1,000,000 courses without a noticeable sluggishness in performance for typical usage.

<br>

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X.
* **Command Line Interface (CLI)**: A display that allows you to type commands to interact with the application.
* **Graphical User Interface (GUI)**: A user-friendly display that allows you to see the effects of your actions in the application.
* **JSON**: stands for JavaScript Object Notation, a lightweight data-interchange format and a plain text written in JavaScript object notation, used to send data between computers.
* **Pre-requisite**: Pre-requisites indicate the base of knowledge on which the subject matter of a particular course will be built. Before taking a course, a student should complete any pre-requisite course(s) listed for that particular course.<br>
Where pre-requisites are specified, equivalent courses will also be accepted. For more information on pre-requisites, please refer to [NUS's Modular System](https://www.nus.edu.sg/registrar/academic-information-policies/graduate/modular-system).
* **Pre-clusions**: Courses that have similar emphases and may not be taken together with that particular course.
* **Co-requisites**: Courses that are to be taken concurrently.

<br>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Open command terminal and `cd` into the folder where the jar file is in. Use the `java -jar ModCraft.jar` command to run the application.<br>
   Expected: Shows the GUI with a set of sample modules. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

<br>

### Adding a module

1. Adding a module into a semester

    1. Prerequisites: No new modules are being added except for those default modules present in ModCraft.

    1. Test case: `add CS2030S y/1 s/1 g/IP`.<br>
       Expected: The module `CS2030S` is added to the list `Year 1 S1`, with its grade as `IP` in a grey box. Details of the added module shown in the status message. The modules shown in the semester list is updated.

    1. Test case: `add CS3230 y/0 s/1 g/A`.<br>
       Expected: A new column of semester named `Adv Placement` appears. The module `CS3230` is added to the list `Adv Placement`, with its grade as `A` in a green box. Details of the added module shown in the status message. The modules shown in the semester list is updated.

    2. Test case: `add CS1010 y/1 s/ST1 g/F`.<br>
       Expected: A new column of semester named `Year 1 ST1` appears. The module `CS1010` is added to the list `Year 1 ST1`, with its grade as `F` in a red box. Details of the added module shown in the status message. The modules shown in the semester list is updated.

    3. Test case: `add CS1231S ...` when it is already in the semester list.<br>
       Expected: No module is added. Error details shown in the status message. Status bar remains the same.

    4. Test case: `add CS1010 y/1 s/ST1 g/a`.<br>
       Expected: No module is added. Error details of wrong format of grade shown in the status message. Status bar remains the same.

    3. Other incorrect add commands to try: `add`, `add 1234`, `add CS1010 y/1`, `...` (when the format of the module code to be added is incorrect)<br>
       Expected: Similar to previous.

<br>

### Deleting a module

1. Deleting a module from a semester

   1. Prerequisites: Multiple modules in the list.

   1. Test case: `delete CS2030S`.<br>
      Expected: The module `CS2030S` is deleted from the list. Details of the deleted module shown in the status message. The modules shown in the semester list is updated.

   1. Test case: `delete CS1231S` when it is not in the semester list.<br>
      Expected: No module is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete 1234`, `...` (when the format of the module code to be deleted is incorrect).<br>
      Expected: Similar to previous.

<br>

### Editing a module

1. Editing a module already present in a semester

    1. Prerequisites: The module to be edited is already present in ModCraft.

    1. Test case: `edit CS2030S g/IP`.<br>
       Expected: The grade of `CS2030S` is updated to `IP` in a grey box. Details of the edited module shown in the status message. The module shown in the semester list is updated.

    1. Test case: `edit CS3230 y/0`.<br>
       Expected: A new column of semester named `Adv Placement` appears. The module `CS3230` is moved to the list `Adv Placement`, while its grade remain unchanged. Details of the edited module shown in the status message. The modules shown in the semester list is updated.

    2. Test case: `edit CS1010 s/ST1`.<br>
       Expected: A new column of semester named `ST1` appears. The module `CS1010` is moved to the list `ST1`, while its grade and semester remain unchanged. Details of the added module shown in the status message. The modules shown in the semester list is updated.

    3. Test case: `edit CS1010 g/a`.<br>
       Expected: No module is edited. Error details of wrong format of grade shown in the status message. Status bar remains the same.

    3. Test case: `edit CS1101S ...` when it is not already present in the semester list.<br>
       Expected: No module is edited. Error details shown in the status message. Status bar remains the same.

    3. Other incorrect edit commands to try: `edit`, `edit 1234`, `...` (when the format of the module code to be edited is incorrect)<br>
       Expected: Similar to previous.

<br>

### Finding info about a module

1. Finding information about a module from the database

   1. Test case: `info CS2030S`.<br>
   Expected: The module code, module title, modular credit and module description of `CS2030S` is displayed.

   2. Other incorrect info commands to try: `info`, `info 1234`, `...` (when the format of the module code to be searched is incorrect).<br>
     No information about module is displayed. Error details shown in the status message. Status bar remains the same.

<br>

### Calculating total CAP

1. Calculating the current CAP from all modules

   1. Test case: `calculateCAP` when there are no modules in the semester list.<br>
         Expected: The CAP output is `0.0`.

   2. Prerequisites: Multiple module in the list.

   3. Test case: `calculateCAP`.<br>
     Expected: The CAP output is a `float` of `0.0` $\leq$ CAP $\leq$ `5.0` with a status message.

   4. Test case: `calculateCAP` when grades of all modules are marked as `IP`, `EXE`, `W`, `IC`, `S`, `U`, `CS` or `CU`.<br>
    Expected: The CAP output is `0.0`.

<br>

### Calculating total Modular Credits (MCs)

1. Calculating the current Modular Credits (MCs) from all modules

   1. Test case: `calculateMC` when there are no modules in all the semester lists.<br>
      Expected: The Modular Credits output is `0.0`.

   2. Prerequisites: Multiple modules in the list.

   3. Test case: `calculateMC`.<br>
      Expected: The Modular Credits output is a `float` of Modular Credits $\geq$ `0.0` with a status message.

<br>

### Saving data

1. Dealing with missing/corrupted data files

   1. Prerequisites: There are existing module and module plan files with existing stored modules.

   2. Test Case: Close the application and delete `moduleplan.json`.
      Expected: Upon the next application start, a new `moduleplan.json` is created.

   3. Test Case: Close the application and edit `moduleplan.json` by changing the name of the first Module to `CS3230`.
      Expected: Upon the next application start, the name of the first Module in chronological order in the list of years and semesters will appear as `CS3230`.

   4. Test Case: Close the application and edit `moduleplan.json` by changing the year of the first Module to `2`.
      Expected: Upon the next application start, the name of the first Module will appear in `Year 2`, with its semester and grade unchanged.

   5. Test Case: Close the application and edit `moduleplan.json` by changing the semester of the first Module to `ST2`.
      Expected: Upon the next application start, the name of the first Module will appear in `ST2`, with its year and grade unchanged.

   6. Test Case: Close the application and edit `moduleplan.json` by changing the grade of the first Module to `IP`.
      Expected: Upon the next application start, the grade of the first Module in chronological order in the list of years and semesters will appear as `IP`, which should be grey in colour. Its name should remain unchanged.




