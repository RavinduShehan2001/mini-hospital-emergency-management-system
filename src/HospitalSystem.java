import java.util.NoSuchElementException;
import java.util.Scanner;

public class HospitalSystem {
    private PatientBST patientTree;
    private EmergencyQueue emergencyQueue;
    private TreatmentStack treatmentStack;
    private Scanner scanner;

    public HospitalSystem() {
        patientTree = new PatientBST();
        emergencyQueue = new EmergencyQueue();
        treatmentStack = new TreatmentStack();
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        try {
            while (running) {
                displayMenu();
                int choice = readNumber("Enter your choice: ", 1);

                switch (choice) {
                    case 1:
                        registerPatient();
                        break;
                    case 2:
                        searchPatient();
                        break;
                    case 3:
                        deletePatient();
                        break;
                    case 4:
                        patientTree.displayInOrder();
                        break;
                    case 5:
                        addEmergencyPatient();
                        break;
                    case 6:
                        treatNextPatient();
                        break;
                    case 7:
                        emergencyQueue.displayQueue();
                        break;
                    case 8:
                        recordTreatment();
                        break;
                    case 9:
                        treatmentStack.displayTreatmentHistory();
                        break;
                    case 10:
                        removeLatestTreatment();
                        break;
                    case 11:
                        addVisit();
                        break;
                    case 12:
                        searchVisit();
                        break;
                    case 13:
                        removeVisit();
                        break;
                    case 14:
                        displayVisitHistory();
                        break;
                    case 15:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Please choose an option from 1 to 15.");
                }
            }
        } catch (NoSuchElementException exception) {
            System.out.println("\nInput ended. Closing the hospital system.");
        } finally {
            scanner.close();
        }
    }

    private void displayMenu() {
        System.out.println("\n===== MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM =====");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Delete Patient");
        System.out.println("4. Display All Patients");
        System.out.println("5. Add Patient to Emergency Queue");
        System.out.println("6. Treat Next Emergency Patient");
        System.out.println("7. Display Emergency Queue");
        System.out.println("8. Record Completed Treatment");
        System.out.println("9. Display Treatment History");
        System.out.println("10. Remove Latest Treatment Record");
        System.out.println("11. Add Patient Visit");
        System.out.println("12. Search Patient Visit");
        System.out.println("13. Remove Patient Visit");
        System.out.println("14. Display Patient Visit History");
        System.out.println("15. Exit");
    }

    private void registerPatient() {
        int patientId = readNumber("Patient ID: ", 1);
        if (patientTree.searchPatient(patientId) != null) {
            System.out.println("Patient ID " + patientId + " already exists. Registration cancelled.");
            return;
        }

        String patientName = readText("Patient Name: ");
        int age = readNumber("Age: ", 0);
        String contactNumber = readText("Contact Number: ");
        String medicalCondition = readText("Medical Condition: ");

        Patient patient = new Patient(patientId, patientName, age, contactNumber, medicalCondition);
        patientTree.insertPatient(patient);
        System.out.println("Patient ID " + patientId + " registered successfully.");
    }

    private void searchPatient() {
        Patient patient = findPatientFromInput();
        if (patient != null) {
            System.out.println("Patient found:");
            System.out.println(patient);
        }
    }

    private void deletePatient() {
        int patientId = readNumber("Patient ID: ", 1);
        if (patientTree.deletePatient(patientId)) {
            System.out.println("Patient ID " + patientId + " deleted from the patient registry.");
        } else {
            System.out.println("Patient ID " + patientId + " was not found. Nothing was deleted.");
        }
    }

    private void addEmergencyPatient() {
        Patient patient = findPatientFromInput();
        if (patient == null) {
            return;
        }

        emergencyQueue.enqueue(patient);
        System.out.println("Patient ID " + patient.getPatientId() + " added to the emergency queue.");
    }

    private void treatNextPatient() {
        Patient patient = emergencyQueue.dequeue();
        if (patient == null) {
            System.out.println("The emergency queue is empty. No patient is waiting for treatment.");
            return;
        }

        System.out.println("Next patient removed from the queue for treatment:");
        System.out.println(patient);
        System.out.println("Use option 8 to record the completed treatment.");
    }

    private void recordTreatment() {
        Patient patient = findPatientFromInput();
        if (patient == null) {
            return;
        }

        int recordId = readNumber("Treatment Record ID: ", 1);
        String description = readText("Treatment Description: ");
        String date = readText("Treatment Date (YYYY-MM-DD): ");

        TreatmentRecord record = new TreatmentRecord(recordId, patient.getPatientId(),
                patient.getPatientName(), description, date);
        treatmentStack.push(record);
        System.out.println("Treatment record " + recordId + " saved successfully.");
    }

    private void removeLatestTreatment() {
        TreatmentRecord record = treatmentStack.pop();
        if (record == null) {
            System.out.println("The treatment stack is empty. No record can be removed.");
            return;
        }

        System.out.println("Removed the most recently added treatment record:");
        System.out.println(record);
    }

    private void addVisit() {
        Patient patient = findPatientFromInput();
        if (patient == null) {
            return;
        }

        String visitId = readText("Visit ID: ");
        if (patient.getVisitHistory().searchVisit(visitId) != null) {
            System.out.println("Visit ID " + visitId + " already exists for this patient. Visit was not added.");
            return;
        }

        String date = readText("Visit Date (YYYY-MM-DD): ");
        String doctorName = readText("Doctor Name: ");
        String diagnosis = readText("Diagnosis: ");
        String treatment = readText("Treatment: ");

        patient.getVisitHistory().addVisit(new Visit(visitId, date, doctorName, diagnosis, treatment));
        System.out.println("Visit " + visitId + " added for patient " + patient.getPatientId() + ".");
    }

    private void searchVisit() {
        Patient patient = findPatientFromInput();
        if (patient == null) {
            return;
        }

        String visitId = readText("Visit ID: ");
        Visit visit = patient.getVisitHistory().searchVisit(visitId);
        if (visit == null) {
            System.out.println("Visit ID " + visitId + " was not found for patient " + patient.getPatientId() + ".");
        } else {
            System.out.println("Visit found:");
            System.out.println(visit);
        }
    }

    private void removeVisit() {
        Patient patient = findPatientFromInput();
        if (patient == null) {
            return;
        }

        String visitId = readText("Visit ID: ");
        if (patient.getVisitHistory().removeVisit(visitId)) {
            System.out.println("Visit " + visitId + " removed for patient " + patient.getPatientId() + ".");
        } else {
            System.out.println("Visit ID " + visitId + " was not found. Nothing was removed.");
        }
    }

    private void displayVisitHistory() {
        Patient patient = findPatientFromInput();
        if (patient != null) {
            System.out.println("Visit history for patient " + patient.getPatientId()
                    + " (" + patient.getPatientName() + "):");
            patient.getVisitHistory().displayVisits();
        }
    }

    private Patient findPatientFromInput() {
        int patientId = readNumber("Patient ID: ", 1);
        Patient patient = patientTree.searchPatient(patientId);
        if (patient == null) {
            System.out.println("Patient ID " + patientId + " was not found. Register the patient first.");
        }
        return patient;
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private int readNumber(String prompt, int minimum) {
        while (true) {
            String input = readText(prompt);
            try {
                int number = Integer.parseInt(input);
                if (number >= minimum) {
                    return number;
                }
                System.out.println("Please enter a number of at least " + minimum + ".");
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number within the integer range.");
            }
        }
    }
}
