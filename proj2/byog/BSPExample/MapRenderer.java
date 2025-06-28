package byog.BSPExample;

/**
 * 地图渲染器
 * 负责将地图数据转换为可视化表示
 */
public class MapRenderer {
    private static final char[] SYMBOLS = {
            '#', // WALL
            '.', // FLOOR
            'E', // ENTRANCE
            'X', // EXIT
            ',', // CORRIDOR
    };

    /**
     * 渲染地图到控制台
     * @param mapData 地图数据
     */
    public void renderToConsole(MapData mapData) {
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int cellValue = mapData.getCell(x, y);
                char symbol = cellValue >= 0 && cellValue < SYMBOLS.length ?
                        SYMBOLS[cellValue] : '?';
                System.out.print(symbol);
            }
            System.out.println();
        }
    }

    /**
     * 渲染地图到字符串
     * @param mapData 地图数据
     * @return 渲染后的字符串
     */
    public String renderToString(MapData mapData) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int cellValue = mapData.getCell(x, y);
                char symbol = cellValue >= 0 && cellValue < SYMBOLS.length ?
                        SYMBOLS[cellValue] : '?';
                sb.append(symbol);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
