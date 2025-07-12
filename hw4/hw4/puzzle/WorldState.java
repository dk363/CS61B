package hw4.puzzle;

public interface WorldState {
    /** Provides an estimate of the number of moves to reach the goal.
     * Must be less than or equal to the correct distance. */
    /** 提供到达目标所需移动次数的估计值，
     * 该值必须小于或等于实际距离。 */
    int estimatedDistanceToGoal();

    /** Provides an iterable of all the neighbors of this WorldState. */
    /** 提供此 WorldState 所有邻居的可迭代对象。 */
    Iterable<WorldState> neighbors();

    default boolean isGoal() {
        return estimatedDistanceToGoal() == 0;
    }

}
