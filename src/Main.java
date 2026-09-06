public class Main {
    public static void main(String[] args) {
        System.out.println("Mini Hospital Emergency Management System");

        PatientBST patientTree = new PatientBST();

        System.out.println("\nTesting an empty tree:");
        patientTree.displayInOrder();
        displaySearchResult(patientTree, 105);
        deleteAndDisplay(patientTree, 105);

        System.out.println("\nInserting patient IDs: 105, 102, 110, 101, 107");
        patientTree.insertPatient(new Patient(105, "Nimal Perera", 25,
                "0771234567", "Fever"));
        patientTree.insertPatient(new Patient(102, "Amali Silva", 30,
                "0712345678", "Headache"));
        patientTree.insertPatient(new Patient(110, "Kamal Fernando", 45,
                "0723456789", "Back pain"));
        patientTree.insertPatient(new Patient(101, "Saman Kumara", 19,
                "0754567890", "Cough"));
        patientTree.insertPatient(new Patient(107, "Nadeesha Dias", 36,
                "0765678901", "Ankle sprain"));

        System.out.println("\nAttempting to insert duplicate patient ID 102:");
        patientTree.insertPatient(new Patient(102, "Duplicate Patient", 50,
                "0700000000", "Duplicate test"));

        System.out.println("\nPatients in ascending Patient ID order:");
        patientTree.displayInOrder();

        System.out.println("\nTesting successful and unsuccessful searches:");
        displaySearchResult(patientTree, 107);
        displaySearchResult(patientTree, 999);

        System.out.println("\nCase 1: Delete leaf node 101.");
        deleteAndDisplay(patientTree, 101);

        System.out.println("\nCase 2: Delete node 110, which has only a left child (107).");
        deleteAndDisplay(patientTree, 110);

        System.out.println("\nCase 3: Delete root 105, which has two children (102 and 107).");
        deleteAndDisplay(patientTree, 105);

        System.out.println("\nConfirming deleted patient 105 is no longer found:");
        displaySearchResult(patientTree, 105);

        System.out.println("\nAttempting to delete missing patient 999:");
        deleteAndDisplay(patientTree, 999);
    }

    private static void displaySearchResult(PatientBST patientTree, int patientId) {
        Patient patient = patientTree.searchPatient(patientId);
        if (patient == null) {
            System.out.println("Patient ID " + patientId + " was not found.");
        } else {
            System.out.println("Patient found:");
            System.out.println(patient);
        }
    }

    private static void deleteAndDisplay(PatientBST patientTree, int patientId) {
        if (patientTree.deletePatient(patientId)) {
            System.out.println("Patient ID " + patientId + " was deleted.");
        } else {
            System.out.println("Patient ID " + patientId + " was not found. Nothing was deleted.");
        }

        System.out.println("Patients after deletion attempt:");
        patientTree.displayInOrder();
    }
}
