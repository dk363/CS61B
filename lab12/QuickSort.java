import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * 返回一个新的队列，包含将给定的两个队列连接在一起的结果。
     * q2 中的项会被连接在 q1 的所有项之后。
     */
    private static <Item extends Comparable>
    Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** 从给定队列中返回一个随机项。 */
    private static <Item extends Comparable>
    Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;

        // 遍历队列，找到给定索引处的项。
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }

        return pivot;
    }

    /**
     * 通过对给定项进行划分，将未排序的队列划分为多个部分。
     *
     * @param unsorted  一个未排序项的队列
     * @param pivot     用于划分的基准项
     * @param less      一个空队列。函数执行完后，其中将包含所有小于 pivot 的项。
     * @param equal     一个空队列。函数执行完后，其中将包含所有等于 pivot 的项。
     * @param greater   一个空队列。函数执行完后，其中将包含所有大于 pivot 的项。
     */
    private static <Item extends Comparable>
    void partition(Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {

        if (unsorted == null || unsorted.size() <= 1) {
            return;
        }

        for (Item item : unsorted) {
            int com = item.compareTo(pivot);

            if (com == 0) {
                equal.enqueue(item);
            } else if (com > 0) {
                greater.enqueue(item);
            } else { // com < 0
                less.enqueue(item);
            }
        }
    }

    /** 返回一个包含给定项，按从小到大排序的新队列。 */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        if (items == null || items.size() <= 1) {
            return items;
        }

        Item pivot = getRandomItem(items);
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();

        partition(items, pivot, less, equal, greater);

        Queue<Item> lessSorted = quickSort(less);
        Queue<Item> greaterSorted = quickSort(greater);

        items = catenate(catenate(lessSorted, equal), greaterSorted);

        return items;
    }

    public static void main(String args[]) {
        Queue<Integer> input = new Queue<>();

        input.enqueue(5);
        input.enqueue(3);
        input.enqueue(7);
        input.enqueue(2);
        input.enqueue(4);

        for (Integer i : input) {
            System.out.print(i);
        }

        System.out.println();

        Queue<Integer> sorted = quickSort(input);

        for (Integer i : sorted) {
            System.out.print(i);
        }
    }
}
