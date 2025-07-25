import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * 移除并返回 q1 或 q2 中最小的项。
     * 该方法假定 q1 和 q2 都已按升序排序。q1 或 q2 中最多只有一个可以为空（但不能两个都为空）。
     * @param   q1  一个按从最小到最大排序的队列。
     * @param   q2  一个按从最小到最大排序的队列。
     * @return      q1 或 q2 中最小的项。
     */
    private static <Item extends Comparable>
    Item getMin(Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // 查看每个队列中的最小项（由于队列已排序，该项位于队首），以确定哪个更小。
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // 确保调用 dequeue，以便移除最小的项。
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** 返回一个由多个队列组成的队列，其中每个队列包含来自 items 的一个项。 */
    private static <Item extends Comparable>
    Queue<Queue<Item>> makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> q = new Queue<>();
        for (Item item : items) {
            Queue<Item> cur = new Queue<>();
            cur.enqueue(item);
            q.enqueue(cur);
        }
        return q;
    }

    /**
     * 返回一个新队列，其中包含按排序顺序排列的 q1 和 q2 中的项。
     * 此方法所需时间应与 q1 和 q2 中的总项数成线性关系。运行此方法后，q1 和 q2 将为空，它们的所有项都将在返回的队列中。
     * @param   q1  一个按从最小到最大排序的队列。
     * @param   q2  一个按从最小到最大排序的队列。
     * @return      一个包含 q1 和 q2 所有项的队列，按从最小到最大排序。
     */
    private static <Item extends Comparable>
    Queue<Item> mergeSortedQueues(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> sorted = new Queue<>();

        while (!q1.isEmpty() || !q2.isEmpty()) {
            sorted.enqueue(getMin(q1, q2));
        }

        return sorted;
    }

    /** 返回一个包含给定项的队列，按从最小到最大排序。 */
    public static <Item extends Comparable>
    Queue<Item> mergeSort(Queue<Item> items) {
        if (items == null || items.size() <= 1) {
            return items;
        }
        Queue<Queue<Item>> workQueue = makeSingleItemQueues(items);

        while (workQueue.size() > 1) {
            Queue<Item> q1 = workQueue.dequeue();
            Queue<Item> q2 = workQueue.dequeue();

            Queue<Item> sorted = mergeSortedQueues(q1, q2);
            workQueue.enqueue(sorted);
        }
        return workQueue.dequeue();
    }
}