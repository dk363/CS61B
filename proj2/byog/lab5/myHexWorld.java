package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class myHexWorld {

    private static final Random RANDOM = new Random();
    /** 先写 addHexarow
     *  hexa 由两部分组成
     *  一是空格部分
     *  二是瓦片部分
     *
     */

    // 开始的地方是左下角开始的 Position

    /**
     *
     * @param size hexa 的边长
     * @param i 所在的行数
     * @return  所要回退的 space
     */
    private static int rowOffsetX(int size, int i) {
        int efficientI = i;
        if (i >= size) {
            efficientI = 2 * size - 1 - efficientI;
        }
        return -efficientI;
    }

    /**
     * 
     * @param size  hexa 的边长
     * @param i 所在行数
     * @return  the number of Tile
     */
    private static int hexRowWidth(int size, int i) {
        int efficientI = i;
        if (i >= size) {
            efficientI = 2 * size - 1 - efficientI;
        }
        return size + 2 * efficientI;
    }

    /**
     * add a row
     * @param world 所要描绘的 2-D 世界
     * @param p 开始铺设 Tile 位置
     * @param width 铺设 Tile 数目
     * @param t the style of the Tile
     */
    private static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi++) {
            int xCoord = p.x + xi;
            int yCoord = p.y;

            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * add a hexa to the Position p
     * @param world 2-D world
     * @param p start position
     * @param size the length of the hexa
     * @param t the style of the Tile
     */
    public static void addHexa(TETile[][] world, Position p, int size, TETile t) {
        if (size < 2) {
            throw new IllegalArgumentException("hexa must at least 2");
        }

        for (int yi = 0; yi < 2 * size; yi++) {
            int width = hexRowWidth(size, yi);

            int thisRowX = p.x + rowOffsetX(size, yi);
            int thisRowY = p.y + yi;

            Position startPosition = new Position(thisRowX, thisRowY);

            addRow(world, startPosition, width, t);
        }
    }

    /**
     * get a random tile to make world more interesting
     * @return  一个随机的Tile
     */
    private static TETile getRandomTile() {
        int index = RANDOM.nextInt(TILE_OPTIONS.length);
        return TILE_OPTIONS[index];
    }

    /**
     * draw 19 hexas in a 30 * 30 world
     * @param world
     * @param startP   start position
     * @param size  the length of the hexa
     */
    public static void drawHexagonTiling(TETile[][] world, Position startP, int size) {
        int[] hexesInCol = {3, 4, 5, 4, 3};
        int xOffset = 0;
        for (int col = 0; col < hexesInCol.length; col++) {
            int numHexes = hexesInCol[col];
            int yOffset = (5 - numHexes) * size;

            for (int row = 0; row < numHexes; row++) {
                int x = startP.x + xOffset;
                int y = startP.y + yOffset + row * 2 * size;

                TETile randomTile = getRandomTile();
                addHexa(world, new Position(x, y), size, randomTile);
            }

            xOffset += 2 * size - 1;
        }
    }

    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
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
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p = new Position(3, 0);
        addHexa(world, p, 3, Tileset.FLOWER);

        ter.renderFrame(world);
    }
}
