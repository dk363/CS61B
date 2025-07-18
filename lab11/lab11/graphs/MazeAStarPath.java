package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private final int targetY;
    private final int targetX;
    private MinPQ<Integer> pq;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        this.targetX = targetX;
        this.targetY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** 从顶点 v 到目标点的距离估计值（启发函数） */
    private int h(int v) {
        int sourceX = v % (maze.N() + 1);
        int sourceY = v / (maze.N() + 1);
        // 曼哈顿距离
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** 找到估计距离目标点最近的未标记顶点 */
    private int findMinimumUnmarked() {
        return -1;
        /* 你不一定要使用这个方法 */
    }

    /** 从顶点 s 开始执行 A* 搜索 */
    private void astar(int s) {
        pq = new MinPQ<>((a, b) -> (distTo[a] + h(a) - (distTo[b] + h(b))));
        pq.insert(s);

        // 太漂亮了
        // 回去继续好好揣摩一下
        // 条件的判断很整齐
        // 标准统一
        // 我之前写的就是依托啊
        while (!pq.isEmpty()) {
            int curr = pq.delMin();
            if (marked[curr]) {
                continue;
            }
            marked[curr] = true;
            announce();

            // 不需要提早做特判，代码会有冗余
            // 这里牺牲一点空间，创建一些数据结构，但是代码更加好看
            if (curr == t) {
                targetFound = true;
                return;
            }

            for (int w : maze.adj(curr)) {
                // 让路径更短
                if (!marked[w] && distTo[w] > distTo[curr] + 1) {
                    // 更新路径
                    distTo[w] = distTo[curr] + 1;
                    edgeTo[w] = curr;
                    pq.insert(w);
                    announce();
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
        announce();
    }

}
