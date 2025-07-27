package lab14;

import lab14lib.Generator;

// 生成锯齿波
public class SawToothGenerator implements Generator {
    private int period;
    // 在 0 到 period - 1 之间变化
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        double value = normalize(state);
        // 生成器的状态是 递增一的整数
        state = (state + 1) % period;

        return value;
    }

    // 将 0 到 period - 1 之间的值转换为 -1.0 到 1.0 之间的值
    private double normalize (int state) {
        return 2.0 * state / period - 1.0;
    }
}
