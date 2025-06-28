package byog.BSPExample;

import java.util.*;

/**
 * BSP地图生成器主类
 * 使用二叉空间分割算法生成随机地图
 */
public class BSPMapGenerator {
    public final int MIN_EDGE_WIDTH = 2;

    private final MapConfig config;
    private final Random random;
    private final MapData mapData;
    private final List<Room> rooms;
    private final List<Corridor> corridors;
    private final List<Room> leafRooms;

    /**
     * 构造函数
     * @param config 地图配置
     */
    public BSPMapGenerator(MapConfig config) {
        this.config = config;
        this.random = config.getSeed() == null ? new Random() : new Random(config.getSeed());
        this.mapData = new MapData(config.getMapWidth(), config.getMapHeight());
        this.rooms = new ArrayList<>();
        this.corridors = new ArrayList<>();
        this.leafRooms = new ArrayList<>();
    }

    /**
     * 生成完整地图
     * @return 生成的地图数据
     */
    public MapData generate() {
        initializeMap();

        // 1. 空间分割
        Room rootroom = new Room(0, 0, config.getMapWidth(), config.getMapHeight(), null);
        splitRoom(rootroom, 0);

        // 2. 创建房间
        createRoomsInLeaves();

        // 3. 连接房间
        connectRooms();

        // 4. 标记入口和出口
        markEntranceAndExit();

        // 5. 后处理
        postProcessMap();

        return mapData;
    }

    // 初始化为 WALL
    private void initializeMap() {
        for (int x = 0; x < config.getMapWidth(); x++) {
            for (int y = 0; y < config.getMapHeight(); y++) {
                mapData.setCell(x, y, MapData.WALL);
            }
        }
    }

    /**
     * 递归分割空间
     * @param room 要分割的空间
     * @param depth 当前分割深度
     */
    private void splitRoom(Room room, int depth) {
        // 终止条件检查...
        if (depth > config.getMaxSplitDepth()
                /* 如果任意一边太小就不再分割 */
                // TODO: 尝试两边都太小才分割会怎么样
                || room.getWidth() < config.getMinRoomSize()
                || room.getHeight() < config.getMinRoomSize()) {
            room.markAsLeaf();
            leafRooms.add(room);
            return;
        }

        // 决定分割方向
        boolean splitHorizontal = determineSplitDirection(room);

        // 计算分割位置
        int splitPos = calculateSplitPosition(room, splitHorizontal);

        // 无法有效分割
        if (splitPos == -1) {
            room.markAsLeaf();
            leafRooms.add(room);
            return;
        }

        // 创建子房间并递归分割...
        Room left, right;

        if (splitHorizontal) {
            left = new Room(room.getX(), room.getY(), room.getWidth(), splitPos, room);
            right = new Room(room.getX(), room.getY() + splitPos, room.getWidth(), room.getHeight() - splitPos, room);
        } else {
            left = new Room(room.getX(), room.getY(), splitPos, room.getHeight(), room);
            right = new Room(room.getX() + splitPos, room.getY(), room.getWidth() - splitPos, room.getHeight(), room);
        }

        room.setChildren(left, right);
        splitRoom(left, depth + 1);
        splitRoom(right, depth + 1);
    }

    /* 随机决定是水平或者竖直分割 */
    // TODO:用 ratio 决定水平或垂直分割
    // ratio 可以做到接近正方形的分割
    private boolean determineSplitDirection(Room room) {
        if (room.getWidth() > room.getHeight()) {
            return false;
        } else if (room.getWidth() < room.getHeight()) {
            return true;
        } else {
            return random.nextBoolean();
        }
    }

    /* 计算分割的位置（坐标）
    *   如果是水平分割，就返回 room2 y 的位置
    *   如果是垂直分割，就返回 room2 x 的位置
    */
    private int calculateSplitPosition(Room room, boolean horizontal) {
        // 是水平分割吗？是的话就 get height 和 Y
        int totalLength = horizontal ? room.getHeight() : room.getWidth();
        int roomStart = horizontal ? room.getY() : room.getX();

        // 最小的分割量
        int minSplit = roomStart + config.getMinRoomSize();
        // -minRoomSize 确保右边的房子足够大
        int maxSplit = roomStart + totalLength - config.getMinRoomSize();

        if (maxSplit <= minSplit) {
            return -1;
        }

        return minSplit + random.nextInt(maxSplit - minSplit);
    }



