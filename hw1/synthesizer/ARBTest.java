package synthesizer;

import static org.junit.Assert.*;
import org.junit.Test;

public class ARBTest {
    @Test
    public void testEnqueueDeququePeek() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);

        arb.enqueue(10);
        arb.enqueue(20);
        arb.enqueue(30);
        arb.enqueue(40);
        assertEquals(4, arb.fillCount());

        assertEquals((Integer) 10, arb.peek());
        assertEquals(4, arb.fillCount());

        arb.enqueue(50);

        assertTrue(arb.isFull());

        assertEquals((Integer) 10, arb.dequeue());
        assertEquals((Integer) 20, arb.dequeue());
        assertEquals((Integer) 30, arb.dequeue());
        assertEquals((Integer) 40, arb.dequeue());
        assertEquals((Integer) 50, arb.dequeue());

        assertTrue(arb.isEmpty());
    }
}
