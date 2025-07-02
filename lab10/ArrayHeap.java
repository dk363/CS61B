import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * 这指的是一个泛型堆类。
 * 与 Java 的优先级队列不同，这个堆不仅存储可比较对象，它还可以存储任何类型的对象（由类型 T 表示），
 * 以及一个优先级值。之所以这样设计，是为了后续课程中的用途。 */
public class ArrayHeap<T> implements ExtrinsicPQ<T> {
    private Node[] contents;
    private int size;
    private static final int ROOT_INDEX = 1;

    public ArrayHeap() {
        contents = new ArrayHeap.Node[16];

        /* 在 ArrayHeap 的开头添加一个虚拟项，以便 getLeft、getRight 和 parent 方法更简洁。*/
        contents[0] = null;

        /* 尽管前端有一个空位，但我们仍然认为大小为 0，因为尚未插入任何内容。*/
        size = 0;
    }

    /**
     * 返回位于索引 i 处节点的左侧节点的索引。
     */
    private static int leftIndex(int i) {
        return 2 * i;
    }

    /**
     * 返回位于索引 i 处节点的右侧节点的索引。
     */
    private static int rightIndex(int i) {
        return 2 * i + 1;
    }

    /**
     * 返回位于索引 i 处节点的父节点的索引。
     */
    private static int parentIndex(int i) {
        return i / 2;
    }

    /**
     * 获取位于索引 i 处的节点；如果索引超出范围，则返回 null。
     */
    private Node getNode(int index) {
        if (!inBounds(index)) {
            return null;
        }
        return contents[index];
    }

    /**
     * 如果索引对应一个有效项，则返回 true。例如，如果
     * 我们有 5 个项，那么有效索引是 1, 2, 3, 4, 5。索引 0 是
     * 无效的，因为我们将第 0 个条目留空。
     */
    private boolean inBounds(int index) {
        if ((index > size) || (index < 1)) {
            return false;
        }
        return true;
    }

