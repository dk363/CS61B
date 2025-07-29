/** 实验五工具类
 *  @author Josh Hug
 */
package huglife;
import java.util.Random;
import java.util.List;

public class HugLifeUtils {
    private static Random r = null;

    /** 返回一个在 0 到 1 之间均匀分布的随机数 */
    public static double random() {
        if (r == null)
            r = new Random();

        return r.nextDouble();
    }

    /** 返回一个在 min 和 max（包括两端）之间均匀分布的随机整数
     来源：http://stackoverflow.com/questions/363681 */
    public static int randomInt(int min, int max) {
        if (r == null)
            r = new Random();

        return r.nextInt((max - min) + 1) + min;
    }

    /** 返回一个在 0 到 max 之间均匀分布的随机整数 */
    public static int randomInt(int max) {
        return randomInt(0, max);
    }

    /** 从列表 L 中均匀随机返回一个 Direction */
    public static Direction randomEntry(List<Direction> L) {
        int dirIndex = randomInt(L.size() - 1);
        return L.get(dirIndex);
    }
}
