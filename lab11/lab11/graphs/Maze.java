package lab11.graphs;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

import java.util.Random;
import java.awt.Color;

import java.util.Observer;
import java.util.Observable;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Maze implements Observer {

    public enum MazeType {
        SINGLE_GAP, POPEN_SOLVABLE, BLANK
    }

    /**
     * 更新迷宫的绘制。
     */
    public void update(Observable o, Object arg) {
        MazeExplorer me = (MazeExplorer) o;
        StdDraw.clear();
        draw();
        for (int i = 0; i < N * N; i += 1) {
            if (me.marked[i]) {
                drawEdges(i, me);
            }
        }
        for (int i = 0; i < N * N; i += 1) {
            if (me.marked[i]) {
                draw(i, me);
            }
        }

        StdDraw.show(DRAW_DELAY_MS);
    }

    /**
     * 返回顶点 v 的邻接顶点。
     */
    public Iterable<Integer> adj(int v) {
        int x = toX(v);
        int y = toY(v);
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        if (!wallExists(x, y, "North")) {
            neighbors.add(xyTo1D(x, y + 1));
        }

        if (!wallExists(x, y, "East")) {
            neighbors.add(xyTo1D(x + 1, y));
        }

        if (!wallExists(x, y, "South")) {
            neighbors.add(xyTo1D(x, y - 1));
        }

        if (!wallExists(x, y, "West")) {
            neighbors.add(xyTo1D(x - 1, y));
        }

        return neighbors;
    }

    /**
     * 返回顶点 v 的 x 坐标。
     * 例如，如果 N = 10，且 V = 12，则返回 2。
     */
    public int toX(int v) {
        return v % N + 1;
    }

    /**
     * 返回顶点 v 的 y 坐标。
     * 例如，如果 N = 10，且 V = 12，则返回 1。
     */
    public int toY(int v) {
        return v / N + 1;
    }

    /**
     * 返回 (x, y) 位置顶点的一维索引。
     */
    public int xyTo1D(int x, int y) {
        return (y - 1) * N + (x - 1);
    }

    // 如果存在墙则返回 true
    private boolean wallExists(int x, int y, String s) {
        int tx = targetX(x, s);
        int ty = targetY(y, s);
        boolean ooBounds = (tx == 0 || ty == 0 || tx == N + 1 || ty == N + 1);

        if (ooBounds) {
            return true;
        }

        if (s.equals("North")) {
            return north[x][y];
        }

        if (s.equals("East")) {
            return east[x][y];
        }

        if (s.equals("South")) {
            return south[x][y];
        }

        if (s.equals("West")) {
            return west[x][y];
        }

        return true;
    }

    /**
     * 返回迷宫中空间的数量。
     */
    public int V() {
        return N * N;
    }

    /**
     * 返回迷宫的大小。
     */
    public int N() {
        return N;
    }

    /**
     * 根据给定的配置文件创建迷宫。
     */
    public Maze(String configFilename) {
        In in = new In(configFilename);
        int rseed = 0;
        N = 10;
        DRAW_DELAY_MS = 50;
        MazeType mt = MazeType.SINGLE_GAP;
        double pOpen = 0.48;
        String configPatternString = "(\\w+)\\s*=\\s*([a-zA-Z0-9_.]+)";
        Pattern configPattern = Pattern.compile(configPatternString);

        while (!in.isEmpty()) {
            String thisLine = in.readLine();
            if (thisLine.length() == 0 || thisLine.charAt(0) == '%') {
                continue;
            }

            Matcher m = configPattern.matcher(thisLine);
            if (m.find()) {
                String variable = m.group(1);
                String value = m.group(2);
                switch (variable) {
                    case "N":
                        N = Integer.parseInt(value);
                        break;
                    case "rseed":
                        rseed = Integer.parseInt(value);
                        break;
                    case "pOpen":
                        pOpen = Double.parseDouble(value);
                        break;
                    case "DRAW_DELAY_MS":
                        DRAW_DELAY_MS = Integer.parseInt(value);
                        break;
                    case "MazeType":
                        if (value.equals("SINGLE_GAP")) {
                            mt = MazeType.SINGLE_GAP;
                        }

                        if (value.equals("POPEN_SOLVABLE")) {
                            mt = MazeType.POPEN_SOLVABLE;
                        }

                        if (value.equals("BLANK")) {
                            mt = MazeType.BLANK;
                        }

                        break;
                    default:
                        break;
                }
            }
        }
        init(rseed, pOpen, mt);
    }

    /**
     * 使用给定的 N、随机种子、参数 p（并非所有迷宫类型都使用）和迷宫类型创建迷宫。
     */
    public Maze(int N, int rseed, double pOpen, MazeType mt) {
        this.N = N;
        init(rseed, pOpen, mt);
    }

    /**
     * 根据构造函数设定的参数初始化迷宫。
     */
    private void init(int rseed, double p, MazeType mt) {
        StdDraw.setXscale(0, N + 2);
        StdDraw.setYscale(0, N + 2);
        rgen = new Random(rseed);
        if (mt == MazeType.SINGLE_GAP) {
            generateSingleGapMaze();
        }
        if (mt == MazeType.POPEN_SOLVABLE) {
            generatePopenSolvableMaze(p);
        }
        if (mt == MazeType.BLANK) {
            generateBlankMaze();
        }
    }

    private void generateBlankMaze() {
        /** 创建全为 false 的数组。 */
        north = new boolean[N + 2][N + 2];
        east = new boolean[N + 2][N + 2];
        south = new boolean[N + 2][N + 2];
        west = new boolean[N + 2][N + 2];
    }

    // 生成迷宫
    private void generateSingleGapMaze(int x, int y, boolean[][] marked) {

        marked[x][y] = true;
        // 当存在未标记的邻居时
        while (!marked[x][y + 1] || !marked[x + 1][y] || !marked[x][y - 1] || !marked[x - 1][y]) {

            // 随机选择一个邻居（也可以用 Knuth 技巧）
            while (true) {

                double r = rgen.nextDouble();

                if (r < 0.25 && !marked[x][y + 1]) {
                    north[x][y] = south[x][y + 1] = false;
                    generateSingleGapMaze(x, y + 1, marked);
                    break;
                } else if (r >= 0.25 && r < 0.50 && !marked[x + 1][y]) {
                    east[x][y] = west[x + 1][y] = false;
                    generateSingleGapMaze(x + 1, y, marked);
                    break;
                } else if (r >= 0.5 && r < 0.75 && !marked[x][y - 1]) {
                    south[x][y] = north[x][y - 1] = false;
                    generateSingleGapMaze(x, y - 1, marked);
                    break;
                } else if (r >= 0.75 && r < 1.00 && !marked[x - 1][y]) {
                    west[x][y] = east[x - 1][y] = false;
                    generateSingleGapMaze(x - 1, y, marked);
                    break;
                }
            }
        }
    }

    // 从左下角开始生成迷宫
    private void generateSingleGapMaze() {
        boolean[][] marked = new boolean[N + 2][N + 2];
        for (int x = 0; x < N + 2; x++) {
            marked[x][0] = marked[x][N + 1] = true;
        }
        for (int y = 0; y < N + 2; y++) {
            marked[0][y] = marked[N + 1][y] = true;
        }

        // 初始化所有墙为存在
        north = new boolean[N + 2][N + 2];
        east = new boolean[N + 2][N + 2];
        south = new boolean[N + 2][N + 2];
        west = new boolean[N + 2][N + 2];
        for (int x = 0; x < N + 2; x++) {
            for (int y = 0; y < N + 2; y++) {
                north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
            }
        }

        generateSingleGapMaze(1, 1, marked);
    }

    private void generatePopenSolvableMaze(double pOpen) {
        // 初始化所有墙为存在
        north = new boolean[N + 2][N + 2];
        east = new boolean[N + 2][N + 2];
        south = new boolean[N + 2][N + 2];
        west = new boolean[N + 2][N + 2];
        for (int x = 0; x < N + 2; x++) {
            for (int y = 0; y < N + 2; y++) {
                north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
            }
        }

        for (int x = 1; x < N + 1; x += 1) {
            for (int y = 1; y < N + 1; y += 1) {
                double r = rgen.nextDouble();
                if (r < pOpen) {
                    if (inBounds(x, y + 1)) {
                        north[x][y] = south[x][y + 1] = false;
                    }
                }

                r = rgen.nextDouble();
                if (r < pOpen) {
                    if (inBounds(x + 1, y)) {
                        east[x][y] = west[x + 1][y] = false;
                    }
                }

                r = rgen.nextDouble();
                if (r < pOpen) {
                    if (inBounds(x, y - 1)) {
                        south[x][y] = north[x][y - 1] = false;
                    }
                }

                r = rgen.nextDouble();
                if (r < pOpen) {
                    if (inBounds(x - 1, y)) {
                        west[x][y] = east[x - 1][y] = false;
                    }
                }
            }
        }
    }

    /**
     * 如果 (x, y) 在迷宫边界内则返回 true。
     */
    private boolean inBounds(int x, int y) {
        return (!(x == 0 || x == N + 1 || y == 0 || y == N + 1));
    }

    // 给定源位置和方向，返回目标的 x 坐标
    private int targetX(int x, String s) {
        if (s.equals("West")) {
            return x - 1;
        }
        if (s.equals("East")) {
            return x + 1;
        }
        return x;
    }

    // 给定源位置和方向，返回目标的 y 坐标
    private int targetY(int y, String s) {
        if (s.equals("South")) {
            return y - 1;
        }
        if (s.equals("North")) {
            return y + 1;
        }
        return y;
    }

    /**
     * 在第 i 个单元格中绘制一个指定颜色 c 的实心圆。
     */
    private void draw(int i, Color c) {
        StdDraw.setPenColor(c);
        int x = toX(i);
        int y = toY(i);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
    }

    /**
     * 在 (x, y) 单元格中绘制一个指定颜色 c 的实心圆。
     */
    private void draw(int x, int y, Color c) {
        StdDraw.setPenColor(c);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
    }

    /* 绘制单元格 i 的状态，包括任何返回边。 */
    private void draw(int i, MazeExplorer me) {
        int x = toX(i);
        int y = toY(i);
        if (me.marked[i]) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        }
        if (me.distTo[i] < Integer.MAX_VALUE) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(x + 0.5, y + 0.5, Integer.toString(me.distTo[i]));
        }
    }

    private void drawEdges(int i, MazeExplorer me) {
        int x = toX(i);
        int y = toY(i);
        if (me.edgeTo[i] < Integer.MAX_VALUE) {
            StdDraw.setPenColor(StdDraw.MAGENTA);
            int fromX = toX(me.edgeTo[i]);
            int fromY = toY(me.edgeTo[i]);
            StdDraw.line(fromX + 0.5, fromY + 0.5, x + 0.5, y + 0.5);
        }
    }

    // 绘制迷宫
    private void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                if (south[x][y]) {
                    StdDraw.line(x, y, x + 1, y);
                }
                if (north[x][y]) {
                    StdDraw.line(x, y + 1, x + 1, y + 1);
                }
                if (west[x][y]) {
                    StdDraw.line(x, y, x, y + 1);
                }
                if (east[x][y]) {
                    StdDraw.line(x + 1, y, x + 1, y + 1);
                }
            }
        }
    }

    /* 绘制迷宫，并将所有点用一维索引编号。 */
    private void drawDotsByIndex() {
        for (int i = 0; i < V(); i += 1) {
            int x = toX(i);
            int y = toY(i);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(x + 0.5, y + 0.5, Integer.toString(i));
        }
        StdDraw.show();
    }

    /* 绘制迷宫，并将所有点用 (x, y) 坐标编号。 */
    private void drawDotsByXY() {
        for (int i = 0; i < V(); i += 1) {
            int x = toX(i);
            int y = toY(i);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(x + 0.5, y + 0.5, Integer.toString(x) + ", " + Integer.toString(y));
        }
        StdDraw.show();
    }

    // 一个测试客户端
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int rseed = Integer.parseInt(args[1]);

        Maze maze = new Maze(N, rseed, 0.48, MazeType.POPEN_SOLVABLE);
        StdDraw.show(0);
        maze.draw();
//        MazeExplorer mdfp = new MazeAStarPath(maze, 4, 4, N, N);
        MazeExplorer mdfp = new MazeCycles(maze);
        mdfp.solve();
    }


    private int N;                 // 迷宫的尺寸
    private boolean[][] north;     // 单元格 (i, j) 的北墙是否存在
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private static Random rgen;
    private static int DRAW_DELAY_MS = 50;
}
