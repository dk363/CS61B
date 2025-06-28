package byog.BSPExample;

/**
 * 地图数据模型
 * 包含地图的网格数据和特殊标记
 */
public class MapData {
    public static final int WALL = 0;
    public static final int FLOOR = 1;
    public static final int ENTRANCE = 2;
    public static final int EXIT = 3;
    public static final int CORRIDOR = 4;

    private final int width;
    private final int height;
    private final int[][] grid;

    private Room entranceRoom;
    private Room exitRoom;

    /**
     * 构造函数
     * @param width 地图宽度
     * @param height 地图高度
     */
    public MapData(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = WALL;
            }
        }
    }

    /**
     * 设置网格值
     * @param x X坐标
     * @param y Y坐标
     * @param value 要设置的值
     */
    public void setCell(int x, int y, int value) {
        if (isInBounds(x, y)) {
            grid[x][y] = value;
        }
    }

    /**
     * 获取网格值
     * @param x X坐标
     * @param y Y坐标
     * @return 网格值
     */
    public int getCell(int x, int y) {
        if (isInBounds(x, y)) {
            return grid[x][y];
        }
        return WALL;
    }
    
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    // Getters and setters...
    public void setEntranceRoom(Room room) { this.entranceRoom = room; }
    public void setExitRoom(Room room) { this.exitRoom = room; }
    public Room getEntranceRoom() { return entranceRoom; }
    public Room getExitRoom() { return exitRoom; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
