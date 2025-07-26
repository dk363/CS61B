import java.util.Arrays;

/**
 * 用于执行基数排序的类
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    private final static int R = 256;
    /**
     * 对传入的数组执行 LSD（最低位优先）基数排序，具备以下限制条件：
     * - 数组中只能包含 ASCII 字符串（即每个字符为 1 个字节）
     * - 排序是稳定的，且不会修改原始数组（非破坏性排序）
     * - 字符串长度可变（不是所有字符串长度都一样）
     *
     * @param asciis 需要排序的字符串数组
     * @return String[] 排序后的字符串数组
     */
    public static String[] sort(String[] asciis) {
        int max = Integer.MIN_VALUE;
        for (String s : asciis) {
            max = Math.max(s.length(), max);
        }

        String[] copy = Arrays.copyOf(asciis, asciis.length);

        for (int i = max - 1; i >= 0; i--) {
            sortHelperLSD(copy, i);
        }

        return copy;
    }

    /**
     * LSD 辅助方法，对字符串数组中的字符在指定索引位置上执行破坏性的计数排序。
     *
     * @param asciis 输入的字符串数组
     * @param index  要排序的字符所在的位置（索引）
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // 将当前的字母放入 buckets 中
        int[] buckets = new int[R];
        for (int i = 0; i < asciis.length; i++) {
            // 对于过短的用 0 补齐
            int c = (index < asciis[i].length()) ? asciis[i].charAt(index) : 0;
            buckets[c]++;
        }

        // 构建前缀和
        int[] starts = new int[R];
        int pos = 0;
        for (int i = 0; i < R; i++) {
            starts[i] = pos;
            pos += buckets[i];
        }

        // 将 string 重新排序
        String[] sorted = new String[asciis.length];
        for (String s : asciis) {
            int c = (index < s.length()) ? s.charAt(index) : 0;
            int place = starts[c];
            sorted[place] = s;
            starts[c] += 1;
        }

        System.arraycopy(sorted, 0, asciis, 0, asciis.length);
    }

    /**
     * MSD（最高位优先）基数排序的辅助函数，递归调用自身以完成排序。
     * 这是一个破坏性方法，会修改传入的 asciis 数组。
     *
     * @param asciis 要排序的字符串数组
     * @param start  排序开始位置的索引（包含该位置的字符串）
     * @param end    排序结束位置的索引（不包含该位置的字符串）
     * @param index  当前正在排序的字符索引位置
     */
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // MSD 排序的可选辅助方法，用于实现可选的 MSD 基数排序
        return;
    }
}
