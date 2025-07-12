package hw4.puzzle;
import edu.princeton.cs.algs4.StdOut;

public class WordPuzzleSolver {
    /***********************************************************************
     * Test routine for your Solver class. Uncomment and run to test
     * your basic functionality.
     *  用于测试Solver类的常规程序。取消注释并运行以测试
     *  您的基本功能。
     **********************************************************************/
    public static void main(String[] args) {
        String start = "cube";
        String goal = "tubes";

        Word startState = new Word(start, goal);
        Solver solver = new Solver(startState);

        StdOut.println("Minimum number of moves = " + solver.moves());
        for (WorldState ws : solver.solution()) {
            StdOut.println(ws);
        }
    }
}
