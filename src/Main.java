public class Main {
    public static void main(String[] args) {
        System.out.println("Mini Hospital Emergency Management System");

        // Temporary Section 2 tests for the manually implemented BST.
        PatientBST patientTree = new PatientBST();

        System.out.println("\nDisplaying an empty tree:");
        patientTree.displayInOrder();

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
    }
}
