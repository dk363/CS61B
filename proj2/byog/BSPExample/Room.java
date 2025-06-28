package byog.BSPExample;

/**
 * 房间类，表示地图中的一个房间
 */
public class Room {
    private final int x, y;          // 左上角坐标
    private final int width, height; // 房间尺寸
    private final int centerX, centerY; // 中心点
    private Room parent; // 父节点
    private Room leftChild;
    private Room rightChild;
    private Room actualRoom;
    private boolean isLeaf;

    public Room(int x, int y, int width, int height, Room parent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.centerX = x + width / 2;
        this.centerY = y + height / 2;
        this.parent = parent;
        this.isLeaf = false;
    }

    public void setChildren(Room left, Room right) {
        this.leftChild = left;
        this.rightChild = right;
        this.isLeaf = false;
    }

    public void markAsLeaf() {
        this.isLeaf = true;
    }


    public void setActualRoom(Room room) {
        this.actualRoom = room;
    }
    /**
     * 检查是否与另一个房间相交
     * @param other 另一个房间
     * @return 是否相交
     */
    public boolean intersects(Room other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    // Getters...
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getCenterX() { return centerX; }
    public int getCenterY() { return centerY; }
    public Room getParent() {
        return parent;
    }
    public Room getLeftChild() {
        return leftChild;
    }
    public Room getRightChild() {
        return rightChild;
    }
    public Room getActualRoom() {
        return actualRoom;
    }
    public boolean isLeaf() {
        return isLeaf;
    }
}
