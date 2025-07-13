package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    /*
        Solver(initial): Constructor which solves the puzzle, computing
        everything necessary for moves() and solution() to
        not have to solve the problem again. Solves the
        puzzle using the A* algorithm. Assumes a solution exists.
        Solver(initial): 构造函数，用于解谜题，预先计算所有必要信息，
        使得moves()和solution()无需重复求解。使用A*算法进行解谜，
        默认存在解。
    */

    private List<WorldState> sequenceOfSolution; // 从初始WorldState到解的WorldState序列。
    private MinPQ<SearchNode> pq; // 按照距离进行排序

    private static class SearchNode {
        WorldState worldState;
        int moves;
        SearchNode prev;
        int disToGoal;

        SearchNode(WorldState w, int moves, SearchNode prev, int disToGoal) {
            this.worldState = w;
            this.moves = moves;
            this.prev = prev;
            this.disToGoal = disToGoal;
        }
    }

    private SearchNode goalNode;

    public Solver(WorldState initial) {
        SearchNode ini = new SearchNode(initial, 0, null, initial.estimatedDistanceToGoal());

        if (initial.isGoal()) {
            goalNode = ini;
            return;
        }

        pq = new MinPQ<>((a, b) -> (a.moves + a.disToGoal) - (b.moves + b.disToGoal));
        pq.insert(ini);

        for (WorldState n : initial.neighbors()) {
            int dis = n.estimatedDistanceToGoal();
            SearchNode newNode = new SearchNode(n, ini.moves + 1, ini, dis);
            pq.insert(newNode);
        }

        SearchNode minPriority = pq.delMin();

        while (!minPriority.worldState.isGoal()) {
            for (WorldState n : minPriority.worldState.neighbors()) {
                // 检查是否有重复
                if (minPriority.prev != null && n.equals(minPriority.prev.worldState)) {
                    continue;
                }

                int dis = n.estimatedDistanceToGoal();
                SearchNode newNode = new SearchNode(n, minPriority.moves + 1, minPriority, dis);
                pq.insert(newNode);
            }
            minPriority = pq.delMin();
        }

        goalNode = minPriority;
    }

    /** moves(): Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState. */
    /**
        moves(): 返回从初始WorldState开始解谜所需的最少步数。
    */
    public int moves() {
        return goalNode.moves;
    }

    /** solution(): Returns a sequence of WorldStates from the initial WorldState
        to the solution. */
    /** solution():      返回从初始WorldState到解的WorldState序列。*/
    public Iterable<WorldState> solution() {
        // 从目标状态开始回溯 parent 节点，直到起点
        // 如果直接一边遍历一边加入的话，我们会加入一大堆无关紧要的节点
        List<WorldState> path = new LinkedList<>();
        SearchNode current = goalNode;
        while (current != null) {
            path.addFirst(current.worldState); // 插到前面
            current = current.prev;
        }
        sequenceOfSolution = path;
        return sequenceOfSolution;
    }
}
