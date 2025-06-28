package byog.BSPExample;

/**
 * 走廊类，连接两个房间
 */
public class Corridor {
    private final int startX, startY;
    private final int endX, endY;

    public Corridor(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }



    // Getters...
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getEndX() { return endX; }
    public int getEndY() { return endY; }
}
