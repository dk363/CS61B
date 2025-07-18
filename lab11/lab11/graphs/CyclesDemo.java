package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class CyclesDemo {
    /* Identifies a cycle (if any exist) in the given graph, and draws the cycle with
     * a purple line. */
    /* 识别给定图形中的循环（如果存在），并用紫色线条绘制该循环。 */
    
    public static void main(String[] args) {
        Maze maze = new Maze("lab11/graphs/maze.txt");

        MazeCycles mc = new MazeCycles(maze);
        mc.solve();
    }

}