    /**
     * 交换两个索引处的节点。
     */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        contents[index1] = node2;
        contents[index2] = node1;
    }


    /**
     * 返回具有较小优先级的节点的索引。前置条件：两个节点不能都为空。
     */
    private int min(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        if (node1 == null) {
            return index2;
        } else if (node2 == null) {
            return index1;
        } else if (node1.myPriority < node2.myPriority) {
            return index1;
        } else {
            return index2;
        }
    }


    /**
     * 将当前位于给定索引处的节点上浮。
     */
    private void swim(int index) {
        validateSinkSwimArg(index); // 确保当前要上浮的节点是有效的

        // 只要不是根节点 (index > 1) 且当前节点的优先级小于其父节点的优先级，就继续上浮
        while (index > ROOT_INDEX && contents[index].myPriority < contents[parentIndex(index)].myPriority) {
            swap(index, parentIndex(index));
            index = parentIndex(index); // 更新当前节点的索引，继续上浮
        }
    }


    /**
     * 将当前位于给定索引处的节点下沉。
     */
    private void sink(int index) {
        validateSinkSwimArg(index);
        while (true) { // 使用无限循环，内部条件跳出
            int left = leftIndex(index);
            int right = rightIndex(index);
            int smallest = index; // 假设当前节点最小

            if (inBounds(left) && contents[left].myPriority < contents[smallest].myPriority) {
                smallest = left;
            }
            if (inBounds(right) && contents[right].myPriority < contents[smallest].myPriority) {
                smallest = right;
            }

            if (smallest == index) { // 如果当前节点最小，则停止下沉
                break;
            }

            swap(index, smallest);
            index = smallest; // 继续下沉到新的位置
        }
    }


    /**
     * 插入具有给定优先级值的项。这是入队或提供。
     * 要实现此方法，请将其添加到 ArrayList 的末尾，然后上浮它。
     */
    @Override
    public void insert(T item, double priority) {
        /* 如果数组已满，则调整大小。 */
        if (size + 1 == contents.length) {
            resize(contents.length * 2);
        }

        size += 1;
        contents[size] = new Node(item, priority);

        swim(size);
    }

    /**
     * 返回具有最小优先级值的节点，但不将其从堆中移除。要实现此功能，请返回 ArrayList 中第一个位置的项。
     */
    @Override
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        return contents[ROOT_INDEX].myItem;
    }

    /**
     * 返回具有最小优先级值的节点，并将其从堆中移除。这是出队或轮询。
     * 要实现此功能，请将堆中的最后一项交换到根位置，然后下沉根。
     * 这相当于解雇公司的总裁，将名单上最后一名员工提拔为总裁，然后反复降职。
     * 请确保通过将“死亡”项设为 null 来避免闲置。
     */
    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        Node min = contents[ROOT_INDEX];
        swap(ROOT_INDEX, size);
        contents[size] = null;
        size -= 1;

        sink(ROOT_INDEX);

        return min.myItem;
    }

    /**
     * 返回 PQ 中的项目数量。这比支持 ArrayList 的大小少一个，因为我们将第 0 个元素留空。
     * 此方法已为您实现。
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 将此堆中具有给定项的节点更改为具有给定优先级。您可以假设堆中不会有两个具有相同项的节点。
     * 使用 .equals() 而不是 == 检查项相等性。这是一个具有挑战性的奖励问题，
     * 但如果您真正理解堆并在开始编码之前考虑算法，它应该不会太难。
     */
    @Override
    public void changePriority(T item, double givenPriority) {
        for (int i = 1; i < contents.length; i++) {
            Node c = contents[i];
            if (c != null && c.myItem.equals(item)) {
                if (c.myPriority > givenPriority) {
                    c.myPriority = givenPriority;
                    swim(i);
                    break;
                } else if (c.myPriority < givenPriority) {
                    c.myPriority = givenPriority;
                    sink(i);
                    break;
                }
            }
        }
    }

    /**
     * 将堆侧向打印出来。已为您提供。
     */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* toString 的递归辅助方法。 */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = rightIndex(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = leftIndex(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }


    /**
     * 如果索引对于下沉或上浮无效，则抛出异常。
     */
    private void validateSinkSwimArg(int index) {
        if (index < 1) {
            throw new IllegalArgumentException("无法对索引为 0 或更小的节点进行下沉或上浮操作");
        }
        if (index > size) {
            throw new IllegalArgumentException("无法对索引大于当前大小的节点进行下沉或上浮操作。");
        }
        if (contents[index] == null) {
            throw new IllegalArgumentException("无法对空节点进行下沉或上浮操作。");
        }
    }

    private class Node {
        private T myItem;
        private double myPriority;

        private Node(T item, double priority) {
            myItem = item;
            myPriority = priority;
        }

        public T item(){
            return myItem;
        }

        public double priority() {
            return myPriority;
        }

        @Override
        public String toString() {
            return myItem.toString() + ", " + myPriority;
        }
    }


    /** 必要时用于调整底层数组大小的辅助函数。 */
    private void resize(int capacity) {
        Node[] temp = new ArrayHeap.Node[capacity];
        for (int i = 1; i < this.contents.length; i++) {
            temp[i] = this.contents[i];
        }
        this.contents = temp;
    }

    @Test
    public void testIndexing() {
        assertEquals(6, leftIndex(3));
        assertEquals(10, leftIndex(5));
        assertEquals(7, rightIndex(3));
        assertEquals(11, rightIndex(5));

        assertEquals(3, parentIndex(6));
        assertEquals(5, parentIndex(10));
        assertEquals(3, parentIndex(7));
        assertEquals(5, parentIndex(11));
    }

    @Test
    public void testSwim() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.size = 7;
        for (int i = 1; i <= 7; i += 1) {
            pq.contents[i] = new ArrayHeap<String>.Node("x" + i, i);
        }
        // 将项 x6 的优先级更改为较低的值。

        pq.contents[6].myPriority = 0;
        System.out.println("上浮前的 PQ：");
        System.out.println(pq);

        // 将 x6 上浮。它应该到达根部。

        pq.swim(6);
        System.out.println("上浮后的 PQ：");
        System.out.println(pq);
        assertEquals("x6", pq.contents[1].myItem);
        assertEquals("x2", pq.contents[2].myItem);
        assertEquals("x1", pq.contents[3].myItem);
        assertEquals("x4", pq.contents[4].myItem);
        assertEquals("x5", pq.contents[5].myItem);
        assertEquals("x3", pq.contents[6].myItem);
        assertEquals("x7", pq.contents[7].myItem);
    }

    @Test
    public void testSink() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.size = 7;
        for (int i = 1; i <= 7; i += 1) {
            pq.contents[i] = new ArrayHeap<String>.Node("x" + i, i);
        }
        // 将根的优先级更改为较大的值。
        pq.contents[1].myPriority = 10;
        System.out.println("下沉前的 PQ：");
        System.out.println(pq);

        // 下沉根。
        pq.sink(1);
        System.out.println("下沉后的 PQ：");
        System.out.println(pq);
        assertEquals("x2", pq.contents[1].myItem);
        assertEquals("x4", pq.contents[2].myItem);
        assertEquals("x3", pq.contents[3].myItem);
        assertEquals("x1", pq.contents[4].myItem);
        assertEquals("x5", pq.contents[5].myItem);
        assertEquals("x6", pq.contents[6].myItem);
        assertEquals("x7", pq.contents[7].myItem);
    }


    @Test
    public void testInsert() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.insert("c", 3);
        assertEquals("c", pq.contents[1].myItem);

        pq.insert("i", 9);
        assertEquals("i", pq.contents[2].myItem);

        pq.insert("g", 7);
        pq.insert("d", 4);
        assertEquals("d", pq.contents[2].myItem);

        pq.insert("a", 1);
        assertEquals("a", pq.contents[1].myItem);

        pq.insert("h", 8);
        pq.insert("e", 5);
        pq.insert("b", 2);
        pq.insert("c", 3);
        pq.insert("d", 4);
        System.out.println("插入 10 个项后的 pq：");
        System.out.println(pq);
        assertEquals(10, pq.size());
        assertEquals("a", pq.contents[1].myItem);
        assertEquals("b", pq.contents[2].myItem);
        assertEquals("e", pq.contents[3].myItem);
        assertEquals("c", pq.contents[4].myItem);
        assertEquals("d", pq.contents[5].myItem);
        assertEquals("h", pq.contents[6].myItem);
        assertEquals("g", pq.contents[7].myItem);
        assertEquals("i", pq.contents[8].myItem);
        assertEquals("c", pq.contents[9].myItem);
        assertEquals("d", pq.contents[10].myItem);
    }


    @Test
    public void testInsertAndRemoveOnce() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.insert("c", 3);
        pq.insert("i", 9);
        pq.insert("g", 7);
        pq.insert("d", 4);
        pq.insert("a", 1);
        pq.insert("h", 8);
        pq.insert("e", 5);
        pq.insert("b", 2);
        pq.insert("c", 3);
        pq.insert("d", 4);
        String removed = pq.removeMin();
        assertEquals("a", removed);
        assertEquals(9, pq.size());
        assertEquals("b", pq.contents[1].myItem);
        assertEquals("c", pq.contents[2].myItem);
        assertEquals("e", pq.contents[3].myItem);
        assertEquals("c", pq.contents[4].myItem);
        assertEquals("d", pq.contents[5].myItem);
        assertEquals("h", pq.contents[6].myItem);
        assertEquals("g", pq.contents[7].myItem);
        assertEquals("i", pq.contents[8].myItem);
        assertEquals("d", pq.contents[9].myItem);
    }

    @Test
    public void testInsertAndRemoveAllButLast() {
        ExtrinsicPQ<String> pq = new ArrayHeap<>();
        pq.insert("c", 3);
        pq.insert("i", 9);
        pq.insert("g", 7);
        pq.insert("d", 4);
        pq.insert("a", 1);
        pq.insert("h", 8);
        pq.insert("e", 5);
        pq.insert("b", 2);
        pq.insert("c", 3);
        pq.insert("d", 4);

        int i = 0;
        String[] expected = {"a", "b", "c", "c", "d", "d", "e", "g", "h", "i"};
        while (pq.size() > 1) {
            assertEquals(expected[i], pq.removeMin());
            i += 1;
        }
    }

}