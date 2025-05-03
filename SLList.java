import java.util.NoSuchElementException;

/** An SLList is a list of integers, with a sentinel node for simplicity. */
public class SLList {
    /** The sentinel node does not store real data. */
    private IntNode sentinel;
    private IntNode last;
    private int size;

    /** Node class for the singly linked list. */
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    /** Creates an empty list. */
    public SLList() {
        sentinel = new IntNode(0, null);
        last = sentinel;
        size = 0;
    }

    /** Creates a list with one integer. */
    public SLList(int x) {
        sentinel = new IntNode(0, null);
        IntNode node = new IntNode(x, null);
        sentinel.next = node;
        last = node;
        size = 1;
    }

    /** Creates a list from an array. */
    public SLList(int[] arr) {
        sentinel = new IntNode(0, null);
        last = sentinel;
        size = 0;
        for (int num : arr) {
            addLast(num);  // Maintain order
        }
    }

    /** Adds x to the front of the list. */
    public void addFirst(int x) {
        IntNode node = new IntNode(x, sentinel.next);
        sentinel.next = node;
        if (size == 0) {
            last = node;
        }
        size++;
    }

    /** Returns the first item in the list. */
    public int getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty.");
        }
        return sentinel.next.item;
    }

    /** Adds x to the end of the list. */
    public void addLast(int x) {
        IntNode node = new IntNode(x, null);
        last.next = node;
        last = node;
        size++;
    }

    /** Deletes the first item in the list. */
    public void deleteFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot delete from an empty list.");
        }
        sentinel.next = sentinel.next.next;
        size--;
        if (size == 0) {
            last = sentinel;
        }
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Prints the list items from front to end. */
    public void printList() {
        IntNode p = sentinel.next;
        while (p != null) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    /** Test code. */
    public static void main(String[] args) {
        SLList list = new SLList(new int[]{10, 20, 30});
        list.addFirst(5);        // 5 -> 10 -> 20 -> 30
        list.addLast(40);        // 5 -> 10 -> 20 -> 30 -> 40
        list.printList();        // Output: 5 10 20 30 40
        list.deleteFirst();      // Delete 5
        list.printList();        // Output: 10 20 30 40
        System.out.println("Size: " + list.size()); // Output: 4
    }
}