    /**
     * 在叶节点创建实际房间
     */
    private void createRoomsInLeaves() {
        for (Room leaf : leafRooms) {
            if (!leaf.isLeaf()) continue;

            int maxAvailableWidth = leaf.getWidth() - MIN_EDGE_WIDTH;
            int maxAvailableHeight = leaf.getHeight() - MIN_EDGE_WIDTH;

            if (maxAvailableHeight < config.getMinRoomSize() ||
                    maxAvailableWidth < config.getMinRoomSize()) {
                continue;
            }

            int roomWidth = 0;
            if (maxAvailableWidth > config.getMinRoomSize()) {
                int maxWidth = Math.min(config.getMaxRoomSize(), maxAvailableHeight);
                roomWidth = config.getMinRoomSize() + random.nextInt(maxWidth - config.getMinRoomSize());
            }

            int roomHeight = 0;
            if (maxAvailableHeight > config.getMinRoomSize()) {
                int maxHeight = Math.min(config.getMaxRoomSize(), maxAvailableHeight);
                roomHeight = config.getMinRoomSize() + random.nextInt(maxHeight - config.getMinRoomSize());
            }

            int maxX = leaf.getWidth() - roomWidth - MIN_EDGE_WIDTH;
            int maxY = leaf.getHeight() - roomHeight - MIN_EDGE_WIDTH;
            int roomX = leaf.getX() + MIN_EDGE_WIDTH + (maxX > 0 ? random.nextInt(maxX) : 0);
            int roomY = leaf.getY() + MIN_EDGE_WIDTH + (maxY > 0 ? random.nextInt(maxY) : 0);

            Room actualRoom = new Room(roomX, roomY, roomWidth, roomHeight, leaf);
            leaf.setActualRoom(actualRoom);

            for (int x = roomX; x < roomX + roomWidth; x++) {
                for (int y = roomY; y < roomY + roomHeight; y++) {
                    if (x >= 0 && x < config.getMapWidth()
                            && y >= 0 && y < config.getMapHeight()) {
                        mapData.setCell(x, y, MapData.FLOOR);
                    }
                }
            }
        }
    }

    private void connectRooms() {
        connectChildren(rootRoom);

        ensureAllRoomsConnected();

        addExtraConnections(0.1);
    }

    private void connectChildren(Room room) {
        if (room == null || room.isLeaf()) return;


        // 递归连接子树
        connectChildren(room.getLeftChild());
        connectChildren(room.getRightChild());

        // 连接左右子树
        connectSubTrees(room.getLeftChild(), room.getRightChild());
    }

    private void connectSubTrees(Room left, Room right) {
        if (left == null || right == null) return;

        // 获取每个子树中最远的叶节点房间
        Room leftRoom = getFurthestRoom(left);
        Room rightRoom = getFurthestRoom(right);

        // 创建连接走廊
        createCorridorBetweenRooms(leftRoom.getActualRoom(), rightRoom.getActualRoom());
    }

    /**
     * 获取子树中距离当前房间最远的叶节点
     * @param room 当前子树根节点
     * @return 该子树中最远的叶节点房间
     */
    private Room getFurthestRoom(Room room) {
        if (room.isLeaf()) {
            return room;
        }

        // 递归获取左右子树的最远房间
        Room leftFurthest = room.getLeftChild() != null ?
                getFurthestRoom(room.getLeftChild()) : null;
        Room rightFurthest = room.getRightChild() != null ?
                getFurthestRoom(room.getRightChild()) : null;

        // 比较左右子树的最远房间，返回距离更远的一个
        if (leftFurthest == null) return rightFurthest;
        if (rightFurthest == null) return leftFurthest;

        // 计算两个候选房间到当前房间的距离
        int leftDistance = calculateDistance(room, leftFurthest);
        int rightDistance = calculateDistance(room, rightFurthest);

        return leftDistance > rightDistance ? leftFurthest : rightFurthest;
    }

    /**
     * 计算两个房间之间的距离
     * 使用曼哈顿距离，计算简单且适合网格环境
     */
    private int calculateDistance(Room room1, Room room2) {
        return Math.abs(room1.getCenterX() - room2.getCenterX()) +
                Math.abs(room1.getCenterY() - room2.getCenterY());
    }

    private void createCorridorBetweenRooms(Room room1, Room room2) {
        boolean useLShape = random.nextBoolean();
        // TODO: use nextDouble() > 0.3 改变几率

        if (useLShape) {
            int cornerX = random.nextBoolean() ? room1.getCenterX() : room2.getCenterX();
            int cornerY = random.nextBoolean() ? room1.getCenterY() : room2.getCenterY();

            createHorizontalCorridor(room1.getCenterX(), cornerX, cornerY);

            createVerticalCorridor(cornerY, room2.getCenterY(), cornerX);
        } else {
            createStraightCorridor(room1.getCenterX(), room1.getCenterY(),
                    room2.getCenterX(), room2.getCenterY());
        }

        // 记录走廊
        corridors.add(new Corridor(room1.getCenterX(), room1.getCenterY(),
                room2.getCenterX(), room2.getCenterY()));
    }


