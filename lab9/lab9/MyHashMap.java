package lab9;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Kuangdi Xu
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;
    private Set<K> keySet;

    private double loadFactor() {
        return (double) size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        keySet = new HashSet<>();
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
        keySet = new HashSet<>();
    }

    /**
     * Computes the hash function of the given key. Consists of
     * computing the hashcode, followed by modding by the number of buckets.
     * To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int hashCode = hash(key);
        ArrayMap<K, V> b = buckets[hashCode];
        return b.get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");

        int hashCode = hash(key);
        ArrayMap<K, V> b = buckets[hashCode];
        boolean isNewKey = !containsKey(key);
        b.put(key, value);
        if (isNewKey) {
            keySet.add(key);
            size += 1;
        }
        if (loadFactor() > MAX_LF) {
            resize(size * 2);
        }
    }

    private void resize(int capacity) {
        ArrayMap<K, V>[] newBuckets = new ArrayMap[capacity];

        for (int i = 0; i < capacity; i++) {
            newBuckets[i] = new ArrayMap<>();
        }

        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket.keySet()) {
                V value = bucket.get(key);
                int newHashCode = Math.floorMod(key.hashCode(), capacity); // ✅ 修正点
                newBuckets[newHashCode].put(key, value);
            }
        }

        buckets = newBuckets;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /// ///////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("calls remove() with a null key");
        if (keySet.contains(key)) {
            V oldValue = get(key);

            int hashCode = hash(key);
            buckets[hashCode].remove(key);
            keySet.remove(key);
            size -= 1;

            return oldValue;
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls remove() with a null key");

        V curVal = get(key);
        if (curVal != null && get(key).equals(value)) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<>();
        for (ArrayMap<K, V> b : buckets) {
            for (K key : b.keySet()) {
                keys.add(key);
            }
        }
        return keys.iterator();
    }
}
