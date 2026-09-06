public class VisitHistory {
    private Node head;

    private static class Node {
        private Visit visit;
        private Node next;

        private Node(Visit visit) {
            this.visit = visit;
        }
    }

    public void addVisit(Visit visit) {
        if (visit == null) {
            System.out.println("Cannot add a null visit to the history.");
            return;
        }

        Node newNode = new Node(visit);
        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    public void displayVisits() {
        if (head == null) {
            System.out.println("Visit history is empty. No visits are recorded.");
            return;
        }

        System.out.println("Visits in the order they were added:");
        Node current = head;
        while (current != null) {
            System.out.println(current.visit);
            System.out.println();
            current = current.next;
        }
    }
}