    /**
     * 标记入口和出口
     */
    private void markEntranceAndExit() {
        if (leafRooms.isEmpty()) return;

        // 找到相距最远的两个房间
        Room entrance = leafRooms.get(0);
        Room exit = leafRooms.get(0);
        int maxDistance = 0;

        for (int i = 0; i < leafRooms.size(); i++) {
            for (int j = i + 1; j < leafRooms.size(); j++) {
                Room r1 = leafRooms.get(i);
                Room r2 = leafRooms.get(j);
                int dist = distanceBetweenRooms(r1, r2);
                if (dist > maxDistance) {
                    maxDistance = dist;
                    entrance = r1;
                    exit = r2;
                }
            }
        }

        // 标记入口和出口
        mapData.setEntranceRoom(entrance);
        mapData.setExitRoom(exit);

        // 在中心点做标记
        mapData.setCell(entrance.getCenterX(), entrance.getCenterY(), MapData.ENTRANCE);
        mapData.setCell(exit.getCenterX(), exit.getCenterY(), MapData.EXIT);
    }

    /**
     * 创建水平走廊
     * @param x1 起始X坐标
     * @param x2 结束X坐标
     * @param y 固定Y坐标
     */
    private void createHorizontalCorridor(int x1, int x2, int y) {
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);

        // 考虑走廊宽度
        int halfWidth = config.getCorridorWidth() / 2;
        int widthStart = y - halfWidth;
        int widthEnd = widthStart + config.getCorridorWidth();

        for (int x = startX; x <= endX; x++) {
            for (int wy = widthStart; wy < widthEnd; wy++) {
                if (isValidPosition(x, wy)) {
                    // 只有当前位置是墙时才覆盖为走廊
                    // 避免覆盖已经存在的房间或入口出口
                    if (mapData.getCell(x, wy) == MapData.WALL) {
                        mapData.setCell(x, wy, MapData.CORRIDOR);
                    }
                }
            }
        }
    }

    /**
     * 创建垂直走廊
     * @param y1 起始Y坐标
     * @param y2 结束Y坐标
     * @param x 固定X坐标
     */
    private void createVerticalCorridor(int y1, int y2, int x) {
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);

        // 考虑走廊宽度
        int halfWidth = config.getCorridorWidth() / 2;
        int widthStart = x - halfWidth;
        int widthEnd = widthStart + config.getCorridorWidth();

        for (int y = startY; y <= endY; y++) {
            for (int wx = widthStart; wx < widthEnd; wx++) {
                if (isValidPosition(wx, y)) {
                    if (mapData.getCell(wx, y) == MapData.WALL) {
                        mapData.setCell(wx, y, MapData.CORRIDOR);
                    }
                }
            }
        }
    }

    /**
     * 使用Bresenham算法创建直线走廊
     * @param x1 起点X
     * @param y1 起点Y
     * @param x2 终点X
     * @param y2 终点Y
     */
    private void createStraightCorridor(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            // 绘制当前点及周围形成宽度
            drawCorridorWithWidth(x1, y1);

            if (x1 == x2 && y1 == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    /**
     * 在指定位置绘制带有宽度的走廊点
     */
    private void drawCorridorWithWidth(int centerX, int centerY) {
        int halfWidth = config.getCorridorWidth() / 2;

        for (int wy = centerY - halfWidth; wy <= centerY + halfWidth; wy++) {
            for (int wx = centerX - halfWidth; wx <= centerX + halfWidth; wx++) {
                if (isValidPosition(wx, wy)) {
                    // 计算到中心的距离
                    double dist = Math.sqrt(Math.pow(wx - centerX, 2) + Math.pow(wy - centerY, 2));
                    if (dist <= config.getCorridorWidth() / 2.0) {
                        if (mapData.getCell(wx, wy) == MapData.WALL) {
                            mapData.setCell(wx, wy, MapData.CORRIDOR);
                        }
                    }
                }
            }
        }
    }

    /**
     * 检查坐标是否有效
     */
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < config.getMapWidth() &&
                y >= 0 && y < config.getMapHeight();
    }


    /**
     * 地图后处理
     */
    private void postProcessMap() {
        removeIsolatedWalls();
        // 实现后处理逻辑...
    }

    /**
     * 移除孤立的墙块
     */
    private void removeIsolatedWalls() {
        for (int x = 1; x < config.getMapWidth() - 1; x++) {
            for (int y = 1; y < config.getMapHeight() - 1; y++) {
                if (mapData.getCell(x, y) == MapData.WALL) {
                    int floorCount = 0;
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (mapData.getCell(x + dx, y + dy) == MapData.FLOOR ||
                                    mapData.getCell(x + dx, y + dy) == MapData.CORRIDOR) {
                                floorCount++;
                            }
                        }
                    }
                    if (floorCount >= 5) {
                        mapData.setCell(x, y, MapData.FLOOR);
                    }
                }
            }
        }
    }

    private int distanceBetweenRooms(Room r1, Room r2) {
        int dx = r1.getCenterX() - r2.getCenterX();
        int dy = r1.getCenterY() - r2.getCenterY();
        return dx * dx + dy * dy; // 不需要实际距离，比较平方即可
    }
    // 其他辅助方法...
}
