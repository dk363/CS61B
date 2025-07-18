package lab11.graphs;

import java.util.Observable;

/**
 * @author Josh Hug
 */
public abstract class MazeExplorer extends Observable {
    protected int[] distTo;
    protected int[] edgeTo;
    protected boolean[] marked;
    protected Maze maze;


    /**
     * Notify all Observers of a change.
     * 通知所有观察者发生变更。
     */
    protected void announce() {
        setChanged();
        notifyObservers();
    }

    public MazeExplorer(Maze m) {
        maze = m;

        distTo = new int[maze.V()];
        edgeTo = new int[maze.V()];
        marked = new boolean[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            distTo[i] = Integer.MAX_VALUE;
            edgeTo[i] = Integer.MAX_VALUE;
        }
        addObserver(maze);
    }

    /**
     * Solves the maze, modifying distTo and edgeTo as it goes.
     * 解决迷宫问题，过程中更新 distTo 和 edgeTo 。
     */
    public abstract void solve();

    /* Getters for AG. */
    public int[] getDistTo() {
        return distTo;
    }

    public int[] getEdgeTo() {
        return edgeTo;
    }
}
