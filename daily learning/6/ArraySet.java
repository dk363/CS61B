import java.util.Iterator;

public class ArraySet<T> implements Iterable<T> {
    private T[] items;
    private int size;

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key.
     */
    public boolean contains(T x) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    /*  Associates the specified value with the specified key in this map.
        users cannot add null.
        Throws an IllegalArgumentException if the key is null. */
    public void add(T x) {
        if (x == null) {
            throw new IllegalArgumentException("can't add null");
        }
        if (!contains(x)) {
            items[size] = x;
            size++;
        }
        return;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

//    public static void main(String[] args) {
//        ArraySet<String> s = new ArraySet<>();
//        // s.add(null);
//        /*  Approach 1: Don't add null to the array if it is passed into add
//            Approach 2: Change the contains method to account for the case if items[i] == null.
//         */
//        s.add("horse");
//        s.add("fish");
//        s.add("house");
//        s.add("fish");
//        System.out.println(s.contains("horse"));
//        System.out.println(s.size());
//    }

    /* Also to do:
    1. Make ArraySet implement the Iterable<T> interface.
    2. Implement a toString method.
    3. Implement an equals() method.
    */
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int currentPos;

        public ArraySetIterator() {
            currentPos = 0;
        }

        public boolean hasNext() {
            return currentPos < size;
        }

        public T next() {
            T returnItem = items[currentPos];
            currentPos += 1;
            return returnItem;
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "{ }";
        }
        StringBuilder returnString = new StringBuilder("{");
        for (int i = 0; i < size - 1; i++) {
            returnString.append(items[i]);
            returnString.append(", ");
        }
        returnString.append(items[size -1]);
        returnString.append("}");

        return returnString.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        ArraySet<T> o = (ArraySet<T>) other;

        if (o.size() != this.size()) {
            return false;
        }

        for (T item : this) {
            /*  Remember, a set is an unordered collection of unique elements.
                So, for two sets to be considered equal,
                you just need to check if they have the same elements.
             */
            if (!o.contains(item)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        ArraySet<Integer> aset = new ArraySet<>();
        aset.add(5);
        aset.add(23);
        aset.add(42);

        //iteration
        for (int i : aset) {
            System.out.println(i);
        }

        //toString
        System.out.println(aset);

        //equals
        ArraySet<Integer> aset2 = new ArraySet<>();
        aset2.add(5);
        aset2.add(23);
        aset2.add(42);

        System.out.println(aset.equals(aset2));
        System.out.println(aset.equals(null));
        System.out.println(aset.equals("fish"));
        System.out.println(aset.equals(aset));
        }
}
