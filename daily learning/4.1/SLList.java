public class SLList<T> implements List61B<T> {
    /** The first node in the list. */
    private Node first;
    private int size;

    /** A nested class representing a node in the singly linked list. */
    private class Node {
        public T item;
        public Node next;

        public Node(T i, Node n) {
            item = i;
            next = n;
        }
    }

    /** Creates an empty list. */
    public SLList() {
        first = null;
        size = 0;
    }

    /** Creates a list with one item. */
    public SLList(T x) {
        first = new Node(x, null);
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(T x) {
        first = new Node(x, first);
        size++;
    }

    /** Returns the first item in the list. */
    public T getFirst() {
        if (first == null) {
            return null;
        }
        return first.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(T x) {
        if (first == null) {
            addFirst(x);
            return;
        }
        Node p = first;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new Node(x, null);
        size++;
    }

    /** Returns the item at the given index (0-based). */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node p = first;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public T getLast() {
        return get(size - 1);
    }

    public T removeLast() {
        if (first == null) {
            return null; // 或者抛出异常
        }
        if (first.next == null) {
            T value = first.item;
            first = null;
            size--;
            return value;
        }

        Node<T> current = first;
        while (current.next.next != null) {
            current = current.next;
        }
        T value = current.next.item;
        current.next = null;
        size--;
        return value;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }
}
