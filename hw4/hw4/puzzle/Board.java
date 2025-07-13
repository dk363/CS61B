package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
import static java.lang.Math.abs;

public class Board implements WorldState {
    private final int[][] tiles;
    private static final int BLANK = 0;

    // Board(tiles): Constructs a board from an N-by-N array of tiles where
    //    tiles[i][j] = tile at row i, column j
    /**
     *  Board(tiles): 从一个N×N的瓷砖数组构造一个棋盘，其中
     *  tiles[i][j] = 第i行第j列的瓷砖
     */
    public Board(int[][] tiles) {
        int[][] cowmoo = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, cowmoo[i], 0, tiles[0].length);
        }
        this.tiles = cowmoo;
    }

    // tileAt(i, j): Returns value of tile at row i, column j (or 0 if blank)
    /** tileAt(i, j): 返回第i行第j列的瓷砖值（若为空则返回0）
     */
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    /** size(): 返回棋盘大小N
     *  size(): Returns the board size N
     */
    public int size() {
        return tiles.length;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     * neighbors(): 返回当前棋盘的相邻状态
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (abs(-bug + l11il) + abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    // hamming(): Hamming estimate described below
    /** hamming(): 下方描述的汉明距离估计值 */
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int value = tiles[i][j];
                if (value == 0) {
                    continue;
                }
                int targetPos = targetPos(i, j);
                if (value != targetPos) {
                    cnt += 1;
                }
            }
        }
        return cnt;
    }

    private int targetPos(int i, int j) {
        return i * tiles.length + j + 1;
    }


    // manhattan():  Manhattan estimate described below
    /** manhattan():  下方描述的曼哈顿距离估计值 */
    public int manhattan() {
        int cnt = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int value = tiles[i][j];
                if (value == 0) {
                    continue;
                }

                int goalRow = (value - 1) / tiles.length;
                int goalCol = (value - 1) % tiles.length;

                cnt += Math.abs(goalRow - i) + Math.abs(goalCol - j);
            }
        }
        return cnt;
    }


    /**
     equals(y):    Returns true if this board's tile values are the same position as y's
     equals(y):    若此棋盘的瓷砖值与y的瓷砖位置相同则返回true
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null || !(y instanceof Board)) {
            return false;
        }

        Board that = (Board) y;

        if (this.tiles.length != that.tiles.length) {
            return false;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    /*
    estimatedDistanceToGoal(): Estimated distance to goal. This method should
    simply return the results of manhattan() when submitted to
    Gradescope.
    estimatedDistanceToGoal(): 到目标状态的估计距离。此方法在提交至Gradescope时应直接返回manhattan()的结果。
    */
    /**
     * 提供到达目标所需移动次数的估计值，
     * 该值必须小于或等于实际距离。
     */
    @Override
    public int estimatedDistanceToGoal() {
        return this.manhattan();
    }

    @Override
    public int hashCode() {
        int result = tiles != null ? Arrays.deepHashCode(tiles) : 0;
        assert tiles != null;
        result = 31 * result + Arrays.hashCode(tiles[0]);
        return result;
    }
}
