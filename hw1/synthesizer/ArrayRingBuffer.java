// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;

import java.util.NoSuchElementException;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> implements Iterable<T> {
    /* Index for the next dequeue or peek. */
    private int first = 0;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last = 0;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int fillCount = 0;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        this.rb = (T[])new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = (last + 1) % capacity;
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T retVal = rb[first];
        rb[first] = null;
        first = (first + 1) % capacity;
        fillCount--;
        return retVal;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (fillCount == 0) {
            throw new RuntimeException("there is no item");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int ptr;
        /* 计算已经 iterate 的元素 */
        private int count;

        public ArrayRingBufferIterator() {
            ptr = first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < fillCount;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException("there is no next item");
            T returnItem = rb[ptr];
            ptr = (ptr + 1) % capacity;
            count++;
            return returnItem;
        }

    }
    // TODO: When you get to part 5, implement the needed code to support iteration.
}
