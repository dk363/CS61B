public class ArrayDeque {
    private int[] items;
    private int front;
    private int last;
    private int size;

    public ArrayDeque() {
        items = new int[8];
        front = 3;
        last = 4;
        size = 0;
    }

    public void addFirst(int i) {
        if (size == items.length) {
            int[] p = items;
            items = new int[size * 2];
            for (int j = 0; j < size; j++) {
                items[front + j] = p[front + j];
            }
        }

        if (front == 0) {
            front = items.length - 1;
            items[front] = i;
        } else {
            front = front - 1;
            items[front] = i;
            size++;
        }
    }

    public void addLast(int i) {
        if (size == items.length) {
            int[] p = items;
            items = new int[size * 2];
            for (int j = 0; j < size; j++) {
                items[front + j] = p[front + j];
            }
        }

        if (last == items.length - 1) {
            last = 0;
            items[last] = i;
        } else {
            last = last + 1;
            items[last] = i;
            size++;
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int p = front;
        while (p != last) {
            System.out.print(items[p] + " ");
            p++;
        }
    }

    public int removeFirst() {
        int p = front;
        front = front + 1;
        size--;
        if (size < items.length / 4 && items.length < 16) {
            int[] p2 = items;
            items = new int[items.length / 2];
            for (int j = 0; j < size; j++) {
                items[j] = p2[front + j];
            }
        }
        return items[p];
    }

    public int removeLast() {
        int p = last;
        last = last - 1;
        size--;
        if (size < items.length / 4 && items.length < 16) {
            int[] p2 = items;
            items = new int[items.length / 2];
            for (int j = 0; j < size; j++) {
                items[j] = p2[front + j];
            }
        }
        return items[p];
    }

    public int get(int index) {
        return items[front + index];
    }
}
