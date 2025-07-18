package lab11.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private boolean cycleFound;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        Arrays.fill(edgeTo, -1);
        int startX = 1, startY = 1;
        s = maze.xyTo1D(startX, startY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private void dfs(int v) {
        if (cycleFound) {
            return;
        }

        marked[v] = true;

        for (int w : maze.adj(v)) {
            if (cycleFound) {
                return;
            }
            if (!marked[w]) {
                edgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                dfs(w);
            } else if (marked[w] && edgeTo[v] != w) {
                // w 不是 v 的父节点 且 w 已经被访问过了
                cycleFound = true;
                List<Integer> cycle = new ArrayList<>();

                int path = v;
                cycle.add(v);
                while (path != w) {
                    path = edgeTo[path];
                    cycle.add(path);
                }
                cycle.add(v);

                Arrays.fill(edgeTo, -1);

                for (int i = 1; i < cycle.size(); i++) {
                    edgeTo[cycle.get(i)] = cycle.get(i - 1);
                    announce();
                }
                return;
            }
        }
    }

    @Override
    public void solve() {
        dfs(s);
        announce();
    }


    // Helper methods go here
}

