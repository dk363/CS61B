package byog.BSPExample;

/**
 * 地图生成配置参数
 * 包含所有可配置的地图生成参数
 */
public class MapConfig {
    // default value
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_MIN_ROOM_SIZE = 5;
    private static final int DEFAULT_MAX_ROOM_SIZE = 15;
    private static final int DEFAULT_MAX_DEPTH = 5;
    private static final int DEFAULT_CORRIDOR_WIDTH = 1;


    private final int mapWidth;
    private final int mapHeight;
    private final int minRoomSize;
    private final int maxRoomSize;
    private final int maxSplitDepth;
    private final int corridorWidth;
    private final Long seed;

    /**
     * 构建地图配置
     * @param builder 配置构建器
     */
    private MapConfig(Builder builder) {
        this.mapWidth = builder.mapWidth;
        this.mapHeight = builder.mapHeight;
        this.minRoomSize = builder.minRoomSize;
        this.maxRoomSize = builder.maxRoomSize;
        this.maxSplitDepth = builder.maxSplitDepth;
        this.corridorWidth = builder.corridorWidth;
        this.seed = builder.seed;
    }

    // Getters
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
    public int getMinRoomSize() { return minRoomSize; }
    public int getMaxRoomSize() { return maxRoomSize; }
    public int getMaxSplitDepth() { return maxSplitDepth; }
    public int getCorridorWidth() { return corridorWidth; }
    public Long getSeed() { return seed; }

    /**
     * 配置构建器模式
     */
    public static class Builder {
        /* 默认的初始值 */
        private int mapWidth = DEFAULT_WIDTH;
        private int mapHeight = DEFAULT_HEIGHT;
        private int minRoomSize = DEFAULT_MIN_ROOM_SIZE;
        private int maxRoomSize = DEFAULT_MAX_ROOM_SIZE;
        private int maxSplitDepth = DEFAULT_MAX_DEPTH;
        private int corridorWidth = DEFAULT_CORRIDOR_WIDTH;
        private Long seed = null;

        public Builder setMapWidth(int width) {
            if (width <= 0) {
                throw new IllegalArgumentException("宽度必须为正数");
            }
            
            this.mapWidth = width;
            return this;
        }

        public Builder setMapHeight(int height) {
            if (height <= 0) {
                throw new IllegalArgumentException("height must be bigger than 0");
            }
            this.mapHeight = height;
            return this;
        }

        public Builder setMinRoomSize(int minRoomSize) {
            if (minRoomSize <= 0) {
                throw new IllegalArgumentException("minRoomSize must be positive");
            }
            this.minRoomSize = minRoomSize;
            return this;
        }

        public Builder setMaxRoomSize(int maxRoomSize) {
            if (maxRoomSize <= 0) {
                throw new IllegalArgumentException("maxRoomSize must be positive");
            }
            this.maxRoomSize = maxRoomSize;
            return this;
        }

        public Builder setMaxSplitDepth(int maxSplitDepth) {
            if (maxSplitDepth <= 0) {
                throw new IllegalArgumentException("maxSplitDepth must be positive");
            }
            this.maxSplitDepth = maxSplitDepth;
            return this;
        }

        public Builder setCorridorWidth(int corridorWidth) {
            if (corridorWidth <= 0) {
                throw new IllegalArgumentException("corridorWidth must be positive");
            }
            this.corridorWidth = corridorWidth;
            return this;
        }

        public Builder setSeed(long seed) {
            this.seed = seed;
            return this;
        }

        public MapConfig build() {
            validate();
            return new MapConfig(this);
        }

        /*  检查当前 builder 中设置的字段是否合法（比如不能为空、不冲突、范围正确等）。
            如果不合法，抛出异常。
        */
        private void validate() {
            if (mapWidth <= 0 || mapHeight <= 0) {
                throw new IllegalArgumentException("地图尺寸必须为正数");
            }
            if (minRoomSize <= 0 || maxRoomSize <= minRoomSize) {
                throw new IllegalArgumentException("房间尺寸参数无效");
            }
            if (maxSplitDepth <= 0) {
                throw new IllegalArgumentException("分割次数必须大于 0 ");
            }
            if (corridorWidth < 1) {
                throw new IllegalArgumentException("走廊宽度必须大于 1 ");
            }
        }
    }
}
