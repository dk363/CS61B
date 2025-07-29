package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;
import java.util.Random;

import static huglife.HugLifeUtils.*;

/** 一种能够移动的和平光合作用生物的实现。@author Josh Hug */
public class Plip extends Creature {

    /** 红色值。 */
    private int r;
    /** 绿色值。 */
    private int g;
    /** 蓝色值。 */
    private int b;
    /** name plip*/
    private final String name = "plip";
    /** MOVE 行动消耗的能量 */
    private final double MOVE_ENERGY = 0.15;
    /** STAY 消耗的能量 */
    private final double STAY_ENERGY = 0.2;
    /** 最大的能量值 */
    private final double MAX_ENERGY = 2;

    /** 创建一个能量为 E 的 plip。 */
    public Plip(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** 创建一个能量为 1 的 plip。 */
    public Plip() {
        this(1);
    }

    @Override
    /** 返回名字 plip*/
    public String name() {
        return name;
    }

    /** 应该返回一个颜色，其 red = 99，blue = 76，
     *  green 随 plip 的能量线性变化。
     *  若能量为 0，则 green = 63；
     *  若为最大能量，则 green = 255；
     *  中间按线性关系变化。
     *  不需要完全精确。
     */
    public Color color() {
        r = 99;
        b = 76;
        g = (int) (96 * energy + 63);
        return color(r, g, b);
    }

    /** 不对 C 进行任何操作，Plip 是和平主义者。 */
    public void attack(Creature c) {
    }

    /** Plip 移动时损失 0.15 单位能量。
     *  若要避免魔法数字警告，可将其提为常量（非本实验要求）。
     */
    public void move() {
        energy -= MOVE_ENERGY;
    }

    /** Plip 停留时通过光合作用获得 0.2 单位能量。 */
    public void stay() {
        energy = Math.min(energy + STAY_ENERGY, MAX_ENERGY);
    }

    /** Plip 和其复制体各获得 50% 能量，过程无能量损失。
     *  真是高效！返回一个 baby Plip。
     */
    public Plip replicate() {
        energy = energy / 2;
        return new Plip(energy);
    }

    /** Plip 根据邻居 NEIGHBORS 执行以下行为：
     *  1. 若无空邻居，则 STAY；
     *  2. 否则若能量 >= 1，则 REPLICATE；
     *  3. 否则若存在 Clorus，以 50% 概率 MOVE；
     *  4. 否则 STAY。
     *
     *  返回一个 Action 类型的对象。
     *  可参考 Action.java 和 SampleCreature.chooseAction()。
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        boolean anyClorus = neighbors.values().stream().anyMatch(o -> o.name().equals("clorus"));

        if (empties.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(empties));
        }
        if (anyClorus && random() < 0.5) {
            return new Action(Action.ActionType.MOVE, randomEntry(empties));
        }
        return new Action(Action.ActionType.STAY);
    }

}
