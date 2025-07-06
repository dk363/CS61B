package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         * 编写一个工具函数，
         * 若给定对象数组的哈希码能使其均匀分布在M个桶中则返回true。
         * 实现方式与可视化工具相同，即对每个对象的哈希码进行(& 0x7FFFFFFF) % M运算，
         * 并确保每个桶中的元素数量不少于N/50，且不超过N/2.5。
         */
        int[] buckets = new int[M];
        int N = 0;
        for (Oomage o : oomages) {
            int hashCode = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[hashCode] += 1;
            N += 1;
        }

        for (int b : buckets) {
            if (b > N / 2.5 || b < N / 50) {
                return false;
            }
        }
        return true;
    }
}
