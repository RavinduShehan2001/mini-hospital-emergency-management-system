# Mini Hospital Emergency Management System

## About the Project

This is a console-based Java application created for the CIT300 Data Structures and Algorithms individual mid assignment. It manages patient records, an emergency waiting queue, completed treatment records, and each patient's hospital visit history.

The project uses ordinary Java classes and manually implemented nodes. The BST, queue, stack, and singly linked list do not use Java collection classes as replacements. There are no external dependencies or testing frameworks.

## Features

- **Patient registration:** Store Patient ID, name, age, contact number, and medical condition. Duplicate Patient IDs are rejected.
- **Patient search:** Find a registered patient by Patient ID using BST comparisons.
- **Patient deletion:** Remove a registration using BST deletion. A patient who is still waiting in the emergency queue cannot be deleted.
- **Displaying patient records:** Display registered patients in ascending Patient ID order.
- **Emergency queue:** Add registered patients, display waiting patients, and remove the next patient in arrival order. The menu prevents adding the same waiting patient twice.
- **Completed treatment history:** Record a completed treatment, display records from newest added to oldest added, and remove the most recently added record.
- **Patient visit history:** Add, search, remove, and display visits belonging to a particular patient. Visits display in the order they were added.
- **Input handling:** Reject blank fields, invalid numeric input, non-positive Patient/Record IDs, and ages outside 0-150. Missing records and empty structures produce understandable messages.

## Data Structures Used

### Binary Search Tree (BST)

`PatientBST` stores patients using Patient ID as the key. Each node contains a `Patient`, a left child reference, and a right child reference. Smaller IDs go left; larger IDs go right.

Insertion and search follow these comparisons. Deletion handles a leaf, a node with one child, and a node with two children. For two children, the replacement is the smallest patient in the right subtree, called the in-order successor. In-order traversal visits left, current, then right to display ascending IDs.

### Queue

`EmergencyQueue` stores waiting patients and follows **FIFO: First In, First Out**. Each node holds a `Patient` and a `next` reference. `front` points to the next patient to leave; `rear` points to the last waiting patient.

Enqueue adds at the rear. Dequeue removes from the front. Both references become `null` when the final patient leaves. The queue stores the same patient objects found in the BST.

### Stack

`TreatmentStack` stores completed `TreatmentRecord` objects and follows **LIFO: Last In, First Out**. Each node holds a treatment record and a `next` reference. `top` points to the most recently pushed record.

Push adds at the top. Pop removes from the top. For example, pushing A, B, and C means C is removed first. Ordering depends on when records are pushed, not their IDs or date text.

### Singly Linked List

`VisitHistory` stores a patient's visits. Each node contains a `Visit` and a `next` reference. `head` points to the first node, and the last node's `next` is `null`.

New visits are appended at the end. Searching follows the links and compares Visit IDs with `String.equals()`. Removal updates `head` or connects the previous node to the following node. Every `Patient` creates its own `VisitHistory`, so patients do not share one list.

The BST's search cost depends on its height: about O(log n) when reasonably balanced, but O(n) for a chain-shaped tree. Queue enqueue/dequeue and stack push/pop are O(1). Queue membership checks and visit-list operations can take O(n) because they follow nodes one at a time.

## Project Structure

```text
mini-hospital-emergency-management-system/
|-- .gitignore
|-- README.md
|-- src/
|   |-- Main.java              # Starts HospitalSystem
|   |-- HospitalSystem.java    # Console menu, input checks, and workflows
|   |-- Patient.java           # Patient details and that patient's VisitHistory
|   |-- PatientBST.java        # Manual BST insert/search/delete/in-order display
|   |-- EmergencyQueue.java    # Manual FIFO queue and waiting-patient lookup
|   |-- TreatmentRecord.java   # Record ID, patient ID/name, description, and date
|   |-- TreatmentStack.java    # Manual LIFO treatment history
|   |-- Visit.java             # Visit ID/date, doctor, diagnosis, and treatment
|   |-- VisitHistory.java      # Manual singly linked list of visits
|   `-- DataStructureTest.java # Standalone data-structure test runner
`-- out/                      # Local build output; generated and ignored by Git
```

Each data structure defines its own private `Node` class inside its source file. No separate node files are needed.

## How to Compile and Run

Use a Java Development Kit (JDK) with both `java` and `javac` available in the terminal. VS Code can be used to edit the source files and run these commands through its integrated terminal.

1. Open the project folder in VS Code.
2. Select **Terminal > New Terminal**. On Windows, PowerShell is suitable.
3. Make sure the terminal is in the project root, which contains `src` and `README.md`.
4. Check that the JDK commands work:

   ```powershell
   java -version
   javac -version
   ```

5. Compile all source files:

   ```powershell
   javac -d out src/*.java
   ```

6. Run the application:

   ```powershell
   java -cp out Main
   ```

Compilation creates `out` if needed. No compiler output normally means compilation succeeded. Recompile after changing a `.java` file. Edit the files in `src`, not the generated `.class` files.

To enable compiler warnings during a final check:

```powershell
javac -Xlint:all -d out src/*.java
```

The local review used JDK 24.0.2. No Maven, Gradle, or JUnit setup is required.

## Console Menu

```text
===== MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM =====
1. Register Patient
2. Search Patient
3. Delete Patient
4. Display All Patients
5. Add Patient to Emergency Queue
6. Treat Next Emergency Patient
7. Display Emergency Queue
8. Record Completed Treatment
9. Display Treatment History
10. Remove Latest Treatment Record
11. Add Patient Visit
12. Search Patient Visit
13. Remove Patient Visit
14. Display Patient Visit History
15. Exit
```

Enter the option number and press Enter. Follow the prompts, entering one answer per line. The menu appears again after each completed operation.

