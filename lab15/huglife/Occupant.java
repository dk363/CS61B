package huglife;
import java.awt.Color;

/**
 *  @author Josh Hug
 *  表示网格世界中可能的占据者。
 *  仅供以下类扩展使用：
 *     Creature、Empty 和 Impassible。
 */
public abstract class Occupant {
    /** 此类型占据者的名称。 */
    protected final String name;

    /** 创建一个名称为 N 的占据者。 */
    public Occupant(String n) {
        name = n;
    }

    /** 返回该占据者的名称。 */
    public String name() {
        return name;
    }

    /** 给定 R、G、B 值返回一个 Color 对象。
     *  供子类使用，避免它们自己导入或处理颜色相关逻辑。
     */
    protected static Color color(int r, int g, int b) {
        return new Color(r, g, b);
    }

    /** 必须实现的方法：返回一个颜色对象。 */
    public abstract Color color();
}
