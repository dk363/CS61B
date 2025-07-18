package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    /** 从起点开始对迷宫进行广度优先搜索。 */
    private void bfs(int v) {
        Queue<Integer> queue = new LinkedList<>();

        marked[v] = true;
        distTo[v] = 0;
        queue.offer(v);
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (int w : maze.adj(curr)) {
                if (!marked[w]) {
                    edgeTo[w] = curr;
                    marked[w] = true;
                    distTo[w] = distTo[curr] + 1;
                    announce();
                    if (w == t) {
                        targetFound = true;
                        announce();
                        return;
                    }
                    queue.offer(w);
                }
            }
        }
    }


    @Override
    public void solve() {
         bfs(s);
    }
}

