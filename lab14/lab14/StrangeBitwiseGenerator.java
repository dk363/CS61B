package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    // 在 0 到 period - 1 之间变化
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        // 生成器的状态是 递增一的整数
        state = state + 1;
        int weirdState = state & (state >> 7) % period;

        double value = normalize(weirdState);

        return value;
    }

    // 将 0 到 period - 1 之间的值转换为 -1.0 到 1.0 之间的值
    private double normalize (int state) {
        return 2.0 * state / period - 1.0;
    }
}
