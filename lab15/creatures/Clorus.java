package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

import static huglife.HugLifeUtils.*;


public class Clorus extends Creature {
    /** 红色值。 */
    private int r;
    /** 绿色值。 */
    private int g;
    /** 蓝色值。 */
    private int b;
    /** name plip*/
    private final String name = "clorus";
    /** MOVE 行动消耗的能量 */
    private final double MOVE_ENERGY = 0.03;
    /** STAY 消耗的能量 */
    private final double STAY_ENERGY = 0.01;

    /** 创建一个能量为 E 的 clorus */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** 创建一个能量为 1 的 clorus */
    public Clorus() {
        this(1);
    }

    @Override
    /** return name */
    public String name() {
        return name;
    }



    /**
     * 必须实现的方法：返回一个颜色对象。
     */
    @Override
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /**
     * 当该生物攻击生物 C 时调用。
     *
     * @param c
     */
    @Override
    public void attack(Creature c) {
        double cEnergy = c.energy();
        energy += cEnergy;
    }

    /**
     * Clorus 移动时消耗 0.03 单位能量
     */
    @Override
    public void move() {
        energy -= MOVE_ENERGY;
    }

    /**
     * Clorus 停留时消耗 0.01 单位能量
     */
    @Override
    public void stay() {
        energy -= STAY_ENERGY;
    }

    /**
     * Clorus 和其复制体各获得 50% 能量，过程无能量损失。
     *  真是高效！返回一个 baby Clorus。
     */
    @Override
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    /**Clorus 生物必须严格遵循以下行为规则：
     * 1. 如果没有空余方格，Clorus 将保持不动（即使附近存在可攻击的 Plip 生物）。
     * 2. 否则，如果发现任何 Plip 生物，Clorus 会随机攻击其中一只。
     * 3. 否则，如果 Clorus 的能量 >= 1，它会在随机一个空方块上复制。
     * 4. 否则，Clorus 会随机移动到一个空白格子。
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        boolean anyPlip = neighbors.values().stream().anyMatch(o -> o.name().equals("plip"));

        if (empties.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }
        if (anyPlip) {
            List<Direction> plip = getNeighborsOfType(neighbors, "plip");
            return new Action(Action.ActionType.ATTACK, randomEntry(plip));
        }
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(empties));
        }
        return new Action(Action.ActionType.MOVE, randomEntry(empties));
    }
}
