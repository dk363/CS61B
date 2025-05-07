public class ArrayDeque<T> {
    private T[] items;
    private int front;
    private int last;
    private int size;

    private static final int MIN_LENGTH_ARRAY = 16;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        front = 0;
        last = 0;
        size = 0;
    }

    private void reItemSize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(front + i) % items.length];
        }

        // 为什么不是size？
        // 用 items.length 从下标0开始
        // items.length 代表此时实际有的 item 个数
        front = 0;
        last = size;
        items = newItems;
    }

    public void addFirst(T i) {
        if (size == items.length) {
            reItemSize(size * 2);
        }
        front = (front - 1 + items.length) % items.length;
        // 为什么要加 items.length ?
        // items.length 有可能为 0 ，加上 items.length 后取余才可以达成目标
        // python中的 range(::-1) 有可能就是这样写的
        items[front] = i;
        size++;

    }

    public void addLast(T i) {
        if (size == items.length) {
            reItemSize(size * 2);
        }
        last = (last + 1) % items.length;
        items[last] = i;
        size++;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int current = front;
        for (int i = 0; i < size; i++) {
            System.out.print(items[current] + " ");
            current = (current + 1) % items.length;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        // 和下方的 get 不同，如果不特判特殊情况，可能会导致NullPointerException
        T p = items[front];
        items[front] = null;
        front = (front + 1) % items.length;
        size--;
        if (size > MIN_LENGTH_ARRAY && items.length < size * 0.25) {
            reItemSize(items.length / 2);
        }
        return p;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T p = items[last];
        last = (last - 1 + items.length) % items.length;
        items[last] = null;
        size--;
        if (size > MIN_LENGTH_ARRAY && items.length < size * 0.25) {
            reItemSize(items.length / 2);
        }
        return p;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            // index < 0 || index >= size 是用户输入错误，
            // 但是如果 size = 0 呢？ return null 自己会触发，因为上文的 remove 已经将去掉的数字格子还原成 null 了
            return null;
        }
        return items[(front + index) % items.length];
    }
}
