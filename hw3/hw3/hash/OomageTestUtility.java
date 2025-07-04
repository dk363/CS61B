package hw3.hash;

import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.html.HTML.Attribute.N;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
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
