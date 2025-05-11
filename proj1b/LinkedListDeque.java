public class LinkedListDeque<T> implements Deque<T> {
    private Node<T> sentinel;
    private Node<T> last;
    private int size;

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T i, Node<T> p, Node<T> n) {
            item = i;
            next = n;
            prev = p;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        last = sentinel;
    }

    @Override
    public void addFirst(T i) {
        Node<T> node = new Node<>(i, sentinel, sentinel.next);
        sentinel.next = node;
        node.next.prev = node;

        if (size == 0) {
            last = node;
        }
        size++;
    }

    @Override
    public void addLast(T i) {
        Node<T> node = new Node<>(i, last, sentinel);
        sentinel.prev = node;
        last.next = node;
        last = node;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node<T> p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    @Override
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

    @Override
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

    @Override
    public T get(int index) {
        Node<T> p = sentinel.next;
        while (index != 0) {
            p = p.next;
            index--;
        }
        return p.item;
    }


    private T getRecursiveHelper(int index, Node<T> p) {
        if (p == sentinel) {
            return null;
        }
        if (index == 0) {
            return p.item;
        } else {
            return getRecursiveHelper(index - 1, p.next);
        }
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(index, sentinel.next);
    }
}
