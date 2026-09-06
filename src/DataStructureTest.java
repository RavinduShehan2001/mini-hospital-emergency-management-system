public class DataStructureTest {
    private static int passedChecks = 0;

    public static void main(String[] args) {
        testBST();
        testQueue();
        testStack();
        testVisitHistory();
        System.out.println("\nPASS: " + passedChecks + " automated data-structure checks.");
        System.out.println("Display examples above include their expected order for manual comparison.");
    }

    private static void testBST() {
        System.out.println("\nBST TESTS");
        PatientBST tree = new PatientBST();
        check(tree.searchPatient(105) == null, "Search an empty tree");
        check(!tree.deletePatient(105), "Delete from an empty tree");
        tree.displayInOrder();
        tree.insertPatient(null);
        check(tree.searchPatient(105) == null, "Null insertion leaves tree empty");

        int[] ids = {105, 102, 110, 101, 107};
        for (int id : ids) {
            Patient patient = makePatient(id);
            tree.insertPatient(patient);
            check(tree.searchPatient(id) == patient, "Insert and search patient " + id);
        }
        Patient original = tree.searchPatient(102);
        tree.insertPatient(makePatient(102));
        check(tree.searchPatient(102) == original, "Duplicate ID preserves the original patient");
        check(tree.searchPatient(106) == null, "Search a missing ID between existing IDs");
        check(tree.searchPatient(999) == null, "Search a missing ID above the tree range");
        check(tree.searchPatient(100) == null, "Search a missing ID below the tree range");
        check(!tree.deletePatient(999), "Delete a missing patient");
        System.out.println("Expected in-order IDs: 101, 102, 105, 107, 110");
        tree.displayInOrder();

        check(tree.deletePatient(101), "Delete a leaf");
        check(tree.searchPatient(101) == null, "Deleted leaf is absent");
        check(tree.deletePatient(110), "Delete a node with one left child");
        check(tree.searchPatient(107) != null, "The child remains reachable");
        Patient successor = tree.searchPatient(107);
        check(tree.deletePatient(105), "Delete a node with two children");
        check(tree.searchPatient(105) == null, "Deleted two-child node is absent");
        check(tree.searchPatient(107) == successor, "Successor retains its patient object");
        System.out.println("Expected remaining in-order IDs: 102, 107");
        tree.displayInOrder();
        check(tree.deletePatient(107), "Delete a root with one left child");
        check(tree.deletePatient(102), "Delete the last patient");
        check(!tree.deletePatient(102), "Repeated deletion on the empty tree is safe");

        tree.insertPatient(makePatient(10));
        tree.insertPatient(makePatient(20));
        check(tree.deletePatient(10), "Delete a root with one right child");
        check(tree.searchPatient(20) != null, "The right child becomes reachable as root");

        PatientBST deeperTree = new PatientBST();
        int[] deeperIds = {50, 30, 80, 60, 90, 55, 57};
        for (int id : deeperIds) {
            deeperTree.insertPatient(makePatient(id));
        }
        check(deeperTree.deletePatient(50), "Delete with a deeper successor");
        check(deeperTree.searchPatient(57) != null, "Successor's right child is preserved");
        check(deeperTree.searchPatient(50) == null, "Old root is absent");
        System.out.println("Expected deeper-tree IDs: 30, 55, 57, 60, 80, 90");
        deeperTree.displayInOrder();
    }

    private static void testQueue() {
        System.out.println("\nQUEUE TESTS");
        EmergencyQueue queue = new EmergencyQueue();
        check(queue.isEmpty(), "New queue is empty");
        check(queue.dequeue() == null, "Dequeue an empty queue");
        check(!queue.containsPatient(103), "Empty queue contains no patient");
        queue.displayQueue();
        queue.enqueue(null);
        check(queue.isEmpty(), "Null enqueue leaves queue empty");

        Patient first = makePatient(103);
        Patient second = makePatient(101);
        Patient third = makePatient(102);
        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);
        check(!queue.isEmpty(), "Multiple enqueue creates a nonempty queue");
        check(queue.containsPatient(103), "Find waiting patient at front");
        check(queue.containsPatient(101), "Find waiting patient in middle");
        check(queue.containsPatient(102), "Find waiting patient at rear");
        check(!queue.containsPatient(999), "Missing patient is not waiting");
        System.out.println("Expected queue IDs from front to rear: 103, 101, 102");
        queue.displayQueue();
        check(queue.dequeue() == first, "FIFO removes the first arrival");
        check(!queue.containsPatient(103), "Removed patient is no longer waiting");
        queue.enqueue(first);
        check(queue.dequeue() == second, "Second arrival leaves next");
        check(queue.dequeue() == third, "Third arrival follows second");
        check(queue.dequeue() == first, "Requeued patient leaves last");
        check(queue.isEmpty(), "Removing the final patient empties queue");
        check(queue.dequeue() == null, "Dequeue after draining is safe");
        queue.enqueue(second);
        check(queue.dequeue() == second, "Reuse after becoming empty");
        check(queue.isEmpty(), "Reused queue becomes empty again");
    }

    private static void testStack() {
        System.out.println("\nSTACK TESTS");
        TreatmentStack stack = new TreatmentStack();
        check(stack.isEmpty(), "New stack is empty");
        check(stack.pop() == null, "Pop an empty stack");
        stack.displayTreatmentHistory();
        stack.push(null);
        check(stack.isEmpty(), "Null push leaves stack empty");

        TreatmentRecord first = makeTreatment(203);
        TreatmentRecord second = makeTreatment(201);
        TreatmentRecord third = makeTreatment(202);
        stack.push(first);
        stack.push(second);
        stack.push(third);
        check(!stack.isEmpty(), "Multiple pushes create a nonempty stack");
        System.out.println("Expected stack IDs from top to bottom: 202, 201, 203");
        stack.displayTreatmentHistory();
        check(stack.pop() == third, "LIFO removes the last pushed record");
        stack.push(third);
        check(stack.pop() == third, "Push after a pop becomes the new top");
        check(stack.pop() == second, "Second pushed record leaves next");
        check(stack.pop() == first, "First pushed record leaves last");
        check(stack.isEmpty(), "Removing the final record empties stack");
        check(stack.pop() == null, "Pop after draining is safe");
        stack.push(first);
        check(stack.pop() == first, "Reuse an empty stack");
        check(stack.isEmpty(), "Reused stack becomes empty again");
    }

    private static void testVisitHistory() {
        System.out.println("\nVISIT HISTORY TESTS");
        Patient firstPatient = makePatient(101);
        Patient secondPatient = makePatient(102);
        VisitHistory history = firstPatient.getVisitHistory();
        VisitHistory otherHistory = secondPatient.getVisitHistory();
        check(history != otherHistory, "Each patient owns a separate history");
        check(history.searchVisit("V001") == null, "Search an empty history");
        check(!history.removeVisit("V001"), "Remove from an empty history");
        history.displayVisits();
        history.addVisit(null);
        check(history.searchVisit("V001") == null, "Null visit is not stored");

        Visit first = makeVisit("V001");
        Visit second = makeVisit("V002");
        Visit middle = makeVisit("V004");
        Visit last = makeVisit("V005");
        history.addVisit(first);
        history.addVisit(second);
        history.addVisit(middle);
        history.addVisit(last);
        Visit otherVisit = makeVisit("V003");
        otherHistory.addVisit(otherVisit);
        check(history.searchVisit(new String("V001")) == first, "Search compares String contents");
        check(history.searchVisit("V004") == middle, "Search a middle visit");
        check(history.searchVisit("V005") == last, "Search the last visit");
        check(history.searchVisit("V999") == null, "Search a missing visit");
        check(history.searchVisit(null) == null, "Null search ID is safe");
        check(!history.removeVisit(null), "Null removal ID is safe");
        check(!history.removeVisit("V999"), "Remove a missing visit");
        System.out.println("Expected patient 101 visit IDs: V001, V002, V004, V005");
        history.displayVisits();
        check(history.removeVisit("V001"), "Remove the head visit");
        check(history.searchVisit("V001") == null, "Removed head is absent");
        check(history.removeVisit("V004"), "Remove a middle visit");
        check(history.searchVisit("V005") == last, "Node after removed middle stays connected");
        check(history.removeVisit("V005"), "Remove the last visit");
        check(history.searchVisit("V002") == second, "Remaining visit is preserved");
        System.out.println("Expected remaining patient 101 visit ID: V002");
        history.displayVisits();
        history.addVisit(last);
        check(history.searchVisit("V005") == last, "Append after removing the last visit");
        check(history.removeVisit("V002"), "Remove the new head");
        check(history.removeVisit("V005"), "Remove the only remaining visit");
        check(!history.removeVisit("V005"), "Removal after draining is safe");
        history.addVisit(first);
        check(history.searchVisit("V001") == first, "Reuse an empty history");
        check(otherHistory.searchVisit("V003") == otherVisit, "Other patient's visit is unchanged");
        check(otherHistory.searchVisit("V001") == null, "Visits do not leak between patients");
    }

    private static Patient makePatient(int id) {
        return new Patient(id, "Patient " + id, 25, "0771234567", "Condition " + id);
    }

    private static TreatmentRecord makeTreatment(int id) {
        return new TreatmentRecord(id, 101, "Patient 101", "Treatment " + id, "2026-09-06");
    }

    private static Visit makeVisit(String id) {
        return new Visit(id, "2026-09-06", "Dr. Silva", "Review", "Observation completed");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAIL: " + description);
        }
        passedChecks++;
        System.out.println("PASS: " + description);
    }
}
