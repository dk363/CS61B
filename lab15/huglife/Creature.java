package huglife;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 *  @author Josh Hug
 *  表示生物的类。你应该通过扩展该类来为你的世界添加生物。
 */
public abstract class Creature extends Occupant {
    /** 该生物的能量。 */
    protected double energy;

    /** 创建一个名字为 N 的生物。该名字意图在相同类型的生物之间共享。 */
    public Creature(String n) {
        super(n);
    }

    /** 当该生物移动时调用。 */
    public abstract void move();

    /** 当该生物攻击生物 C 时调用。 */
    public abstract void attack(Creature c);

    /** 当该生物选择复制时调用。
     * 必须返回相同类型的生物。
     */
    public abstract Creature replicate();

    /** 当该生物选择停留时调用。 */
    public abstract void stay();

    /** 返回一个行为。该生物将根据其周围的邻居信息 NEIGHBORS 做出决定。 */
    public abstract Action chooseAction(Map<Direction, Occupant> neighbors);

    /** 返回当前能量。 */
    public double energy() {
        return energy;
    }

    /** 工具方法：将 Map<Direction, String> N 转换为包含所有指定类型邻居的列表。
     例如，如果地图包含：
     UP -> "sample", DOWN -> "empty", LEFT -> "empty", RIGHT -> "impassible"
     且 type 为 "empty"，则返回的列表包含 Direction.DOWN 和 Direction.LEFT。 */
    public List<Direction> getNeighborsOfType(Map<Direction, Occupant> n,
                                              String type) {
        List<Direction> L = new ArrayList<Direction>();
        for (Map.Entry<Direction, Occupant> entry : n.entrySet()) {
            String occupantName = entry.getValue().name();
            if (occupantName.equals(type)) {
                L.add(entry.getKey());
            }
        }
        return L;
    }
}