## Example Workflow

Start the application and follow this example using fictional patient details:

| Step | Menu option | Input / expected result |
|---|---|---|
| 1 | **1 - Register Patient** | ID `101`, name `Amali Silva`, age `30`, contact `0712345678`, condition `Headache`. |
| 2 | **2 - Search Patient** | Enter `101`; the patient's details appear. |
| 3 | **5 - Add Patient to Emergency Queue** | Enter `101`; the registered patient joins the queue. |
| 4 | **7 - Display Emergency Queue** | Patient `101` appears as waiting. |
| 5 | **6 - Treat Next Emergency Patient** | Patient `101` is removed from the queue and displayed. |
| 6 | **8 - Record Completed Treatment** | Patient ID `101`, record ID `201`, description `Assessment completed`, date `2026-09-06`. |
| 7 | **9 - Display Treatment History** | Treatment record `201` appears. |
| 8 | **11 - Add Patient Visit** | Patient ID `101`, visit ID `V001`, date `2026-09-06`, doctor `Dr. Silva`, diagnosis `Headache`, treatment `Assessment completed`. |
| 9 | **12 - Search Patient Visit** | Enter patient ID `101`, then visit ID `V001`; the visit details appear. |
| 10 | **14 - Display Patient Visit History** | Enter `101`; only this patient's visits are displayed. |
| 11 | **15 - Exit** | The program prints `Goodbye!` and exits. |

Option 6 only dequeues and displays a patient. Recording a completed treatment and adding a visit are separate menu actions. For the full workflow, record the treatment and visits before deleting the patient's registration.

## Testing

After compilation, run the included test class:

```powershell
java -cp out DataStructureTest
```

The runner uses ordinary Java methods and a simple `check()` helper. A successful condition prints `PASS`; a failed condition throws an `AssertionError` with a description. It runs separately from the interactive menu and does not require the `-ea` option.

The current runner finishes with:

```text
PASS: 82 automated data-structure checks.
Display examples above include their expected order for manual comparison.
```

Compare the printed display examples with the expected orders shown above them. The 82 checks do not automatically compare all displayed text.

The following operations were tested during development:

| Area | Cases covered |
|---|---|
| BST | Insert, duplicate rejection, existing/missing searches, leaf/one-child/two-child deletion, deeper successor handling, in-order display, and empty tree operations. |
| Queue | Multiple enqueue, FIFO dequeue, display, membership lookup, empty dequeue, and reuse after becoming empty. |
| Stack | Multiple push, LIFO pop, display, empty pop, and reuse after becoming empty. |
| Visit history | Add at the end, existing/missing searches, head/middle/last removal, display, empty operations, and independent histories. |
| Console | All 15 options, missing patients, duplicate registrations and waiting entries, blocked deletion of waiting patients, blank fields, letters/decimals/overflow in numeric fields, age boundaries, and input ending partway through an operation. |

Console behavior was also checked with scripted input during development. Those temporary scripts are in the ignored local `out` directory and are not required to compile, run, or use the included test runner. The example workflow above can be repeated manually from a fresh run.

Useful additional manual checks:

- Register IDs `105, 102, 110, 101, 107`; option 4 should display `101, 102, 105, 107, 110`.
- Before queueing these patients, delete `101`, then `110`, then `105` to demonstrate leaf, one-child, and two-child deletion.
- Queue two registered patients in an order different from their IDs. Option 6 should follow arrival order.
- Try adding the same waiting patient again or deleting that patient while they are waiting; both actions should be rejected.
- Push records `201`, then `202`. The history should display `202` first, and option 10 should remove it first.
- Give patient 101 visits `V001, V002` and another patient visit `V003`. Their displayed histories should remain separate.
- Try `abc`, `1.5`, and `2147483648` in a numeric field, a blank name, a negative ID, and age `999`. The program should ask for valid input.
- Try searching/removing missing visits and using dequeue/pop when the corresponding structure is empty.

## Assignment Requirements Covered

- [x] BST insert - completed
- [x] BST search - completed
- [x] BST delete, including all three deletion cases - completed
- [x] BST in-order traversal - completed
- [x] Queue enqueue/dequeue/display with FIFO behavior - completed
- [x] Stack push/pop/display with LIFO behavior - completed
- [x] Singly linked list add/search/remove/display - completed
- [x] Independent visit history for each patient - completed
- [x] Empty structure handling - completed
- [x] Duplicate Patient ID handling - completed
- [x] Interactive console menu and basic input validation - completed
- [x] Manual node implementations without built-in collection replacements - completed

## Scope and Limitations

- Data is kept in memory for the current run. Exiting the application clears it; there is no file or database storage.
- The emergency queue follows FIFO and does not prioritize patients by severity.
- Dates are non-empty strings. Use `YYYY-MM-DD`; calendar validity is not checked.
- Contact numbers are text to preserve leading zeros and `+`. Phone-number formats are not validated.
- Patient IDs and treatment record IDs must be positive Java integers. Ages must be from 0 to 150.
- Treatment record IDs are entered manually and are not checked for uniqueness. Use distinct IDs when demonstrating the program.
- The menu rejects duplicate Visit IDs within one patient's history. Visit IDs are case-sensitive; different patients may use the same Visit ID.
- Removing a treatment record does not remove a visit, and removing a visit does not remove a treatment record.
- Deleting a patient removes their registration and access to their visit history through the menu. Previously recorded treatment details remain in the stack.

## Submission Files

Include `README.md`, `.gitignore`, and the `.java` files in `src`. The `out/` directory, its temporary local test files, and compiled `.class` files are build/development artifacts and are ignored by Git. They are not needed in the source submission; the compilation command recreates the required class files.
