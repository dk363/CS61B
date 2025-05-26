package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 *  Draws a world consisting of hexagonal regions.
 *  六边形有两个部分组成，一是左半部分的空格，一是 *
 *  两个辅助函数计算同一行的空格和 *
 *  一个辅助函数汇总打印上面的信息，打印一行
 *  最后一个函数一次遍历解决问题
 *  hexRowWidth hexRowOffset
 */
public class HexWorld {
    private static final Random RANDOM = new Random();

    /**
        return the num of the width
        if i >= size means i represents the upper half of the hexagon

        @param i in which rows of hexagon
        @param size side length of the hexagon
     */
    public static int hexRowWidth(int i, int size) {
        int effectiveI = i;
        if (i >= size) {
            effectiveI = 2 * size - 1 - effectiveI;
        }
        return size + 2 * effectiveI;
    }

    /**
     * the length of the offset for each line
     *
     *  ** -> the length of the offset is 1 space
     * ****
     * ****
     *  **
     * @param i in which row of the hexa
     * @param size side length of the hexa
     * @return
     */
    public static int hexRowOffset(int i, int size) {
        int effectiveI = i;
        if (i >= size) {
            effectiveI = 2 * size - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /**
     * 添加一行瓦片
     * @param world the hexa we want to print
     * @param p the lower left corner of the hexa
     * @param width the width of the hexa
     * @param t the style of the tile
     */
    private static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCorrd = p.y;

            // make every tile slightly different
            world[xCoord][yCorrd] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * 添加一块六边形瓦片
     * @param world the hexa we want to print
     * @param p he lower left corner of the hexa
     * @param size side length of the hexa
     * @param t the style of the tile
     * @return
     */
    public static void addHexagon(TETile[][] world, Position p, int size, TETile t) {

        for (int i = 0; i < 2 * size; i++) {
            if (size < 2) {
                throw new IllegalArgumentException("hexa must at least size 2");
            }
        }

        for (int yi = 0; yi < 2 * size; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(yi, size);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(yi, size);

            addRow(world, rowStartP, rowWidth, t);
        }
    }

    private static TETile getRandomTile() {
        int index = RANDOM.nextInt(TILE_OPTIONS.length);
        return TILE_OPTIONS[index];
    }

    public static void drawHexagonTiling(TETile[][] world, Position startP, int s) {
        int[] hexesInCol = {3, 4, 5, 4, 3};
        // 从竖直方向上看 hexa 分为这几行，水平方向上有空格，并不易于编码
        // 而且我们的 addHexa 是从左下角开始编码的，所以我们这里保持一致，也从左下角开始编码
        int xOffset = 0;

        for (int col = 0; col < hexesInCol.length; col++) {
            int numHexes = hexesInCol[col];
            int yOffset = (5 - numHexes) * s;
            // 铺满一共有5个，不足5个的向上移（5 - numHexes） 乘上高的一半 也就是 size

            for (int row = 0; row < numHexes; row++) {
                int x = startP.x + xOffset;
                int y = startP.y + yOffset + row * 2 * s;
                /*
                       &&
                      &&&&
                 *  **&&&&
                 * ****&&
                 * ****^^
                 *  **^^^^ -> row 行数，一个是2 * size
                 *    ^^^^
                 *     ^^
                 */

                TETile randomTile = getRandomTile();  // 每次随机选一个图案
                addHexagon(world, new Position(x, y), s, randomTile);
            }

            xOffset += 2 * s - 1;
        }
    }

    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final int HEX_SIZE = 3;
    private static final TETile[] TILE_OPTIONS = {
            Tileset.GRASS,
            Tileset.FLOWER,
            Tileset.TREE,
            Tileset.MOUNTAIN,
            Tileset.SAND,
            Tileset.WATER
    };


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // Position bottomLeft = new Position(10, 5);
        // addHexagon(world, bottomLeft, 3, Tileset.FLOWER);

        Position start = new Position(3, 0);

        drawHexagonTiling(world, start, HEX_SIZE);

        ter.renderFrame(world);
    }
}
