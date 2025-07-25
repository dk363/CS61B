import edu.princeton.cs.algs4.Queue;

public class MergeSortTest {
    public static void main(String[] args) {
        Queue<Integer> input = new Queue<>();
        input.enqueue(5);
        input.enqueue(2);
        input.enqueue(4);
        input.enqueue(1);
        input.enqueue(3);


        for (int item : input) {
            System.out.print(item + " ");
        }

        System.out.println();

        Queue<Integer> sorted = MergeSort.mergeSort(input);

        for (int item : sorted) {
            System.out.print(item + " ");
        }
        // 输出应该为：1 2 3 4 5
    }
}
