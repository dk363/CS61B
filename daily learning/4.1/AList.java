public class AList<T> implements List61B<T> {
    private T[] items;
    private int size;

    /** Creates an empty list with initial capacity of 100. */
    public AList() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /** Adds x to the end of the list. */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = x;
        size++;
    }

    /** Returns the item from the back of the list. */
    public T getLast() {
        return items[size - 1];
    }

    /** Gets the ith item in the list (0-indexed). */
    public T get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and returns deleted item. */
    public T removeLast() {
        T lastItem = getLast();
        items[size - 1] = null;
        size--;
        return lastItem;
    }

    /** Resizes the array to the target capacity. */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }
}
