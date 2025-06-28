package byog.BSPExample;

public class Main {
    public static void main(String[] args) {
        // 1. 创建地图配置
        MapConfig config = new MapConfig.Builder()
                .setMapWidth(50)
                .setMapHeight(30)
                .setMinRoomSize(5)
                .setMaxRoomSize(15)
                .setMaxSplitDepth(5)
                .setCorridorWidth(1)
                .setSeed(12345L)
                .build();

        // 2. 生成地图
        BSPMapGenerator generator = new BSPMapGenerator(config);
        MapData mapData = generator.generate();

        // 3. 渲染地图
        MapRenderer renderer = new MapRenderer();
        renderer.renderToConsole(mapData);

        // 打印入口和出口位置
        System.out.println("入口位置: (" + mapData.getEntranceRoom().getCenterX() +
                ", " + mapData.getEntranceRoom().getCenterY() + ")");
        System.out.println("出口位置: (" + mapData.getExitRoom().getCenterX() +
                ", " + mapData.getExitRoom().getCenterY() + ")");
    }
}
