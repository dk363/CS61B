interface Deque<T> {
    void addFirst(T i);
    void addLast(T i);
    boolean isEmpty();
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
}
