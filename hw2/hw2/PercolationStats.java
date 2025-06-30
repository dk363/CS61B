package hw2;

import java.util.Arrays;
import java.util.Random;

public class PercolationStats {
    private Random rm;
    private Percolation ps;
    private double[] thresholds;
    private double stddev;

    // 在 N×N 的网格上执行 T 次独立的渗流实验
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("N and T must > 0");

        // initial

        thresholds = new double[T];
        rm = new Random();

        for (int i = 0; i < T; i++) {
            ps = pf.make(N);
            while (!ps.percolates()) {
                int row, col;
                do {
                    row = rm.nextInt(N);
                    col = rm.nextInt(N);
                } while (ps.isOpen(row, col));
                ps.open(row, col);
            }
            thresholds[i] = (double) ps.numberOfOpenSites() / (N * N);
        }
    }

    // 返回渗流阈值的样本平均值（mean）
    public double mean() {
        return Arrays.stream(thresholds).average().orElse(0.0);
    }

    // 返回渗流阈值的样本标准差（standard deviation）
    public double stddev() {
        // 忽略调用顺序，可能此时mean没有被调用过
        double m = mean();
        double stdDevSum = 0;
        for (double t : thresholds) {
            stdDevSum += (t - m) * (t - m);
        }
        return Math.sqrt(stdDevSum / (thresholds.length - 1));
    }

    // 返回 95% 置信区间的下限
    public double confidenceLow() {
        return mean() - 1.96 * stddev / Math.sqrt(thresholds.length);
    }

    // 返回 95% 置信区间的上限
    public double confidenceHigh() {
        return mean() + 1.96 * stddev / Math.sqrt(thresholds.length);
    }
}
