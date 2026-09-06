public class PatientBST {
    private Node root;

    // Each node holds one patient and links to its left and right children.
    private static class Node {
        private Patient patient;
        private Node left;
        private Node right;

        private Node(Patient patient) {
            this.patient = patient;
        }
    }

    public void insertPatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot insert a null patient.");
            return;
        }

        root = insertRecursive(root, patient);
    }

    private Node insertRecursive(Node current, Patient patient) {
        // An empty position is where the new node belongs.
        if (current == null) {
            return new Node(patient);
        }

        if (patient.getPatientId() < current.patient.getPatientId()) {
            current.left = insertRecursive(current.left, patient);
        } else if (patient.getPatientId() > current.patient.getPatientId()) {
            current.right = insertRecursive(current.right, patient);
        } else {
            System.out.println("Patient ID " + patient.getPatientId()
                    + " already exists. Duplicate patient was not inserted.");
        }

        // Return this subtree's root so its parent keeps the correct link.
        return current;
    }

    public Patient searchPatient(int patientId) {
        return searchRecursive(root, patientId);
    }

    private Patient searchRecursive(Node current, int patientId) {
        if (current == null) {
            return null;
        }

        if (patientId < current.patient.getPatientId()) {
            return searchRecursive(current.left, patientId);
        } else if (patientId > current.patient.getPatientId()) {
            return searchRecursive(current.right, patientId);
        }

        return current.patient;
    }

    public boolean deletePatient(int patientId) {
        if (searchPatient(patientId) == null) {
            return false;
        }

        root = deleteRecursive(root, patientId);
        return true;
    }

    private Node deleteRecursive(Node current, int patientId) {
        if (current == null) {
            return null;
        }

        if (patientId < current.patient.getPatientId()) {
            current.left = deleteRecursive(current.left, patientId);
        } else if (patientId > current.patient.getPatientId()) {
            current.right = deleteRecursive(current.right, patientId);
        } else {
            if (current.left == null && current.right == null) {
                return null;
            }

            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }

            Node successor = findSmallestNode(current.right);
            current.patient = successor.patient;
            current.right = deleteRecursive(current.right,
                    successor.patient.getPatientId());
        }

        return current;
    }

    private Node findSmallestNode(Node current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void displayInOrder() {
        if (root == null) {
            System.out.println("No patients registered. The tree is empty.");
            return;
        }

        displayInOrderRecursive(root);
    }

    private void displayInOrderRecursive(Node current) {
        if (current == null) {
            return;
        }

        // Visit the left subtree, the current patient, then the right subtree.
        displayInOrderRecursive(current.left);
        System.out.println(current.patient);
        System.out.println();
        displayInOrderRecursive(current.right);
    }
}
