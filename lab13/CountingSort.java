/**
 * 使用两种方法进行计数排序的类，一种是朴素方法，另一种是“更好”的方法
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * 对给定的 int 数组进行计数排序。返回排序后的数组。
     * 不修改原数组（非破坏性方法）。
     * 声明：该方法并不总是有效，请找出其失败的情况
     *
     * @param arr 将被排序的 int 数组
     * @return 排序后的数组
     */
    public static int[] naiveCountingSort(int[] arr) {
        // 找到最大值
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
        }

        // 统计每个值出现的次数
        // 这里会造成错误，如果该元素是负数，这里会如何做呢
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // 对于 int 类型，我们可以直接将每个值根据其出现次数
        // 放入新数组中
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // 下面是一个更正规、通用的计数排序实现，
        // 使用了起始位置的计算方式
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // 返回排序后的数组
        return sorted;
    }

    /**
     * 对给定的 int 数组进行计数排序，必须能处理包含负数的数组。
     * 注意：该代码不需要处理数值范围超过 20 亿的情况。
     * 不修改原数组（非破坏性方法）。
     *
     * @param arr 将被排序的 int 数组
     */
    public static int[] betterCountingSort(int[] arr) {
        // 将整个数组右移
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i : arr) {
            max = Math.max(i, max);
            min = Math.min(i, min);
        }

        // 统计出现次数
        int[] counts = new int[max - min + 1];
        for (int i : arr) {
            counts[i - min]++;
        }

        // 构建前缀和数组 starts 代表开始的下标
        int[] starts = new int[max - min + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i++) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted = new int[arr.length];
        for (int i : arr) {
            int index = i - min;
            int place = starts[index];
            sorted[place] = i;
            starts[index]++;
        }

        return sorted;
    }
}
