public class TreatmentStack {
    private Node top;

    private static class Node {
        private TreatmentRecord treatmentRecord;
        private Node next;

        private Node(TreatmentRecord treatmentRecord) {
            this.treatmentRecord = treatmentRecord;
        }
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(TreatmentRecord treatmentRecord) {
        if (treatmentRecord == null) {
            System.out.println("Cannot add a null treatment record to the stack.");
            return;
        }

        Node newNode = new Node(treatmentRecord);
        newNode.next = top;
        top = newNode;
    }

    public TreatmentRecord pop() {
        if (isEmpty()) {
            return null;
        }

        TreatmentRecord treatmentRecord = top.treatmentRecord;
        top = top.next;
        return treatmentRecord;
    }

    public void displayTreatmentHistory() {
        if (isEmpty()) {
            System.out.println("The treatment stack is empty. No completed treatments are recorded.");
            return;
        }

        System.out.println("Treatment history from top to bottom (most recently added first):");
        Node current = top;
        while (current != null) {
            System.out.println(current.treatmentRecord);
            System.out.println();
            current = current.next;
        }
    }
}
