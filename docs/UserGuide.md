---
layout: default.md
title: "User Guide"
pageNav: 3
---

# ModCraft User Guide

<!-- * Table of Contents -->
<page-nav-print />

ModCraft is an app that provides a fast and easy way for university students to track courses to take to meet graduation requirements and plan courses to take. The user interacts
with it using a Command Line Interface (CLI), and it has a Graphical User Interface (GUI) created with JavaFX.

This User Guide provides a guide of how to set up ModCraft and a description of useful commands to use. If you are a beginner, we recommend that you start with the [Quick start](#quick-start) guide. Otherwise, feel free to explore the various features from the [Table of Contents](#table-of-contents) below.

This User Guide consists of various visuals to aid your reading. 

<box type="tip" seamless>

**Tip:** Tips in boxes like this contain information that may be helpful.
</box>

<box type="warning" seamless>

**Caution:** Warnings in boxes like this contain information that are vital to the running of the application. Ensure that you take special note of these warnings to prevent unexpected behaviour.
</box>

--------------------------------------------------------------------------------------------------------------------
## Table of Contents
* [Quick start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Known issues](#known-issues)
* [Command summary](#command-summary)
--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java 11 or above installed in your Computer.
    1. To do this, open a command terminal and type `java --version`.
    2. If you do not have Java 11 installed, get it [here](https://www.oracle.com/sg/java/technologies/javase/jdk11-archive-downloads.html).

2. Download the latest `ModCraft.jar` from [here](https://github.com/AY2324S1-CS2103T-T13-0/tp/releases/tag/v1.2b).

3. Copy the file to the folder you want to use as the _home folder_ for your ModCraft.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ModCraft.jar` command to run the application.<br>
   A GUI similar to below should appear in a few seconds. Note how the app contains some sample data.<br> <br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `info CS1101S` : Shows Information about the module CS1101S.

    * `add CS2030S y/1 s/2 g/IP` : Adds the module CS2030S to semester 2 in year 1, and marks it as In Progress.

    * `delete CS2040S` : Deletes the module CS2040S if present from the list of modules taken.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) section below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

**Notes about the command format:**<br>

* All commands are **case-sensitive**.

* Words in `UPPER_CASE` are **compulsory parameters** to be supplied by the user.<br>
  e.g. in `delete MODULE`, `MODULE` is a parameter which can be used as `delete CFG1002`.

* Parameters in square brackets denote **optional parameters**.<br>
  e.g. `edit [y/YEAR]` means that specifying `y/YEAR` is optional. However, if `y/` is keyed in by the user, `YEAR` has to be specified.

* When passing in grades as parameters, the grades follow the [NUS Modular System](https://www.nus.edu.sg/registrar/academic-information-policies/undergraduate-students/modular-system).

* Extraneous parameters for commands that do not take in parameters (such as `help` and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Parameters can come with a **prefix** (like `y/` in `y/YEAR`) or without a prefix (like `MODULE`).

* All commands will have at most one parameter without a prefix. Parameters without a prefix should come before any parameters that come with a prefix. ModCraft will parse all input between the command and the first prefix as the parameter without the prefix.<br>
  e.g. For `add MODULE y/YEAR s/SEM g/GRADE`, when you key in `add CS2101 CS2103T y/1 s/1 g/A`, ModCraft will read it as you trying to specify `CS2101 CS2103T` as `MODULE`.

* Parameters with a prefix can be specified in any order among themselves.<br>
  e.g. `add CS2030S y/1 s/2 g/IP` is the same as `add CS2030S s/2 g/IP y/1`.

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a module: `add`

Adds a module to the list of modules taken in the specified year and semester.

Format: `add MODULE y/YEAR s/SEM g/GRADE`

<box type="tip" seamless>

**Tip:** The module will be added to the default sem set.
</box>

Examples:
* `add MA1521 y/1 s/1 g/A`
* `add IS1108 y/1 s/2 g/CS`
* `add ST2334 y/2 s/1 g/IP`


### Editing a module: `edit`

Changes an attribute of a module, if it exists. Useful if you want to update information about a module or have made a mistake in adding.

Format: `edit MODULE [y/YEAR] [s/SEM] [g/GRADE]`

<box type="tip" seamless>

**Tip:** At least one of the optional fields must be provided.
</box>

Examples:
* `edit CS2030S g/A+`: Updates the grade of CS2030S to A+.
* `edit CS3230 y/4 s/2`: Moves CS3230 to Year 4 Semester 2.


### Deleting a module : `delete`

Removes the module from whichever semester the module is taken, if it exists.

Format: `delete MODULE`

Examples:
* `delete GEA1000`
* `delete CS2030S`

### Finding Information about a module: `info`

Retrieves information about a certain module by module code.

Format: `info MODULE`

Examples:
* `info CS2019`
* `info CS1010`

### Calculating the total current CAP:
Calculates the total current CAP of all modules stored in all years and semesters
using the formula:  
$\frac{\text{sum of all modules: (grade point of that module * Modular Credits of that module)}}{\text{total Modular Credits}}$.

Returns a `float` of `0.0` $\leq$ value $\leq$ `5.0`.

Format: `calculateCAP`

### Calculating the total current Modular Credits (MCs)
Calculates the total current Modular Credits (MCs) stored in all years and semesters.

Format: `calculateMC`

### Exiting the program : `exit`

Exits the program.

Format: `exit`


### Saving the data

ModuleList data is saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

### Editing the data file

ModuleList data is saved automtically as a JSON file `[JAR file location]/data/moduleplan.json`
Advanced users are welcome to update data directly by editing that data file.
<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ModCraft will discard all data and start with a default data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
</box>



--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the default data file it creates with the file that contains the data of your previous ModCraft home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action           | Format, Examples                                                                    |
|------------------|-------------------------------------------------------------------------------------|
| **add**          | `add MODULE_CODE y/YEAR s/SEMESTER g/GRADE`<br> e.g., `add CS2106 y/3 s/1 g/IP`     |
| **delete**       | `delete MODULE_CODE` <br> e.g., `delete CS2040S`                                    |
| **edit**         | `edit MODULE_CODE [y/YEAR] [s/SEMESTER] [g/GRADE]` <br> e.g., `edit MA2001 y/1 s/2` |
| **info**         | `info MODULE_CODE`<br> e.g., `info CS3230`                                          |
| **calculateCAP** | `calculateCAP`                                                                      | 
| **calculateMC**  | `calculateMC`                                                                       |
| **help**         | `help`                                                                              |


## Glossary

- Command Line Interface: A display that allows you to type commands to interact with the application.
- Graphical User Interface: A user-friendly display that allows you to see the effects of your actions in the application. 
