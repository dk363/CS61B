package huglife;

/** 动作由生物创建以表示其意图，并通过 HugLife 模拟器生效。
 *
 *  注意：本类有三个构造方法，每个有不同的语义！
 *  它们是：
 *  1. Action(ActionType at)：用于创建不涉及移动的动作。
 *  2. Action(ActionType at, Direction d)：用于创建涉及相对移动的动作。
 *  3. Action(ActionType at, int x, int y)：用于创建涉及绝对位置移动的动作。
 *     本实验不需要此构造方法，但若你想做一些花哨的功能，可使用它。
 *
 *  有五种 ActionType 类型：MOVE（移动）、REPLICATE（复制）、ATTACK（攻击）、STAY（停留）、DIE（死亡）。
 *  若使用 MOVE、REPLICATE、ATTACK，则必须使用带方向或位置的构造方法。
 *  若使用 STAY 或 DIE，则必须使用无方向的构造方法。
 *
 *  @author Josh Hug
 */

public class Action {
    /** 精确有五种可能的动作类型。MOVE、REPLICATE 和 ATTACK 属于移动类动作。
     *  STAY 和 DIE 属于非移动类动作。
     */
    public enum ActionType {
        MOVE,
        REPLICATE,
        ATTACK,
        STAY,
        DIE
    }

    /** 对于不涉及绝对位置的动作，使用 UNDEFINED 位置值。 */
    private static final int UNDEFINED = -126;

    /** 创建一个不涉及移动的动作。
     * 如果传入的是移动类的 ActionType，将抛出运行时错误。
     * 虽然理论上可以在编译时捕获此类错误，但那将需要嵌套枚举，
     * 鉴于本实验已有足够多的新语法，故未采用。
     * 动作类型为 AT。
     */
    public Action(ActionType at) {
        if (isMoveAction(at)) {
            throw new IllegalArgumentException("试图创建类型为 " + at + " 的动作但未提供方向。");
        }
        type = at;
        dir = null;
        x = UNDEFINED;
        y = UNDEFINED;
    }

    /** 创建类型为 AT，方向为 D 的动作。 */
    public Action(ActionType at, Direction d) {
        if (!isMoveAction(at)) {
            throw new IllegalArgumentException("试图创建类型为 " + at + " 的动作但提供了方向。");
        }
        this.type = at;
        this.dir = d;
        this.x = UNDEFINED;
        this.y = UNDEFINED;
    }

    /** 创建类型为 AT，目标位置为 X 和 Y 的动作。 */
    public Action(ActionType at, int x, int y) {
        if (!isMoveAction(at)) {
            throw new IllegalArgumentException("试图创建类型为 " + at + " 的动作但提供了位置。");
        }
        type = at;
        dir = null;
        this.x = x;
        this.y = y;
    }

    /** 动作的类型。 */
    public final ActionType type;

    /** 动作的方向（如果适用）。 */
    public final Direction dir;

    /** 动作目标位置的 x 坐标（如果适用）。 */
    public final int x;

    /** 动作目标位置的 y 坐标（如果适用）。 */
    public final int y;

    /** 返回 AT 是否为移动类动作。 */
    public boolean isMoveAction(ActionType at) {
        return ((at == ActionType.MOVE) || (at == ActionType.REPLICATE)
                || (at == ActionType.ATTACK));
    }

    /** 判断该动作是否与 OTHER 相等。 */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Action that = (Action) other;
        return this.x == that.x && this.y == that.y && this.dir == that.dir
                && this.type == that.type;
    }

    /** 返回该动作的字符串表示形式。 */
    public String toString() {
        if ((dir == null) && (x != UNDEFINED)) {
            return String.format("Action: " + type + " at " + x + ", "
                    + y + ".");
        } else if ((dir != null)) {
            return String.format("Action: " + type + " in direction "
                    + dir + ".");
        } else {
            return String.format("Action: " + type + ".");
        }
    }
}
