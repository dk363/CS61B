package hw4.puzzle;

public class Board implements WorldState {
    /**
    Board(tiles): Constructs a board from an N-by-N array of tiles where
    tiles[i][j] = tile at row i, column j
    tileAt(i, j): Returns value of tile at row i, column j (or 0 if blank)
    size():       Returns the board size N
    neighbors():  Returns the neighbors of the current board
    hamming():    Hamming estimate described below
    manhattan():  Manhattan estimate described below
    estimatedDistanceToGoal(): Estimated distance to goal. This method should
    simply return the results of manhattan() when submitted to
              Gradescope.
    equals(y):    Returns true if this board's tile values are the same
    position as y's
    toString():   Returns the string representation of the board. This
    method is provided in the skeleton
    Board(tiles): 从一个N×N的瓷砖数组构造一个棋盘，其中
                    tiles[i][j] = 第i行第j列的瓷砖
    tileAt(i, j): 返回第i行第j列的瓷砖值（若为空则返回0）
    size():       返回棋盘大小N
    neighbors():  返回当前棋盘的相邻状态
    hamming():    下方描述的汉明距离估计值
    manhattan():  下方描述的曼哈顿距离估计值
    estimatedDistanceToGoal(): 到目标状态的估计距离。此方法在提交至
    Gradescope时应直接返回manhattan()的结果。
    equals(y):    若此棋盘的瓷砖值与y的瓷砖位置相同则返回true
    toString():   返回棋盘的字符串表示。此方法已在框架中提供。
    */
    /**
     *
     */

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    /**
     * 提供到达目标所需移动次数的估计值，
     * 该值必须小于或等于实际距离。
     */
    @Override
    public int estimatedDistanceToGoal() {
        return 0;
    }

    /**
     * 提供此 WorldState 所有邻居的可迭代对象。
     */
    @Override
    public Iterable<WorldState> neighbors() {
        return null;
    }
}
