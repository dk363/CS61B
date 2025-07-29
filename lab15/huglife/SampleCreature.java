package huglife;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** 你可以为你的世界创建的生物示例。
 *
 *  SampleCreature 是一个不死的和平主义者，它会永远在世界中游荡，
 *  从不复制或攻击。
 *
 *  SC 不喜欢拥挤，如果被三面包围，它会移动到任何可用空间中。
 *
 *  即使没有被包围，SC 也有 20% 的概率向周围的空位移动一步。
 *
 *  如果 SampleCreature 静止不动，它的颜色会略微变化，
 *  但只会影响红色部分。
 *
 *  @author Josh Hug
 */
public class SampleCreature extends Creature {
    /** 红色值。 */
    private int r = 155;
    /** 绿色值。 */
    private int g = 61;
    /** 蓝色值。 */
    private int b = 76;
    /** 当空间充足时移动的概率。 */
    private double moveProbability = 0.2;
    /** 允许的颜色变化程度。 */
    private int colorShift = 5;
    /** 复制时保留的能量比例。 */
    private double repEnergyRetained = 0.3;
    /** 分给后代的能量比例。 */
    private double repEnergyGiven = 0.65;

    /** 创建一个能量值为 E 的 sample creature。
     *  这个值对 SampleCreature 的生命无关紧要，
     *  因为它的能量永远不会减少。
     */
    public SampleCreature(double e) {
        super("samplecreature");
        energy = e;
    }

    /** 默认构造函数：创建一个能量为 1 的 creature。 */
    public SampleCreature() {
        this(1);
    }

    /** 使用 Occupant 提供的方法，根据自身的 r、g、b 值返回颜色。 */
    public Color color() {
        return color(r, g, b);
    }

    /** 什么也不做，SampleCreature 是和平主义者，不会选择攻击。
     *  C 是安全的，暂时。
     */
    public void attack(Creature c) {
    }

    /** 当 SampleCreature 移动时没有特别的行为。 */
    public void move() {
    }

    /** 如果 SampleCreature 静止不动，它的红色值会稍微变化，
     *  有特殊代码保证不小于 0 且不超过 255。
     *
     *  你可能会发现 HugLifeUtils 工具类对生成随机数很有用。
     */
    public void stay() {
        r += HugLifeUtils.randomInt(-colorShift, colorShift);
        r = Math.min(r, 255);
        r = Math.max(r, 0);
    }

    /** SampleCreature 根据以下关于邻居的规则采取行动：
     *  1. 如果被三面包围，就移动到唯一的空位中。
     *  2. 否则，如果有任何空位，以 moveProbability 的概率
     *     移动到其中一个空位。
     *  3. 否则，保持静止。
     *
     *  返回选定的动作。
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 1) {
            Direction moveDir = empties.get(0);
            return new Action(Action.ActionType.MOVE, moveDir);
        }

        if (empties.size() > 1) {
            if (HugLifeUtils.random() < moveProbability) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, moveDir);
            }
        }

        return new Action(Action.ActionType.STAY);
    }

    /** 如果 SampleCreature 要复制，它会保留 30% 的能量，
     *  并生成一个拥有 65% 能量的新生物，
     *  其余 5% 能量消散到宇宙中。
     *
     *  然而，如上所述，SampleCreature 从不选择复制，
     *  所以这个方法永远不该被调用。它仅因为 Creature 类
     *  要求我们知道如何复制而存在，唉。
     *
     *  如果这个方法被调用，它将返回一个新的 SampleCreature。
     */
    public SampleCreature replicate() {
        energy = energy * repEnergyRetained;
        double babyEnergy = energy * repEnergyGiven;
        return new SampleCreature(babyEnergy);
    }

}
