public class EmergencyQueue {
    private Node front;
    private Node rear;

    private static class Node {
        private Patient patient;
        private Node next;

        private Node(Patient patient) {
            this.patient = patient;
        }
    }

    public boolean isEmpty() {
        return front == null;
    }

    public boolean containsPatient(int patientId) {
        Node current = front;
        while (current != null) {
            if (current.patient.getPatientId() == patientId) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void enqueue(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot add a null patient to the emergency queue.");
            return;
        }

        Node newNode = new Node(patient);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public Patient dequeue() {
        if (isEmpty()) {
            return null;
        }

        Patient patient = front.patient;
        front = front.next;
        if (front == null) {
            rear = null;
        }

        return patient;
    }

    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("The emergency queue is empty. No patients are waiting.");
            return;
        }

        System.out.println("Waiting patients from front to rear:");
        Node current = front;
        while (current != null) {
            System.out.println(current.patient);
            System.out.println();
            current = current.next;
        }
    }
}
