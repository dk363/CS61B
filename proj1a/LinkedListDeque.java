public class LinkedListDeque<T> {
    private Node<T> sentinel;
    private Node<T> last;
    private int size;

    public static class Node<T> {
        public T item;
        public Node prev;
        public Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            next = n;
            prev = p;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node("??", null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public LinkedListDeque(T i) {
        sentinel = new Node("??", null, null);
        Node<T> node = new Node(i, sentinel, sentinel);
        sentinel.prev = node;
        sentinel.next = node;

        last = node;
        size = 1;
    }

    public void addFirst(T i) {
        Node<T> node = new Node(i, sentinel, sentinel.next);
        sentinel.next = node;

        if (size == 0) {
            last = node;
        }
        size++;
    }

    public void addLast(T i) {
        Node<T> node = new Node(i, last, sentinel);
        sentinel.prev = node;
        last.next = node;
        last = node;
        size++;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node<T> p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node<T> p = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        if (size == 0) {
            last = sentinel;
        }
        return p.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node<T> p = last;
        last = last.prev;
        last.next = sentinel;
        sentinel.prev = last;
        size--;
        return p.item;
    }

    public T get(int index) {
        Node<T> p = sentinel.next;
        while (index != 0) {
            p = p.next;
            index--;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        Node<T> p = sentinel.next;
        if (index == 0) {
            return p.item;
        }
        else {
            return getRecursive(index - 1);
        }
    }
}
