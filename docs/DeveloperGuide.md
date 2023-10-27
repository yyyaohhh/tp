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

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.
* [**`Database`**](#database-component) : Parses data from within the App.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete CS2030S`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

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

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete CS2030S")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `ModulePlanParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `ModulePlanParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `ModulePlanParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

<puml src="diagrams/ModuleClassDiagram.puml" width="450" />

The `Model` component,

* stores the module plan of each semester i.e., all `ModulePlanSemester` in one `ModulePlanSemesterList`.
* stores the module plan data i.e., all `Module` objects (which are contained in a `UniqueModuleList` object).
* each module plan of a semester contains a `Semester` and a `Year` for identification and stores a `UniqueModuleList`.
* stores the currently 'selected' `Module` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Module>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` object.
* stores a `ModuleData` object that represents the information on all modules. This is exposed to the outside as a `ReadOnlyModuleData` object.
* does not depend on any of the other four components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `ModulePlan` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Database component

**API** : [`Database.java`](https://github.com/AY2324S1-CS2103T-T13-0/tp/blob/master/src/main/java/seedu/address/database/Database.java)

<puml src="diagrams/DatabaseClassDiagram.puml" width="550" />

The `Database` component,
* reads the module information from JSON format to the corresponding `ModuleData` object.
* depends on some classes in the `Model` component (because the `Database` component's job is to retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.


### Edit module feature

#### Implementation

The edit mechanism uses `EditModuleDescriptor` to abstract out the fields to edit. It can be found as a publicly accessible class within `EditCommand`. Currently, it only contains fields for `Year`, `Semester` and `Grade`, which are the only attributes of `Module` that can be edited for now. Besides this, it largely follows the parser and command structure as described in [Logic](#Logic component).

We shall now illustrate how `EditModuleDescriptor` is used.

Here is a *Sequence Diagram* showing the parser in action:

<puml src="diagrams/EditParseSequenceDiagram.puml" width="450" />

And here is a *Sequence Diagram* showing the command being executed:

<puml src="diagrams/EditExecuteSequenceDiagram.puml" width="450" />

As can be seen, this is a helpful class to store fields that need to be edited.


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

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


### Use cases

(For all use cases below, the **System** is `ModCraft` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Planning mods to take in the upcoming semester**

**MSS**

1. User searches a module that he wants to take next semester
2. Modcraft displays the information for the module.
3. User adds that module to his timetable
4. Modcraft shows the module in the User’s timetable
Steps 1-4 are repeated for each module the User is interested in

Use case ends.

**Extensions**

* 1a. Modcraft detects that the module does not exist.
    * 1a1. Modcraft informs the user it is unavailable
    * 1a2. User searches another module
Steps 1a1 to 1a2 are repeated until the module is available
Use case resumes from step 2.

* 3a. Modcraft detects that the module is unavailable for the semester.
    * 3a1. Modcraft informs the user it is unavailable
    * 3a2. User searches another module
Steps 3a1 to 3a2 are repeated until the module is available
Use case resumes from step 4.

* *a. At any time, user choose to delete a module
    * *a1. User deletes the module
    * *a2. Modcraft removes the module from the timetable
Use case resumes from step 1.

**Use case: UC02 - Updating end of semester grades**

**MSS**

1. User inputs grades for a module that they have taken.
2. System shows the updated grade in the timetable.
Steps 1-2 are repeated for each grade the user would like to update for.

Use case ends.

**Extensions**

* 1a. Grade is invalid
    * 1a1. System shows the user the grade is invalid
    * 1a2. User inputs correct grade
Steps 1a1 and 1a2 are repeated until the user inputs the correct grade
Use case resumes from step 2.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any mainstream OS as long as it has Java 11 or above installed.
2. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
3. Should be able to hold up to 10,000 courses without a noticeable sluggishness in performance for typical usage.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* *{More to be added}*

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

   1. Double-click the jar file Expected: Shows the GUI with a set of sample modules. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a module

1. Deleting a module from a semester while all modules are being shown

   1. Prerequisites: List all modules using the `list` command. Multiple modules in the list.

   1. Test case: `delete CS2030S`<br>
      Expected: The module CS2030S is deleted from the list. Details of the deleted module shown in the status message. The modules shown in the semester list is updated.

   1. Test case: `delete CS1231S` when it is not in the semester list<br>
      Expected: No module is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete 1234`, `...` (when the format of the module code to be deleted is incorrect)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Calculating total CAP

1. Calculating the current CAP from all modules

    1. Prerequisites: Multiple module in the list.

    1. Test case: `calculateCAP`<br>
       Expected: The CAP output is a `float` of `0.0` $\leq$ CAP $\leq$ `5.0` with a status message.

    1. Test case: `calculateCAP` when there are no modules in the semester list<br>
       Expected: The CAP output is `0.0`

1. _{ more test cases …​ }_

### Calculating total Modular Credits (MCs)

1. Calculating the current Modular Credits (MCs) from all modules

    1. Prerequisites: Multiple modules in the list.

    1. Test case: `calculateMC`<br>
       Expected: The MC output is a `integer` of MC $\geq$ `0` with a status message.

   1. Test case: `calculateMC` when there are no modules in the semester list<br>
      Expected: The MC output is `0`

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
