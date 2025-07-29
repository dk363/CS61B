package huglife;
import creatures.*;

/** 面向世界的 HugLife 模拟器类。
 *  @author Josh Hug
 */
public class HugLife {

    /** 世界的大小。最好保持在 100 以下。 */
    public static final int WORLD_SIZE = 15;

    /** 默认模拟的最大周期数。 */
    public static final int MAX_CYCLES = 1000;

    /** 每一步模拟之间的时间间隔（毫秒）。
     *  降低该值可以加快运行速度。
     */
    public static final int PAUSE_TIME_PER_SIMSTEP = 100;

    /** 如果为 true，则 HugLifeAnimator 类在每个周期后保存一张图像。
     *  所有周期结束或退出时，这些图像将被合成为一个 GIF 动画。
     *  需要安装 ImageMagick，并且仅在 SIMULATE_BY_CYCLE 模式下工作。
     *  若启用此标志，建议减少 PAUSE_TIME_PER_SIMSTEP，因为文件写入会耗时。
     *  详见 HugLifeAnimator 类。
     */
    public static final boolean GENERATE_GIF = false;

    /** GIF 输出文件名（相对于工作目录），在 GENERATE_GIF 为 true 时有效。
     *  应以 ".gif" 结尾。
     */
    public static final String GIF_OUTPUT_FILENAME = System.currentTimeMillis() + ".gif";

    /** 为 HugLife 模拟创建一个新的 N 大小的世界网格。 */
    public HugLife(int N) {
        g = new Grid(N);
    }

    /** 向 HugLife 宇宙中坐标为 X, Y 的位置添加生物 C。 */
    public void addCreature(int x, int y, Creature c) {
        g.createCreature(x, y, c);
    }

    /** 模拟世界 CYCLES 个周期，每个周期完整模拟一次。 */
    public void simulate(int cycles) {
        if (GENERATE_GIF) {
            HugLifeAnimator.init(GIF_OUTPUT_FILENAME);
        }
        int cycleCount = 0;
        while (cycleCount < cycles) {
            boolean cycleCompleted = g.tic();
            if (cycleCompleted) {
                g.drawWorld();
                StdDraw.show(PAUSE_TIME_PER_SIMSTEP);
                if (GENERATE_GIF) {
                    HugLifeAnimator.saveGifFrame(cycleCount);
                }
                cycleCount += 1;
            }
        }
    }

    /** 模拟 TICS 个 tic，每 TICSBETWEENDRAW 次 tic 绘制一次世界状态。 */
    public void simulate(int tics, int ticsBetweenDraw) {
        for (int i = 0; i < tics; i++) {
            g.tic();
            if ((i % ticsBetweenDraw) == 0) {
                g.drawWorld();
                StdDraw.show(PAUSE_TIME_PER_SIMSTEP);
            }
        }
    }

    /** 一组预设的硬编码世界。这种写法风格很差，但写起来很简单。 */
    public void initialize(String worldName) {
        if (worldName.equals("samplesolo")) {
            addCreature(11, 1, new SampleCreature());
        }

        else if (worldName.equals("sampleplip")) {
            addCreature(11, 1, new SampleCreature());
            addCreature(12, 12, new Plip());
            addCreature(4, 3, new Plip());
        }

        else if (worldName.equals("strugggz")) {
            System.out.println("你需要取消注释 strugggz 测试！");
            /*addCreature(11, 1, new SampleCreature());
            addCreature(12, 12, new Plip());
            addCreature(3, 3, new Plip());
            addCreature(4, 3, new Plip());

            addCreature(2, 2, new Clorus(1));*/
        } else {
            System.out.println("未识别的世界名称！");
        }
    }

    /**
     * 从名为 worldName 的文件中读取世界并初始化一个 HugLife。
     * 注意：不要使用这个方法；保留仅供测试使用。
     * @param  worldName 要读取的文件名
     * @return 一个初始化好的 HugLife 实例
     */
    public static HugLife readWorld(String worldName) {
        In in = new In("huglife/" + worldName + ".world");
        HugLife h = new HugLife(WORLD_SIZE);
        while (!in.isEmpty()) {
            String creature = in.readString();
            int x = in.readInt();
            int y = in.readInt();
            switch (creature) {
                //准备好测试 clorus 类时取消注释
                // case "clorus":
                //     h.addCreature(x, y, new Clorus(1));
                //     break;
                case "plip":
                    h.addCreature(x, y, new Plip());
                    break;
                case "samplecreature":
                    h.addCreature(x, y, new SampleCreature());
                    break;
            }
        }
        return h;
    }

    /** 运行由 ARGS[0] 指定的世界。 */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("用法: java huglife.HugLife [worldname]");
            return;
        }
        HugLife h = readWorld(args[0]);
        // HugLife h = new HugLife(WORLD_SIZE);
        // h.initialize(args[0]); 不要使用我

        if (SIMULATE_BY_CYCLE) {
            h.simulate(MAX_CYCLES);
        } else {
            h.simulate(MAX_TICS, TICS_BETWEEN_DRAW);
        }
    }

    /** 保存所有生物的网格。 */
    private Grid g;


    /** 默认情况下，模拟器按周期模拟，即
     *  允许每个生物移动一次再绘制。
     *  如果设置为 false，世界将在每次移动后绘制
     *  （会慢很多）。
     */
    public static final boolean SIMULATE_BY_CYCLE = true;

    /** 如果使用 tics 模式，默认最大模拟 tics 数。 */
    public static final int MAX_TICS = 100000;
    /** 两次绘制操作之间模拟的 tic 数。 */
    public static final int TICS_BETWEEN_DRAW = 10;

}
