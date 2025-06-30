package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Percolation {
    private final boolean[][] grid;
    private final int n;
    private int openSites;

    private final int virtueTop;
    private final int virtueBottom;

    private final WeightedQuickUnionUF ufPercolates;
    private final WeightedQuickUnionUF ufFullCheck;

    // 创建一个 N×N 的网格，初始时所有位置都是阻塞的
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid size must > 0");
        }

        this.n = n;
        this.grid = new boolean[n][n];
        this.openSites = 0;

        this.virtueTop = n * n;
        this.virtueBottom = n * n + 1;

        this.ufPercolates = new WeightedQuickUnionUF(n * n + 2);
        this.ufFullCheck = new WeightedQuickUnionUF(n * n + 1);
    }

    // 如果位置 (row, col) 尚未打开，则将其打开
    // 并且将他与周围的 open site 连接
    public void open(int row, int col) {
        validDate(row, col);

        if (grid[row][col]) {
            return;
        }

        grid[row][col] = true;
        openSites += 1;

        int index = xyTo1D(row, col);

        if (row == 0) {
            ufPercolates.union(index, virtueTop);
            ufFullCheck.union(index, virtueTop);
        }

        if (row == n - 1) {
            ufPercolates.union(index, virtueBottom);
        }

        connectIfOpen(row + 1, col, index);
        connectIfOpen(row - 1, col, index);
        connectIfOpen(row, col + 1, index);
        connectIfOpen(row, col - 1, index);
    }

    // 检查邻居是否可以相连
    private void connectIfOpen(int row, int col, int currentIndex) {
        if (row >= 0 && row < n && col >= 0 && col < n && isOpen(row, col)) {
            int neighborIndex = xyTo1D(row, col);
            ufPercolates.union(currentIndex, neighborIndex);
            ufFullCheck.union(currentIndex, neighborIndex);
        }
    }

    // 将二维坐标转换为 一维坐标
    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    // 判断位置 (row, col) 是否是打开的
    public boolean isOpen(int row, int col) {
        validDate(row, col);
        return grid[row][col];
    }

    // 判断位置 (row, col) 是否是“满”的（即与顶部连通）
    // 但是这里会有回流问题， virtueTop 和 virtueBottom 会通过底部的格子相连，
    // 因为我们的 virtueTop 和 virtueBottom 是相邻的
    public boolean isFull(int row, int col) {
        validDate(row, col);
        return isOpen(row, col) && ufFullCheck.connected(xyTo1D(row, col), virtueTop);
    }

    // 返回当前打开的位置数量
    public int numberOfOpenSites() {
        return openSites;
    }

    // 判断系统是否“渗流”了（即是否存在从顶到底的连通路径）
    public boolean percolates() {
        return ufPercolates.connected(virtueTop, virtueBottom);
    }

    private void validDate(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            System.out.println("Invalid input: row = " + row + ", col = " + col + ", n = " + n);
            throw new IndexOutOfBoundsException("row and col must be between 0 and " + (n - 1));
        }
    }


    // 用于单元测试（不是必须的）
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0); // 打通了
        p.open(2, 2); // 底部另一个 open，但不是 full

        assertTrue(p.percolates());
        assertFalse(p.isFull(2, 2)); // ✅ 如果是 true 就是回流
    }


}
